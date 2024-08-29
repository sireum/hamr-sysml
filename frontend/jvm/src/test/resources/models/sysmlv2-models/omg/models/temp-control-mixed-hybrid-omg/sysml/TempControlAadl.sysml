package TempControlAadl {

    import AADL::*;
    import AADL_Project::*;
    import AADL_Project::Time_Units::*;

	language "GUMBO" /*{
	  library
        functions
          def inRange(temp: Temperature): Base_Types::Boolean :=
            temp.degrees >= f32(-50.0) and temp.degrees <= f32(150.0);

          def enumTest(e: FanCmd): FanCmd := e;

        }*/

	part def TempControlSystem :> System {
		// subcomponents
		part tcp: TempControlProcess;
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
		connection tcTStoTC : PortConnection connect tempSensor.tempChanged to tempControl.tempChanged;
		connection fcTCtoF : PortConnection connect tempControl.fanCmd to fan.fanCmd;
		connection faFtoTC : PortConnection connect fan.fanAck to tempControl.fanAck;
		connection spOItoTC : PortConnection connect opInterface.setPoint to tempControl.setPoint;
	}

	// TempSensor Thread
	part def TempSensor :> Thread {
		// features
		out port currentTemp : DataPort { out :> type : Temperature; }

		// FIXME: EventPort does not have an item usage with visiblity rules.
		out port tempChanged : EventPort;
	
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];

		language "GUMBO" /*{
          functions

            def defaultTempDegrees(): Base_Types::Float_32 := f32(72.0);

          integration
            guarantee Sensor_Temperature_Range:
                TempControlAadl::GUMBO__Library::inRange(currentTemp);

          initialize
            guarantee initializes:
              currentTemp.degrees == defaultTempDegrees();
		}*/
	}

	part def TempControl :> Thread {
		// features
		in port currentTemp : DataPort { in :> type : Temperature; }
		
		// FIXME: EventPort does not have an item usage with visiblity rules.
		in port tempChanged : EventPort;
		in port fanAck : EventDataPort { in :> type : FanAck; }
		in port setPoint : EventDataPort { in :> type : SetPoint; }
		out port fanCmd : EventDataPort { out :> type : FanCmd; }

		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Sporadic;
		attribute :>> Period = 1000 [millisecond];

		language "GUMBO" /*{
          state
            currentSetPoint: SetPoint;
            currentFanState: FanCmd;
            latestTemp: Temperature;

          integration
            assume currentTempRange:
              (currentTemp.degrees >= f32(-128.6)) & (currentTemp.degrees <= f32(134.0));

          initialize
            modifies currentSetPoint, currentFanState, latestTemp;

            guarantee defaultSetPoint:
              (currentSetPoint.low.degrees == f32(70.0)) and (currentSetPoint.high.degrees == f32(80.0));

            guarantee defaultFanStates:
              currentFanState == FanCmd.Off;

            guarantee defaultLatestTemp:
              latestTemp.degrees == f32(72.0);

          compute  // behaviors for COMPUTE Entrypoint
            modifies currentSetPoint, currentFanState, latestTemp;

            assume
                a1 "If the previously received currentTemp was less than the previously
                   |received setPoint then the last fan command must have been Off":
                  '->:'(latestTemp.degrees < currentSetPoint.low.degrees,
                        currentFanState == FanCmd.Off);

            assume
                a2 "If the previously received currentTemp was more than the previously
                   |received setPoint then the last fan command must have been On":
                  '->:'(latestTemp.degrees > currentSetPoint.high.degrees,
                        currentFanState == FanCmd.On);

            //    ---------------------------
            //     Compute Entry Point General Clauses
            //       The following contracts state properties reflecting control laws that should always
            //       hold no matter what incoming event triggers dispatch.   Therefore, they are stated
            //       as independent (not associated with a handler) guarantee clauses.
            //    ---------------------------
            guarantee  TC_Req_01 "If the current temperature is less than the set point, then the fan state shall be Off.":
              '->:'(latestTemp.degrees < currentSetPoint.low.degrees,
                    currentFanState == FanCmd.Off);

            guarantee TC_Req_02 "If the current temperature is greater than the set point,
                                |then the fan state shall be On.":
              '->:' (latestTemp.degrees > currentSetPoint.high.degrees,
                     currentFanState == FanCmd.On);

            guarantee TC_Req_03 "If the current temperature is greater than or equal to the
                                |current low set point and less than or equal to the current high set point,
                                |then the current fan state is maintained.":
              '->:'(latestTemp.degrees >= currentSetPoint.low.degrees & latestTemp.degrees <= currentSetPoint.high.degrees
                    ,
                    // if we are still in set point range, don't change the fan state
                    currentFanState == In(currentFanState));

            guarantee mustSendFanCmd "If the local record of the fan state was updated,
                                     |then send a fan command event with this updated value.":
              (In(currentFanState) != currentFanState) implies MustSend(fanCmd, currentFanState) and

              (currentFanState == In(currentFanState)) implies NoSend(fanCmd);

            //    ---------------------------
            //     Compute Entry Point Handler Contracts
            //       Handler contracts allow the compute entry point contracts to be extended with additional
            //       constraints that must hold for a particular handler.
            //
            //       At the Slang level, each handler's contract is formed by conjoining the compute entry point
            //       general clauses with the contract clauses given in the handlers.
            //       For the modifies clauses, CURRENTLY, the modifies clauses must be identical to the modifies
            //       clause in the compute entry point general clauses.  Note this means that the modifies may be
            //       an over-approximation -- some listed variables may actually not be modified in the handler.
            //       (I believe SPARK would give an error/warning if a listed variable is not written on at least
            //        one path, but Logika does not enforce that).
            //       Thus, in the current state of the tools, if a variable is not actually modified
            //       in the handler, an explicit frame condition must be given equating the pre- and post-state
            //       version of the variable.
            //       In the future, such frame conditions may be automatically generated.
            //    ---------------------------

            handle setPoint:
              modifies (currentSetPoint);

              guarantee setPointChanged:
                currentSetPoint == setPoint;

              guarantee latestTempNotModified:
                (latestTemp == In(latestTemp));


            handle tempChanged:
              modifies (latestTemp);

              guarantee tempChanged:
                latestTemp == currentTemp;

              guarantee setPointNotModified:
                currentSetPoint == In(currentSetPoint);


            handle fanAck:
              guarantee setPointNotModified:
                currentSetPoint == In(currentSetPoint);

              guarantee lastTempNotModified:
                latestTemp == In(latestTemp);

              guarantee currentFanState:
                currentFanState == In(currentFanState);

              guarantee noEventsSent:
                NoSend(fanCmd);
		}*/
	}

	part def Fan :> Thread {
		// features
		in port fanCmd : EventDataPort { in :> type : FanCmd; }
		out port fanAck : EventDataPort { out :> type : FanAck; }
		
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];
	}

	part def OperatorInterface :> Thread {
		// features
		in port currentTemp : DataPort { in :> type : Temperature; }
		out port setPoint : EventDataPort { out :> type : SetPoint; }
	
		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
	    attribute :>> Period = 1000 [millisecond];
	}

	// Data Types
	part def Temperature :> Data {

   		part degrees : Base_Types::Float_32;
		attribute unit : TempUnit;

		language "GUMBO" /*{
          // data invariant
          //  data invariants are assumed whenever value of the datatype is consumed
          //                  and must be proved whenever a value of the data type is constructed
          invariants
            inv AbsZero:
              GUMBO_Definitions::GUMBO__Library::aboveAbsoluteZero(degrees);
		}*/
	}
	
	part def SetPoint :> Data {
		part low : Temperature;
		part high : Temperature;

		language "GUMBO" /*{
          invariants
            inv SetPoint_Data_Invariant:
              (low.degrees >= f32(50.0)) &
              (high.degrees <= f32(110.0)) &
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