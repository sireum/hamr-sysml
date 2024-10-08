package TempControlAadl {

    private import AADL::*;
    private import AADL_Project::*;
    private import AADL_Project::Time_Units::*;

	language "GUMBO" /*{
	  library
        functions
          def inRange(temp: Temperature): Base_Types::Boolean :=
            temp.degrees >= -50.0 [f32] and temp.degrees <= 150.0 [f32];
        }*/

	part def TempControlSystem :> System {
		// subcomponents
		part tcp : TempControlProcess;
	}
	
	part def TempControlProcess :> Process {
		// subcomponents
		part tempSensor : TempSensor;
		part tempControl : TempControl;
		part fan : Fan;
		part opInterface : OperatorInterface;
		
		// connections
		connection ctTStoTC : PortConnection connect tempSensor.currentTemp to tempControl.currentTemp;
		connection ctTStoOI : PortConnection connect tempSensor.currentTemp to opInterface.currentTemp;
		connection fcTCtoF : PortConnection connect tempControl.fanCmd to fan.fanCmd;
		connection faFtoTC : PortConnection connect fan.fanAck to tempControl.fanAck;
		connection spOItoTC : PortConnection connect opInterface.setPoint to tempControl.setPoint;
	}
	
	// TempSensor Thread
	part def TempSensor :> Thread {
		// features
		out port currentTemp : DataPort { out :> type : Temperature; }
	
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];

		language "GUMBO" /*{
          integration
            guarantee Sensor_Temperature_Range:
              TempControlAadl::GUMBO__Library::inRange(currentTemp);

            initialize
              guarantee initializes:
                currentTemp.degrees == 72 [f32];
		}*/
	}

	part def TempControl :> Thread {
		// features
		in port currentTemp : DataPort { in :> type : Temperature; }
		in port fanAck : DataPort { in :> type : FanAck; }
		in port setPoint : DataPort { in :> type : SetPoint; }
		out port fanCmd : DataPort { out :> type : FanCmd; }

		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];

		language "GUMBO" /*{

          state
            latestFanCmd: FanCmd;

          integration
            assume currentTempRange:
              (currentTemp.degrees >= -70.0 [f32]) & (currentTemp.degrees <= 80.0 [f32]);

          initialize
             modifies (latestFanCmd);
             guarantee initLatestFanCmd "Initialize state variable":
              // provide initial value for state variable and out data port
              latestFanCmd == FanCmd.Off;

             guarantee initFanCmd "Initial fan command":
               // provide initial value for state variable and out data port
               fanCmd == FanCmd.Off;

          // illustrate cases syntax
          compute
            modifies (latestFanCmd);

              guarantee altCurrentTempLTSetPoint "If current temperature is less than
                                                 |the current low set point, then the fan state shall be Off" :
                '->:'(currentTemp.degrees < setPoint.low.degrees,
                      latestFanCmd == FanCmd.Off and fanCmd == FanCmd.Off);

              guarantee altCurrentTempGTSetPoint "If current temperature is greater than
                                                 |the current high set point, then the fan state shall be On":
                 '->:'(currentTemp.degrees > setPoint.high.degrees,
                       latestFanCmd == FanCmd.On & fanCmd == FanCmd.On);

              guarantee altCurrentTempInRange "If current temperature is greater than or equal to the
                                              |current low set point and less than or equal to the current high set point,
                                              |then the current fan state is maintained." :
                '->:'(currentTemp.degrees >= setPoint.low.degrees & currentTemp.degrees <= setPoint.high.degrees,
                      latestFanCmd == In(latestFanCmd) & fanCmd == latestFanCmd);

            // alternative style using cases

            compute_cases
              case currentTempLTSetPoint "If current temperature is less than
                                         |the current low set point, then the fan state shall be Off":
                assume currentTemp.degrees < setPoint.low.degrees;
                guarantee (latestFanCmd == FanCmd.Off) & (fanCmd == FanCmd.Off);

              case currentTempGTSetPoint "If current temperature is greater than
                                         |the current high set point, then the fan state shall be On":
                assume currentTemp.degrees > setPoint.high.degrees;
                guarantee (latestFanCmd == FanCmd.On) & (fanCmd == FanCmd.On);

              case currentTempInRange "If current temperature is greater than or equal to the
                                      |current low set point and less than or equal to the current high set point,
                                      |then the current fan state is maintained.":
                assume (currentTemp.degrees >= setPoint.low.degrees) & (currentTemp.degrees <= setPoint.high.degrees);
                guarantee
                  (latestFanCmd == In(latestFanCmd)) &
                  (fanCmd == latestFanCmd);

		}*/
	}

	part def Fan :> Thread {
		// features
		in port fanCmd : EventDataPort { in :> type : FanCmd; }
		out port fanAck : EventDataPort { out :> type : FanAck; }
		
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];
		
		//@Properties {
		//	Dispatch_Protocol = Sporadic;
		//	Period = 1000;
		//}
	}

	part def OperatorInterface :> Thread {
		// features
		in port currentTemp : DataPort { in :> type : Temperature; }
		out port setPoint : EventDataPort { out :> type : SetPoint; }
	
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
	    attribute :>> Period = 1000 [millisecond];
	    
		//@Properties {
		//	Dispatch_Protocol = Periodic;
		//	Period = 1000;
		//}
	}

	// Data Types
	part def Temperature :> Data {

   		part degrees : Base_Types::Float_32;
		attribute unit : TempUnit;

		language "GUMBO" /*{
          // data invariant
          // data invariants are assumed whenever value of the datatype is consumed
          //                  and must be proved whenever a value of the data type is constructed
          invariants
            inv AbsZero:
              degrees >= GUMBO_Definitions::GUMBO__Library::absoluteZero();
		}*/
	}
	
	part def SetPoint :> Data {
		part low : Temperature;
		part high : Temperature;

		language "GUMBO" /*{
          invariants
            inv SetPoint_Data_Invariant:
              (low.degrees >= 50.0 [f32]) &
              (high.degrees <= 110.0 [f32]) &
              (low.degrees <= high.degrees);
		}*/
	}
	
	enum def TempUnit {
		enum Fahrenheit;
		enum Celsius;
		enum Kelvin;
	}
	
	enum def FanCmd {
		enum On;
		enum Off;
	}
	
	enum def FanAck {
		Ok;
		Error;
	}
}