// Isolette_Environment.sysml
//
// =====================================================================================================================
//
//   I s o l e t t e     E n v i r o n m e n t     P a c k a g e 
// 
//
//   Modeling of environment for Isolette
//
//   Note:  These models contribute nothing to the Isolette implementation.
//          They are included here to support model-based hazard analysis activities 
//          as well support Concept of Operations and Use Case modeling 
//
// =====================================================================================================================

package Isolette_Environment {
	
	import AADL_Properties::*;
	import Isolette_Requirements_Type_2::*;
	import ScalarValues::Boolean;
	
	// air inside room (make air connections flows?)
	part def Air {
		@System_Properties;
		
		// Ports
		
		// warming of air by heat source
		port heat_in { in heat : Heat; }
		// heat-loss of incubator air to room
		port heat_out { out heat : Heat; }
		// air contact with infant
		// TODO: This is currently modeled as an IN/OUT port, which might cause problems
		// with the EMv2/hazard analysis
		port infant_interaction { inout air_Interaction : Air_Interaction; }
		//actual air temperature that the sensor measures
		port air_temperature { out temp : Isolette_Data_Model::PhysicalTemp; }
	}
	
	// nurse controlling room
	abstract part def Nurse {
		
		// Ports
		
		// models (abstractly) visual information presented to the operator on Isolette operator interface
		port operator_visual_information { in interface_Interaction : Interface_Interaction; }
		// models (abstraction) audio information presented to the operator on Isolette operator interface
		port operator_alarm { in interface_Interaction : Interface_Interaction; }
		// models (abstractly) commands given by the operator to Isolette operator interface
		port operator_commands { out interface_Interaction : Interface_Interaction; }
	}
	
	// infant in room
	abstract part def Infant {
		port air_interaction { inout air_Interaction : Air_Interaction; }
	}
	
	// heat emitted by source to warm air
	part def Heat;
	
	// data that flows between operator and control panel
	part def Interface_Interaction;
	
	// warming of air by heat source, or of infant by air
	part def Air_Interaction;
	
	// nursery room that contains room, infant, nurse, and air
	part def Isolette_Environment {
		@System_Properties;
		
		// models heat-loss of incubator to (presumably) cooler room
		port heat_loss {out heat : Heat;}
	}
	
	// implementation of Room that contains incubator, infant, nurse, and air
	part Isolette_Environment_Normal_Operation : Isolette_Environment {
		
		// Subcomponents
		
		part anIncubator : Isolette::Isolette;
		part theAir : Air;
		part aNurse : Nurse;
		part anInfant : Infant;
		
		// Connections
		// air with infant interaction
		connection a2i
			connect theAir.infant_interaction to anInfant.air_interaction;
		// heating source warming air interaction
		connection hs2a
			connect anIncubator.heat_out to theAir.heat_in;
		connection tmp
			connect theAir.air_temperature to anIncubator.air_temperature;
		// heat-loss
		connection hl
			connect  theAir.heat_out to heat_loss;
		// nurse with control-panel interaction
		connection n2oivi
			connect anIncubator.operator_visual_information to aNurse.operator_visual_information;
		connection n2ioa
			connect anIncubator.operator_alarm to aNurse.operator_alarm;
		connection n2ioc
			connect aNurse.operator_commands to anIncubator.operator_commands;
			
		message operator_hears_alarm
			from anIncubator to aNurse;
		message operator_reads_visuals
			from anIncubator to aNurse;
		message operator_sends_commands	
			from aNurse to anIncubator;
	}
	
	// Use case examples
	
	// Represents the "Normal Operation" use case in three ways
	
	// Describes the normal operation of the Isolette
	// Precondition: Infant is ready to be placed in the Isolette
	// 	             Isolette and Thermostat are turned off
	// Postcondition: Infant is removed from the Isolette
	// 				  Isolette and Thermostat are turned off
	use case 'A.2.1: Normal Operation' {
		subject nurse : Nurse;
		actor isolette : Isolette::Isolette;
		actor infant : Infant;
		objective {
			/* The infant's needs are properly met by the Isolette. */
		}
		action 'Nurse turns on Isolette';
		action 'Isolette turns on Thermostat';
		action 'Thermostat initializes and enters normal mode' {
			requirement 'A.5.1.2' : Isolette_Requirements_Type_2::Manage_Regulator_Mode_Requirements_2;
			requirement 'A.5.2.2' : Isolette_Requirements_Type_2::Manage_Monitor_Mode_Requirements_2;
		}
		action 'Nurse configures Isolette for needs of Infant';
		action 'Nurse waits until Current Temperature within desired range' {
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		action 'Nurse places Infant in Isolette';
		action 'Isolette maintains Desired Temperature';
		action 'Nurse confirms Current Temperature within Desired Temperature Range during rounds' {
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		action 'Nurse removes Infant';
		action 'Nurse turns off Isolette';
		action 'Isolette turns off Thermostat';
	}
	
	
	// Describes actors for each step
	
	// Describes the normal operation of the Isolette
	// Precondition: Infant is ready to be placed in the Isolette
	// 	             Isolette and Thermostat are turned off
	// Postcondition: Infant is removed from the Isolette
	// 				  Isolette and Thermostat are turned off
	use case 'A.2.1: Normal Operation 2' {
		actor nurse : Nurse;
		actor isolette : Isolette::Isolette;
		actor infant : Infant;
		objective {
			/* The infant's needs are properly met by the Isolette. */
		}
		use case 'Nurse turns on Isolette' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
		}
		use case 'Isolette turns on Thermostat' {
			actor isolette : Isolette::Isolette;
		}
		use case 'Thermostat initializes and enters normal mode' {
			actor isolette : Isolette::Isolette;
			
			requirement 'A.5.1.2' : Isolette_Requirements_Type_2::Manage_Regulator_Mode_Requirements_2;
			requirement 'A.5.2.2' : Isolette_Requirements_Type_2::Manage_Monitor_Mode_Requirements_2;
		}
		use case 'Nurse configures Isolette for needs of Infant' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
		}
		use case 'Nurse waits until Current Temperature within desired range' {
			actor isolette : Isolette::Isolette;
			actor nurse : Nurse;
			
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		use case 'Nurse places Infant in Isolette' {
			actor nurse : Nurse;
			actor infant : Infant;
			actor isolette : Isolette::Isolette;
		}
		use case 'Isolette maintains Desired Temperature' {
			actor isolette : Isolette::Isolette;
		}
		use case 'Nurse confirms Current Temperature within Desired Temperature Range during rounds' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
			
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		use case 'Nurse removes Infant' {
			actor nurse : Nurse;
			actor infant : Infant;
		}
		use case 'Nurse turns off Isolette' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
		}
		use case 'Isolette turns off Thermostat' {
			actor isolette : Isolette::Isolette;
		}
	}
	
	
	// Includes messages
	
	// Describes the normal operation of the Isolette
	// Precondition: Infant is ready to be placed in the Isolette
	// 	             Isolette and Thermostat are turned off
	// Postcondition: Infant is removed from the Isolette
	// 				  Isolette and Thermostat are turned off
	use case 'A.2.1: Normal Operation 3' {
		actor nurse : Nurse;
		actor isolette : Isolette::Isolette;
		actor infant : Infant;
		objective {
			/* The infant's needs are properly met by the Isolette. */
		}
		use case 'Nurse turns on Isolette' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
			message Turn_On_Message
				from nurse to isolette;
		}
		use case 'Isolette turns on Thermostat' {
			actor isolette : Isolette::Isolette;
			message Initialize_Message
				from isolette to isolette.thermostat;
		}
		use case 'Thermostat initializes and enters normal mode' {
			actor isolette : Isolette::Isolette;
			
			requirement 'A.5.1.2' : Isolette_Requirements_Type_2::Manage_Regulator_Mode_Requirements_2;
			requirement 'A.5.2.2' : Isolette_Requirements_Type_2::Manage_Monitor_Mode_Requirements_2;
			
			// Possible Exception
			use case 'Exception Case 1'{
				actor isolette : Isolette::Isolette;
				actor nurse : Nurse;
				// More to come
			}
		}
		use case 'Nurse configures Isolette for needs of Infant' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
			message Configuration_Message
				from nurse to isolette.operator_interface;
		}
		use case 'Nurse waits until Current Temperature within desired range' {
			actor isolette : Isolette::Isolette;
			actor nurse : Nurse;
			message Temperature_In_Range_Message
				from isolette.operator_interface to nurse;
			
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		use case 'Nurse places Infant in Isolette' {
			actor nurse : Nurse;
			actor infant : Infant;
			actor isolette : Isolette::Isolette;
		}
		use case 'Isolette maintains Desired Temperature' {
			actor isolette : Isolette::Isolette;
		}
		use case 'Nurse confirms Current Temperature within Desired Temperature Range during rounds' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
			message Current_Temperature_Message
				from isolette.operator_interface to nurse;
			
			requirement 'A.5.1.1' : Isolette_Requirements_Type_2::Manage_Regulator_Interface_Requirements_2;
		}
		use case 'Nurse removes Infant' {
			actor nurse : Nurse;
			actor infant : Infant;
		}
		use case 'Nurse turns off Isolette' {
			actor nurse : Nurse;
			actor isolette : Isolette::Isolette;
			message Turn_Off_Message
				from nurse to isolette;
		}
		use case 'Isolette turns off Thermostat' {
			actor isolette : Isolette::Isolette;
			message Turn_Off_Message
				from isolette to isolette.thermostat;
		}
	}
}