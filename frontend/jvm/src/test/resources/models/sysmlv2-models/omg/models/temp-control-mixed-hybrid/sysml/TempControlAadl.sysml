package TempControlAadl {

    import AADL::*;
    import AADL_Project::*;
    import AADL_Project::Time_Units::*;

	language "GUMBO" /*{
	  library
        functions
          // defs may include constants (these might also be defined in an Alisa file)
          // Types on defs may include base types as well as user-defined types

          // paramless version (i.e. no parens) is not currently supported as it requires modifications
          // to codegen/sym-resolving

          def gminTempDegrees(): Base_Types::Float_32 := f32(-50.0);

          // only strictpure methods are currently supported so the annotation is optional
          def gmaxTempDegrees(): Base_Types::Float_32 := f32(150.0);

          def gdefaultTempDegrees(): Base_Types::Float_32 := f32(72.0);

          // defs may include predicates (strict pure boolean functions) (these might also be defined in an Alisa file)
          def gTempSensorRange(tempy: Temperature): Base_Types::Boolean :=
             // can use simple name of any method defined in this lib annex
             (tempy.degrees >= gminTempDegrees()) and (tempy.degrees <= gmaxTempDegrees()) and

             // or you can use the fully qualified method names
             (tempy.degrees >= TempSensor::gminTempDegrees()) and (tempy.degrees <= TempSensor::gmaxTempDegrees());
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
		connection tcTStoTC : PortConnection connect tempSensor.tempChanged to tempControl.tempChanged;
		connection fcTCtoF : PortConnection connect tempControl.fanCmd to fan.fanCmd;
		connection faFtoTC : PortConnection connect fan.fanAck to tempControl.fanAck;
		connection spOItoTC : PortConnection connect opInterface.setPoint to tempControl.setPoint;
	}
	
	// TempSensor Thread
	part def TempSensor :> Thread {
		// features
		out port currentTemp : DataPort { out :> type : Temperature; }
		out port tempChanged : EventPort;
	
		attribute :>> Dispatch_Protocol = Periodic;
		attribute :>> Period = 1000 [ms];
		
		//@Properties {
		//	Dispatch_Protocol = Periodic;
		//	Period = 1000;
		//}

		language "GUMBO" /*{
          functions
            def minTempDegrees(): Base_Types::Float_32 := f32(-50.0);

            def defaultTempDegrees(): Base_Types::Float_32 := f32(72.0);

          integration
            guarantee Sensor_Temperature_Range:
                (currentTemp.degrees >= f32(-50.0)) and (currentTemp.degrees <= f32(150.0)) and

                // subclause spec defs can only be referred to via their simple name
                (currentTemp.degrees >= minTempDegrees()) and

                // can refer to library spec defs defined in the same package using the simple name
                (currentTemp.degrees >= gminTempDegrees()) and

                // or you can use the fully qualified name
                (currentTemp.degrees >= TempSensor::gminTempDegrees()) and

                // currently must use the fully qualified name for spec defs defined in remote package
                // but maybe we should relax that to match how OSATE handles imported symbols
                (GUMBO_Definitions::inRange(currentTemp));

          initialize
            guarantee initializes:
              (currentTemp.degrees == f32(72.0)) and

              (currentTemp.degrees == defaultTempDegrees()) and

              // FIXME: see commented out method in functions section
              //(currentTemp == createTemperature(f32"72.0"));

              // but you can construct the type directly
              (currentTemp == Temperature.i(defaultTempDegrees())) and

              // or you can use the fully qualified name of the type
              (currentTemp == TempSensor::Temperature.i(defaultTempDegrees()));

            guarantee g1 "Testing MustSend with event port, initializes so no event expected":
              not MustSend(tempChanged);

            guarantee g2 "Testing NoSend with event port, initializes so no event expected":
              NoSend(tempChanged);
		}*/
	}

	part def TempControl :> Thread {
		// features
		in port <tc> currentTemp : DataPort { in :> type : Temperature; }
		
		// EventPort does not have an item usage with visiblity rules.
		in port <'this is a short name'> tempChanged : EventPort;
		
		out port <'just short'> fanCmd : EventDataPort { out :> type : FanCmd; }
		in port fanAck : EventDataPort { in :> type : FanAck; }
		in port setPoint : EventDataPort { in :> type : SetPoint; }
		
		attribute :>> Dispatch_Protocol = Sporadic;
		attribute :>> Period = 1000 [ms];
		
		//@Properties {
		//	Dispatch_Protocol = Sporadic;
		//	Period = 1000;
		//}
		language "GUMBO" /*{
          // state variables are not needed in periodic version since we will base all control logic
          //  directly off of data port values
          state
            currentSetPoint: SetPoint;
            currentFanState: CoolingFan::FanCmd;
            latestTemp: TempSensor::Temperature;

          integration
            assume currentTempRange:
              (currentTemp.degrees >= f32(-70.0)) & (currentTemp.degrees <= f32(180.0));

          initialize
            modifies currentSetPoint, currentFanState, latestTemp;
            guarantee defautSetPoint:
              (currentSetPoint.low.degrees == f32(70.0)) and (currentSetPoint.high.degrees == f32(80.0));
            guarantee defaultFanStates:
              currentFanState == CoolingFan::FanCmd.Off;
            guarantee defaultLatestTemp:
              latestTemp.degrees == f32(72.0);

          compute  // behaviors for COMPUTE Entrypoint
            modifies currentSetPoint, currentFanState, latestTemp;

            assume
                a1 "If the previously received currentTemp was less than the previously
                   |received setPoint then the last fan command must have been Off":
                  ((latestTemp.degrees < currentSetPoint.low.degrees) implies (currentFanState == CoolingFan::FanCmd.Off));

            assume
                a2 "If the previously received currentTemp was more than the previously
                   |received setPoint then the last fan command must have been On":
                  ((latestTemp.degrees > currentSetPoint.high.degrees) implies (currentFanState == CoolingFan::FanCmd.On));

            assume Test "Just testing assume+guarantee along with handlers":
              (3 + 2) == 5;

            assume Refer_to_state_var_in_sporadic_general_assume:
              currentSetPoint.low.degrees <= currentSetPoint.high.degrees;

            //    ---------------------------
            //     Compute Entry Point General Clauses
            //       The following contracts state properties reflecting control laws that should always
            //       hold no matter what incoming event triggers dispatch.   Therefore, they are stated
            //       as independent (not associated with a handler) guarantee clauses.
            //    ---------------------------
            guarantee  TC_Req_01 "If the current temperature is less than the set point, then the fan state shall be Off.":
              (latestTemp.degrees < currentSetPoint.low.degrees) implies (currentFanState == CoolingFan::FanCmd.Off);

            guarantee TC_Req_02 "If the current temperature is greater than the set point,
                                |then the fan state shall be On.":
              (latestTemp.degrees > currentSetPoint.high.degrees) implies (currentFanState == CoolingFan::FanCmd.On);

            guarantee TC_Req_03 "If the current temperature is greater than or equal to the
                                |current low set point and less than or equal to the current high set point,
                                |then the current fan state is maintained.":
              ((latestTemp.degrees >= currentSetPoint.low.degrees) &
                (latestTemp.degrees <= currentSetPoint.high.degrees))
                // if we are still in set point range, don't change the fan state
                implies (currentFanState == In(currentFanState));

            guarantee mustSendFanCmd "If the local record of the fan state was updated,
                                     |then send a fan command event with this updated value.":
              ((In(currentFanState) != currentFanState) implies MustSend(fanCmd,currentFanState)) and // alternate: fanCmd!!currentFanState

              // belt: TODO discuss addition of NoSend
              ((currentFanState == In(currentFanState)) implies NoSend(fanCmd)) and

              // belt: testing MustSend with EventDataPort where we just care that an event was sent
              ((In(currentFanState) != currentFanState) implies MustSend(fanCmd));

            // TODO: discussion, is MustSend supported for outgoing data ports

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

            // TODO: it would be nice if we supported task markers (ie. this would show up in the Tasks view)

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
                // currentFanState == fanCmd; // belt: according to Figure 38, but fanCmd should be empty
                currentFanState == In(currentFanState);

              guarantee noEventsSent:
                NoSend(fanCmd);
		}*/
	}

	part def Fan :> Thread {
		// features
		in port fanCmd : EventDataPort { in :> type : FanCmd; }
		out port fanAck : EventDataPort { out :> type : FanAck; }
		
		attribute :>> Dispatch_Protocol = Sporadic;
		attribute :>> Period = 1000 [ms];
		
		//@Properties {
		//	Dispatch_Protocol = Sporadic;
		//	Period = 1000;
		//}
	}

	part def OperatorInterface :> Thread {
		// features
		in port currentTemp : DataPort { in :> type : Temperature; }
		out port setPoint : EventDataPort { out :> type : SetPoint; }
	
		attribute :>> Dispatch_Protocol = Periodic;
	    attribute :>> Period = 1000 [ms];
	    
		//@Properties {
		//	Dispatch_Protocol = Periodic;
		//	Period = 1000;
		//}
	}

	// Data Types
	part def Temperature :> Data {
	    // The following two should not type check as attributes must
	    // be typed by attribute definitions and parts must be typed
	    // by part definitions
		//attribute degrees : AADL_Base_Types::Float_32;
   		//part unit : TempUnit;

   		part degrees : AADL_Base_Types::Float_32;
		attribute unit : TempUnit;

		language "GUMBO" /*{
          // data invariant
          //  data invariants are assumed whenever value of the datatype is consumed
          //                  and must be proved whenever a value of the data type is constructed
          invariants
            inv AbsZero:
              degrees >= f32(-459.67);
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