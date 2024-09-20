// @ HAMR: --slang-output-dir hamr/slang --package-name tc --platform seL4 --output-c-dir hamr/c --camkes-output-dir hamr/camkes
//@ HAMR: --platform Microkit --camkes-output-dir microkit


// codegen will write out to the workspace root dir if output-dir is not set.
// The option can be relative to the workspace (above) or absolute

package TempControlAadl {
 
    import AADL::*;
    import AADL_Project::*;
    import AADL_Project::Time_Units::*;

  language "GUMBO" /*{
    library
      functions
        def inRange(temp: Temperature): Base_Types::Boolean :=
          temp.degrees >= -40.0[f32] & temp.degrees <= 122.0[f32];
      }*/

  part def TempControlSystem :> System {
    // subcomponents
    part tsp : TempSensorProcess;
    part tcp : TempControlProcess;
    part fp : FanProcess;
    part oip : OperatorInterfaceProcess;

    part proc: TempControlProcessor;

    connection c0 : PortConnection connect tsp.currentTemp to tcp.currentTemp;
    connection c1 : PortConnection connect tsp.currentTemp to oip.currentTemp;
    connection c2 : PortConnection connect fp.fanAck to tcp.fanAck;
    connection c3 : PortConnection connect tcp.fanCmd to fp.fanCmd;
    connection c4 : PortConnection connect oip.setPoint to tcp.setPoint;

    allocation b0 : Deployment_Properties::Actual_Processor_Binding allocate tsp to proc;
    allocation b1 : Deployment_Properties::Actual_Processor_Binding allocate tcp to proc;
    allocation b2 : Deployment_Properties::Actual_Processor_Binding allocate fp to proc;
    allocation b3 : Deployment_Properties::Actual_Processor_Binding allocate oip to proc;
  }

  part def TempControlProcessor :> Processor {
    attribute framePeriod: Timing_Properties::Frame_Period = 1000 [millisecond];
    attribute clockPeriod: Timing_Properties::Clock_Period = 1 [millisecond];
  }

  part def TempSensorProcess :> Process {
    // subcomponents
    part tempSensor : TempSensor;

    // features
    out port currentTemp : DataPort { :> type : Temperature; }

    // connections
    connection c0 : PortConnection connect tempSensor.currentTemp to currentTemp;

    attribute domain: CASE_Scheduling::Domain = 2;
  }
  
  // TempSensor Thread
  part def TempSensor :> Thread {
    // features
    out port currentTemp : DataPort { :> type : Temperature; }
  
    attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
    attribute :>> Period = 1000 [millisecond];
    attribute :>> Compute_Execution_Time = 100 [millisecond] .. 100 [millisecond];

    language "GUMBO" /*{
      integration
        guarantee Sensor_Temperature_Range:
          currentTemp.degrees >= -40.0[f32] & currentTemp.degrees <= 121.0[f32];

      initialize
        guarantee initializes:
          currentTemp.degrees == 72.0[f32];
    }*/
  }

  part def TempControlProcess :> Process {

    part tempControl : TempControl;

    in port currentTemp : DataPort { in :> type : Temperature; }
    in port fanAck : DataPort { in :> type : FanAck; }
    in port setPoint : DataPort { in :> type : SetPoint; }
    out port fanCmd : DataPort { out :> type : FanCmd; }

    connection c0 : PortConnection connect currentTemp to tempControl.currentTemp;
    connection c1 : PortConnection connect fanAck to tempControl.fanAck;
    connection c2 : PortConnection connect setPoint to tempControl.setPoint;
    connection c3 : PortConnection connect tempControl.fanCmd to fanCmd;

    attribute domain: CASE_Scheduling::Domain = 3;
  }

  part def TempControl :> Thread {
    // features
    in port currentTemp : DataPort { in :> type : Temperature; }
    in port fanAck : DataPort { in :> type : FanAck; }
    in port setPoint : DataPort { in :> type : SetPoint; }
    out port fanCmd : DataPort { out :> type : FanCmd; }

    attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
    attribute :>> Period = 1000 [millisecond];
    attribute :>> Compute_Execution_Time = 100 [millisecond] .. 100 [millisecond];

    language "GUMBO" /*{

      state
        latestFanCmd: FanCmd;

      integration
        assume currentTempRange:
          currentTemp.degrees >= -40.0[f32] & currentTemp.degrees <= 121.0[f32];

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

  part def FanProcess :> Process {
    part fan : Fan;

    // features
    in port fanCmd : DataPort { in :> type : FanCmd; }
    out port fanAck : DataPort { out :> type : FanAck; }

    connection c0 : PortConnection connect fanCmd to fan.fanCmd;
    connection c1 : PortConnection connect fan.fanAck to fanAck;

    attribute domain: CASE_Scheduling::Domain = 4;
  }

  part def Fan :> Thread {
    // features
    in port fanCmd : DataPort { in :> type : FanCmd; }
    out port fanAck : DataPort { out :> type : FanAck; }
    
    attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
    attribute :>> Period = 1000 [millisecond];
    attribute :>> Compute_Execution_Time = 100 [millisecond] .. 100 [millisecond];
  }

  part def OperatorInterfaceProcess :> Process {
    part operatorInterface : OperatorInterface;

    in port currentTemp : DataPort { in :> type : Temperature; }
    out port setPoint : DataPort { out :> type : SetPoint; }

    connection c0 : PortConnection connect currentTemp to operatorInterface.currentTemp;
    connection c1 : PortConnection connect operatorInterface.setPoint to setPoint;

    attribute domain: CASE_Scheduling::Domain = 5;
  }

  part def OperatorInterface :> Thread {
    // features
    in port currentTemp : DataPort { in :> type : Temperature; }
    out port setPoint : DataPort { out :> type : SetPoint; }
  
    attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
    attribute :>> Period = 1000 [millisecond];
    attribute :>> Compute_Execution_Time = 100 [millisecond] .. 100 [millisecond];
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
          degrees >= -459.67[f32];
    }*/
  }
  
  part def SetPoint :> Data {
    part low : Temperature;
    part high : Temperature;

    language "GUMBO" /*{
      invariants
        inv SetPoint_Data_Invariant:
          (low.degrees >= 50.0[f32]) &
          (high.degrees <= 110.0[f32]) &
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
