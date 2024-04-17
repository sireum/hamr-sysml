// Isolette.sysml
//
// =====================================================================================================================
//
//   I s o l e t t e     P a c k a g e 
// 
//
//   Top-level system specification including
//
//      - thermostat + temp sensor and heater hardware components
//      - system boundary capturing interactions with the environment
//
// =====================================================================================================================
 
// authors Brian Larson and John Hatcliff
// translated to SysMLv2 by Clint McKenzie

package Isolette {
	
	import Isolette_Data_Model::*;
	import Regulate::*;
	import Monitor::*;
	// Represents AADL properties and component categories with metadata
	import AADL_Properties::*;
	import Isolette_Requirements_Type_2::*;
	
	// top-level system component representing the Isolette
	part def Isolette {
		@System_Properties;
		
		// The following is a property, but I'm not sure how to represent it in SysMLv2
		language "AADL"
			/* properties
			 * 		Actual_Processor_Binding => (reference (isolette_processor)) applies to isolette_process;
			 */
		
		// Subcomponents
		
		part thermostat : Thermostat_Single_Sensor;
		
		part operator_interface : Operator_Interface;
		
		part temperature_sensor : Devices::Temperature_Sensor_System;
		
		part heat_source : Devices::Heat_Source_System;
		
		part isolette_processor: Isolette_Processor;
		
		part isolette_process: Isolette_Process;
		
		// Ports
		
		// models (abstractly) warming of air inside of Isolette enclosure
		port heat_out { out heat : Isolette_Environment::Heat; }
		// models (abstractly) commands given by the operator to Isolette operator interface
		port operator_commands { in interface_Interaction : Isolette_Environment::Interface_Interaction; }
		// models (abstractly) visual information presented to the operator on Isolette operator interface
		port operator_visual_information { out interface_Interaction : Isolette_Environment::Interface_Interaction; }
		// models (abstraction) audio information presented to the operator on Isolette operator interface
		port operator_alarm { out interface_Interaction : Isolette_Environment::Interface_Interaction; }
		// models (abstractly) the sensing of the physical air temperature
		port air_temperature { in temp : Isolette_Data_Model::PhysicalTemp; }
		
		// Connections
		
		// ==== INPUT interface to internal components ==== 	    
		// commands from operator flow into the operator interface
		connection oioc
			connect operator_commands to operator_interface.operator_commands;
		// the environment air temperature (abstract/physical) flows into the temp sensor
		connection a2ts
			connect air_temperature to temperature_sensor.air;

		// ==== OUTPUT interface values from internal components ==== 
		// information (audio and visual) flows from the operator interface to the operator
		connection oiovi
			connect operator_interface.operator_visual_information to operator_visual_information;
		connection oioa
			connect operator_interface.operator_alarm to operator_alarm;

		// heat source's (abstract) output is increase in physical air temperature
		connection hs
			connect heat_source.heat_out to heat_out;

		// ==== INTERNAL communication ==== 
		// sensor sends sensed current temperature to thermostat  
		connection ct
			connect temperature_sensor.current_tempWstatus to thermostat.current_tempWstatus;
		
		// thermostat controls turns the heat source off and on  
		connection hc
			connect thermostat.heat_control to heat_source.heat_control;

		// operator interface communicates desired temperature to thermostat
		connection ldt
			connect operator_interface.lower_desired_tempWstatus to thermostat.lower_desired_tempWstatus;
		connection udt
			connect operator_interface.upper_desired_tempWstatus to thermostat.upper_desired_tempWstatus;

		// operator interface communicates alarm temperature to thermostat
		connection lat
			connect operator_interface.lower_alarm_tempWstatus to thermostat.lower_alarm_tempWstatus;
		connection uat
			connect operator_interface.upper_alarm_tempWstatus to thermostat.upper_alarm_tempWstatus;

		// thermostat communicates regulator status to display on operator interface
		connection rs
			connect thermostat.regulator_status to operator_interface.regulator_status;
		// thermostat communicates monitor status to display on operator interface
		connection ms
			connect thermostat.monitor_status to operator_interface.monitor_status;
		// thermostat communicates current sensed temperature to display on operator interface
		connection dt
			connect thermostat.display_temperature to operator_interface.display_temperature;
		// thermostat communicates alarm information to display on operator interface
		connection al
			connect thermostat.alarm_control to operator_interface.alarm_control;
	}
	
	part def Isolette_Processor {
		@Processor_Properties;
	}

	part def Isolette_Process {
		@Process_Properties;
	}
	
	// =====================================================================================================================
	//
	//   T h e r m o s t a t 
	// 
	//   See Section A-5 and Figure A-2
	//
	// =====================================================================================================================
	// See Figure A-2 for overall architecture and Tables A-4, A-5, and A-6 for data descriptions
	
	part def Thermostat_Single_Sensor {
		@System_Properties;
		
		// ======== INPUTS =======
		// receive current temperature (with status info) from temp sensor
		port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// receive desired temperature range (with status info) from operator interface
		port lower_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// receive alarm temperature range (with status info) from operator interface
		port lower_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }

		// ======== OUTPUTS =========
		// send heat control command to heat source 
		port heat_control { out mode : Isolette_Data_Model::On_Off; }
		// send information to operator interface
		port regulator_status { out status : Isolette_Data_Model::Status; }
		port monitor_status { out status : Isolette_Data_Model::Status; }
		port display_temperature { out temp : Isolette_Data_Model::Temp; }
		port alarm_control { out mode : Isolette_Data_Model::On_Off; }
		
		part regulate_temperature : Regulate::Regulate_Temperature;
		part monitor_temperature : Monitor::Monitor_Temperature;
		
		// ==== INPUT interface to internal components ====
		// current temperature from temp sensor 	
		connection tctm
			connect current_tempWstatus to monitor_temperature.current_tempWstatus;
		connection tctr
			connect current_tempWstatus to regulate_temperature.current_tempWstatus;
		// desired temperature range
		connection tudt
			connect upper_desired_tempWstatus to regulate_temperature.upper_desired_tempWstatus;
		connection tldt
			connect lower_desired_tempWstatus to regulate_temperature.lower_desired_tempWstatus;
		// alarm temperature range
		connection tuat
			connect upper_alarm_tempWstatus to monitor_temperature.upper_alarm_tempWstatus;
		connection tlat
			connect lower_alarm_tempWstatus to monitor_temperature.lower_alarm_tempWstatus;

		// ==== OUTPUT interface from internal components ==== 	
		// display temperature
		connection tdt
			connect regulate_temperature.displayed_temp to display_temperature;
		// subsystem status
		connection trs
			connect regulate_temperature.regulator_status to regulator_status;
		connection tms 
			connect monitor_temperature.monitor_status to monitor_status;
		// alarm control
		connection ta 
			connect monitor_temperature.alarm_control to alarm_control;
		// heat control
		connection thc
			connect regulate_temperature.heat_control to heat_control;
			
		requirement TR : Thermostat_Requirements;
		requirement TR2 : Thermostat_Requirements_2;
	}
	
	// =====================================================================================================================
	//
	//   O p e r a t o r     I n t e r f a c e
	// 
	//   See Section A-5 and Figure A-2
	//
	// =====================================================================================================================

	part def Operator_Interface {
		@System_Properties;
		
		// ======== INPUT from Operator to Operator Interface =======		
		port operator_commands { in interaction : Isolette_Environment::Interface_Interaction; }
		// ======== OUTPUT from Operator Interface to Operator  =======	
		// models (abstractly) visual information presented to the operator on Isolette operator interface
		port operator_visual_information { out interaction : Isolette_Environment::Interface_Interaction; }
		// models (abstraction) audio information presented to the operator on Isolette operator interface
		port operator_alarm { out interaction : Isolette_Environment::Interface_Interaction; }
		// models (abstractly) the sensing of the physical air temperature
		// ==== INPUTS from thermostat to operator interface 
		port regulator_status { in status : Isolette_Data_Model::Status; }
		port monitor_status { in status : Isolette_Data_Model::Status; }
		port display_temperature { in temp : Isolette_Data_Model::Temp; }
		port alarm_control { in mode : Isolette_Data_Model::On_Off; }

		// ==== OUTPUTS from operator interface to thermostat 
		port lower_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port lower_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		
		part oip : Operator_Interface_Process;
		
		connection c1
			connect regulator_status to oip.regulator_status;
		connection c2
			connect monitor_status to oip.monitor_status;
		connection c3
			connect display_temperature to oip.display_temperature;
		connection c4
			connect alarm_control to oip.alarm_control;

		connection c5
			connect oip.lower_desired_tempWstatus to lower_desired_tempWstatus;
		connection c6
			connect oip.upper_desired_tempWstatus to upper_desired_tempWstatus;
		connection c7
			connect oip.lower_alarm_tempWstatus to lower_alarm_tempWstatus;
		connection c8
			connect oip.upper_alarm_tempWstatus to upper_alarm_tempWstatus;
	}
	
	part def Operator_Interface_Process {
		@Process_Properties;
		
		// ==== INPUTS from thermostat to operator interface
		port regulator_status { in status : Isolette_Data_Model::Status; }
		port monitor_status { in status : Isolette_Data_Model::Status; }
		port display_temperature { in temp : Isolette_Data_Model::Temp; }
		port alarm_control { in mode : Isolette_Data_Model::On_Off; }

		// ==== OUTPUTS from operator interface to thermostat 
		port lower_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port lower_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		
		part oit : Operator_Interface_Thread;
		
		connection c1
			connect regulator_status to oit.regulator_status;
		connection c2
			connect monitor_status to oit.monitor_status;
		connection c3
			connect  display_temperature to oit.display_temperature;
		connection c4
			connect alarm_control to oit.alarm_control;

		connection c5
			connect oit.lower_desired_tempWstatus to lower_desired_tempWstatus;
		connection c6
			connect oit.upper_desired_tempWstatus to upper_desired_tempWstatus;
		connection c7
			connect oit.lower_alarm_tempWstatus to lower_alarm_tempWstatus;
		connection c8
			connect oit.upper_alarm_tempWstatus to upper_alarm_tempWstatus;
	}
	
	part def Operator_Interface_Thread {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ==== INPUTS from thermostat to operator interface
		port regulator_status { in status : Isolette_Data_Model::Status; }
		port monitor_status { in status : Isolette_Data_Model::Status; }
		port display_temperature { in temp : Isolette_Data_Model::Temp; }
		port alarm_control { in mode : Isolette_Data_Model::On_Off; }

		// ==== OUTPUTS from operator interface to thermostat 
		port lower_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_desired_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port lower_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
		port upper_alarm_tempWstatus { out tempWstatus : Isolette_Data_Model::TempWstatus; }
	}
}