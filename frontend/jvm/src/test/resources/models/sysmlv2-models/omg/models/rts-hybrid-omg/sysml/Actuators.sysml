package Actuators {
	
	private import AADL::*;
	private import AADL_Project::*;
	private import AADL_Project::Time_Units::*;
	
	part def ActuatorsMockProcess :> Process {
		part actuatorsMockThread : ActuatorsMockThread;
		
        in port tempPressureActuate : DataPort { in :> type : Base_Types::Boolean; }
		in port saturationActuate : DataPort { in :> type : Base_Types::Boolean; }
		
		connection tPA : PortConnection connect tempPressureActuate to actuatorsMockThread.tempPressureActuate;
		connection sA : PortConnection connect saturationActuate to actuatorsMockThread.saturationActuate;
	}
	
	part def ActuatorsMockThread :> Thread {
		in port tempPressureActuate : DataPort { in :> type : Base_Types::Boolean; }
		in port saturationActuate : DataPort { in :> type : Base_Types::Boolean; }

		attribute :>> Dispatch_Protocol = Supported_Dispatch_Protocols::Periodic;
		attribute :>> Period = 1000 [millisecond];
		
		//@Properties {
		//	Dispatch_Protocol = Periodic;
		//	Period = 1000;
		//}
	}
	
}