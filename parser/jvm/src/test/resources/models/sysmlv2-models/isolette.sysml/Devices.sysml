// Devices.sysml

package Devices {
	
	import Isolette_Data_Model::*;
	import AADL_Properties::*;
	import SI::s;
	
	//device Detect_Regulator_Failure
	//features
	//  internal_failure : out data port Isolette_Data_Model::Failure_Flag.impl;
	//end Detect_Regulator_Failure;
	//
	//device implementation Detect_Regulator_Failure.impl
	//end detect_regulator_failure.impl;
	//
	//
	//--A.5.2.4 Detect Monitor Failure Function
	//--this is where error models signal active threads an 
	//--internal thermostat monitor failure has been detected
	//device Detect_Monitor_Failure
	//features
	//  internal_failure : out data port Isolette_Data_Model::Failure_Flag.impl;
	//end Detect_Monitor_Failure;
	//
	//device implementation Detect_Monitor_Failure.impl
	//end Detect_Monitor_Failure.impl;
	//
	//device Temperature_Sensor
	//features
	//  air : in data port Isolette_Data_Model::PhysicalTemp.impl;
	//  current_tempWstatus : out data port Isolette_Data_Model::TempWstatus.impl;
	//properties
	//   Dispatch_Protocol => Periodic; -- jab
	//   Period => 1sec; -- jab    
	//end Temperature_Sensor;
	//
	//device implementation Temperature_Sensor.impl
	//end Temperature_Sensor.impl;
	//
	//device Heat_Source
	//features
	//  heat_control : in data port Isolette_Data_Model::On_Off;
	//  heat_out : out data port Isolette_Environment::Heat;
	//properties
	//   Dispatch_Protocol => Periodic; -- jab
	//   Period => 1sec; -- jab  
	//end Heat_Source;
	//
	//device implementation Heat_Source.impl
	//end Heat_Source.impl;
	//-------------------------------------------------------------------------
	// temperature_sensor
	//-------------------------------------------------------------------------
	part def Temperature_Sensor_System {
		@System_Properties;
		
		port air { in temp : Isolette_Data_Model::PhysicalTemp; }
		port current_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		
		part cpi : Temperature_Sensor_Process;
		
		connection c1
			connect air to cpi.air;
		connection c2
			connect cpi.current_tempWstatus to current_tempWstatus;
	}
	
	part def Temperature_Sensor_Process {
		@Process_Properties;
		
		port air { in temp : Isolette_Data_Model::PhysicalTemp; }
		port current_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		
		part thermostat : Temperature_Sensor;
		
		//Connections
		connection c1
			connect air to thermostat.air;
		connection c2
			connect thermostat.current_tempWstatus to current_tempWstatus;
	}
	
	part def Temperature_Sensor {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = 1[s];
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		port air { in temp : Isolette_Data_Model::PhysicalTemp; }
		port current_tempWstatus {in tempWstatus : Isolette_Data_Model::TempWstatus; }
	}
	
	//-------------------------------------------------------------------------
	// heat_source
	//-------------------------------------------------------------------------
	part def Heat_Source_System {
		@System_Properties;
		
		port heat_control { in control : Isolette_Data_Model::On_Off; }
		port heat_out { out heat : Isolette_Environment::Heat; }
		
		part cpi : Heat_Source_Process;
		
		connection c3
			connect heat_control to cpi.heat_control;
		connection c4
			connect cpi.heat_out to heat_out;
	}
	
	part def Heat_Source_Process {
		@Process_Properties;
		
		port heat_control { in control : Isolette_Data_Model::On_Off; }
		port heat_out { out heat : Isolette_Environment::Heat; }
		
		part heat_controller : Heat_Source;
		
		connection c3
			connect heat_control to heat_controller.heat_control;
		connection c4
			connect heat_controller.heat_out to heat_out;
	}
	
	part def Heat_Source {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = 1[s];
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		port heat_control { in control : Isolette_Data_Model::On_Off; }
		port heat_out { out heat : Isolette_Environment::Heat; }
	}
	
	//-------------------------------------------------------------------------
	// detect regulator failure
	//-------------------------------------------------------------------------
	part def Detect_Regulator_Failure_System {
		@System_Properties;
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
		
		part p : Detect_Regulator_Failure_Process;
		
		connection c1
			connect p.internal_failure to internal_failure;
	}
	
	part def Detect_Regulator_Failure_Process {
		@Process_Properties;
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
		
		part detect_regulator_failure_thread : Detect_Regulator_Failure;
		
		connection c1
			connect detect_regulator_failure_thread.internal_failure to internal_failure;
	}
	
	part def Detect_Regulator_Failure {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = 1[s];
		}
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
	}
	
	//-------------------------------------------------------------------------
	// detect monitor failure
	//-------------------------------------------------------------------------
	part def Detect_Monitor_Failure_System {
		@System_Properties;
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
		
		part p : Detect_Monitor_Failure_Process;
		
		connection c1
			connect p.internal_failure to internal_failure;
	}
	
	part def Detect_Monitor_Failure_Process {
		@Process_Properties;
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
		
		part monitor_failure_thread : Detect_Monitor_Failure;
		
		connection c1
			connect monitor_failure_thread.internal_failure to internal_failure;
	}
	
	part def Detect_Monitor_Failure {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = 1[s];
		}
		
		port internal_failure { out failure : Isolette_Data_Model::Failure_Flag; }
	}
}