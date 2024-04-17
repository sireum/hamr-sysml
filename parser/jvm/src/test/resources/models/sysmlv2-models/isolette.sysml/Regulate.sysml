// Regulate.sysml

package Regulate {
	
	import Isolette_Properties::*;
	import Isolette_Data_Model::*;
	import AADL_Properties::*;
	import Isolette_Requirements::*;
	
	// ===========================================================================================================
    //
    //   R e g u l a t e    T e m p e r a t u r e    P r o c e s s
    //
    // @description process that holds threads that regulate air temperature
    // A.5.1 REGULATE TEMPERATURE FUNCTION
    // 
    // ===========================================================================================================
	
	// process that holds threads that regulate air temperature
	part def Regulate_Temperature {
		@Process_Properties;
		
		// ===== INPUTs (monitored variables) ...based in part on Figure A.2 and Table A.5 
		//  desired temperature range from operator interface 		
		lower_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		upper_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		//  actual temperature from temp sensor
		current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		     
		// ===== OUTPUTs (controlled variables)  ...based in part on Figure A.2 and Table A.6
		// status of regulate temperature function as reported to operator interface
		regulator_status { out status : Isolette_Data_Model::Status; }
		// value of current temperature to display on operator interface
		displayed_temp { out temp : Isolette_Data_Model::Temp; }
		// actuation command to heater
		heat_control { out mode : Isolette_Data_Model::On_Off; }
		
		//thread to manage the interactions between with operator interface and the rest of the regulator process
		part manage_regulator_interface : Manage_Regulator_Interface;
		// thread to manage the heat source.  Receives the desired range, current determine, and determines whether or not to
		//send an on/off command to the heat source
		part manage_heat_source : Manage_Heat_Source;
		//thread to manage the regular mode. Based inputs describing the failure status of the interface, subsystem internals,
		//and temperature  status,  determines the mode of the regulator process (i.e., if the mode is Init, Normal, or Failed modes)
		part manage_regulator_mode : Manage_Regulator_Mode;
		
		// ==== INPUT INTERFACE values to internal components ==== 		
	    // send incoming desired temperature range to regulator interface manager
	    connection rudt 
	    	connect upper_desired_tempWstatus to manage_regulator_interface.upper_desired_tempWstatus;
	    connection rldt
	    	connect lower_desired_tempWstatus to manage_regulator_interface.lower_desired_tempWstatus;
	
	    // send incoming current temperature (with status value)
	    //   ...to interface manager
	    connection rcti 
	    	connect current_tempWstatus to manage_regulator_interface.current_tempWstatus;
	    //   ...and to heat source manager
	    connection rcth 
			connect current_tempWstatus to manage_heat_source.current_tempWstatus;
	    //   ...and to mode manager
	    connection rctm
	    	connect current_tempWstatus to manage_regulator_mode.current_tempWstatus;
	   
	    // ==== OUTPUT INTERFACE values from internal components ==== 
	    // output temperature to display on operator interface
	    connection rdt
			connect manage_regulator_interface.displayed_temp to displayed_temp;
	    // output regulator subsystem status to operator interface  
	    connection rrs
			connect manage_regulator_interface.regulator_status to regulator_status;
	    // output on/off command to heat source
	    connection rhc
	    	connect manage_heat_source.heat_control to heat_control;
	  
	    // ==== INTERNAL communication ====
	    // send validated desired temperature from interface manager to heat source manager
	    connection mudt 
			connect manage_regulator_interface.upper_desired_temp to manage_heat_source.upper_desired_temp;
	    connection mldt
	   		connect manage_regulator_interface.lower_desired_temp to manage_heat_source.lower_desired_temp;
	  
	    // send regulator mode from mode manager 
	    //  ... to interface manager
	    connection rrmi 
	    	connect manage_regulator_mode.regulator_mode to manage_regulator_interface.regulator_mode;
	    //  ... and to heat source manager
	    connection rrmh 
	    	connect manage_regulator_mode.regulator_mode to manage_heat_source.regulator_mode;
	
	    // send regulator interface failure to mode manager
	    connection rif
	    	connect manage_regulator_interface.interface_failure to manage_regulator_mode.interface_failure;
	    	
	    requirement RT : Regulate_Temperature_Requirements;
	}
	
	// ===========================================================================================================
	//
	//   M a n a g e    R e g u l a t o r   I n t e r f a c e   T h r e a d
	//
	//  
	// A.5.1.1 Manage Regulator Interface Function
	// 
	// ===========================================================================================================

	part def Manage_Regulator_Interface {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ==== INPUTS ====	(see Figure A.3)
	    //  desired temperature from operator interface
	    port upper_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
	    port lower_desired_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
  	    // current temperature from temp sensor
	    port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
	    // current mode of regulator subsystem
	    port regulator_mode { in mode : Isolette_Data_Model::Regulator_Mode; }
	  
	    // ==== OUTPUTS ====	(see Figure A.3)
	    // validated ("valid" status confirmed) desired temperature range from operator interface
	    port upper_desired_temp { out temp : Isolette_Data_Model::Temp; }
	    port lower_desired_temp { out temp : Isolette_Data_Model::Temp; }
	    // current temperature to display on operator interface
	    port displayed_temp { out temp : Isolette_Data_Model::Temp; }
	    // overall status of regulator subsystem sent to operator interface
	    port regulator_status { out status : Isolette_Data_Model::Status; }
	    // flag indicating improper inputs; sent to determine overall subsystem mode 
	    port interface_failure { out flag : Isolette_Data_Model::Failure_Flag; }
	    
	    requirement MRI : Manage_Regulator_Interface_Requirements;
	    
	    // Flows
		// I'm not sure anything in SysMLv2 exactly corresponds to flows
		// For now, they will be represented with a Textual Representation
		language "AADL"
			/* flows
			 *	// ==== desired temps function flows ====
			 *	//   summary: this is a pair-wise propagation of data that just strips
			 *	//     of the status tag
			 *	mri_dt1: flow path upper_desired_tempWstatus -> upper_desired_temp;	
			 *	// these two flows are independent 
			 *	mri_dt2: flow path lower_desired_tempWstatus -> lower_desired_temp;
			 *
			 *	// ==== displayed temp function flows ====
			 *	//   summary: the displayed temp depends both on current temp/status (data dependence)
			 *	//            as well as mode (control dependence)
			 *	// data dependence
			 *	//    current temperature is the value to be displayed in normal mode
		     *	mri_dspt1: flow path current_tempWstatus -> displayed_temp;
		     *	// control dependence
		     *	//    in modes other than normal mode, the displayed value is unspecified
		     *	mri_dspt2: flow path regulator_mode -> displayed_temp;
		     *	// uncaptured at the moment is the notion (from reqs) that if
		     *	// regulator mode is init or failed, then the display_temp is
		     *	// unspecified.
		     *
		     *	// ==== regulator status function ====
		     *	//   summary: regulator mode is just propagated (essentially renamed)
		     *	//            to regulator status
		     *	// data dependence 	
			 *	mri_rs: flow path regulator_mode -> regulator_status;
			 *
			 *	// interface failure function 	
			 *	mri_if1: flow path upper_desired_tempWstatus -> interface_failure;
		     *	mri_if2: flow path lower_desired_tempWstatus -> interface_failure;
			 */
	}
	
	// ===========================================================================================================
	//
	//   M a n a g e    R e g u l a t o r   M o d e   T h r e a d
	//
	//  
	//   A.5.1.2 Manage Regulator Mode Function
	// 
	// ===========================================================================================================

	part def Manage_Regulator_Mode {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ==== INPUTS ========		
  	  	// current temperature from temp sensor
  		port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
  		// status of operator interface interactions
  		port interface_failure { in flag : Isolette_Data_Model::Failure_Flag; }
  		// internal status of regulator
  		port internal_failure { in flag : Isolette_Data_Model::Failure_Flag; }
  
  		// ==== OUTPUTS ========		
  		// mode of regulator (Init, Normal, Failed)
  		port regulator_mode { out mode : Isolette_Data_Model::Regulator_Mode; }
  		
  		requirement MRM : Manage_Regulator_Mode_Requirements;
  		
  		// Flows
		// I'm not sure anything in SysMLv2 exactly corresponds to flows
		// For now, they will be represented with a Textual Representation
		language "AADL"
  			/*flows
	  		*	// ==== regulator_mode function flows ====
	  		*	//   summary: all three inputs are used to determine regulator_mode
	  		*	mrm_ctws2rm: flow path current_tempWstatus -> regulator_mode;
	  		*	mrm_iff2rm: flow path interface_failure -> regulator_mode;
	  		*	mrm_itf2rm: flow path internal_failure -> regulator_mode;	
	  		*/
	}
	
	// ===========================================================================================================
	//
	//   M a n a g e    H e a t    S o u r c e     T h r e a d
	//
	//  
	//    A.5.1.3 Manage Heat Source Function
	// 
	// ===========================================================================================================
	
	part def Manage_Heat_Source {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ======== INPUTS =======
		// current temperature (from temp sensor)
		port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// lowest and upper bound of desired temperature range
 		port lower_desired_temp { in temp : Isolette_Data_Model::Temp; }
		port upper_desired_temp { in temp : Isolette_Data_Model::Temp; }
		// subsystem mode 
 		port regulator_mode { in mode : Isolette_Data_Model::Regulator_Mode; }
    
		// ======== OUTPUTS =======
		// command to turn heater on/off (actuation command)
  		port heat_control { out mode : Isolette_Data_Model::On_Off; }
  		
  		requirement MHS : Manage_Heat_Source_Requirements;
		
		// Flows
		// I'm not sure anything in SysMLv2 exactly corresponds to flows
		// For now, they will be represented with a Textual Representation
		language "AADL"
  		   /*flows
	  		*	mhs_fp1: flow path current_tempWstatus -> heat_control;
	  		*	mhs_fp2: flow path lower_desired_temp -> heat_control;
	  		*	mhs_fp3: flow path upper_desired_temp -> heat_control;
	  		*	mhs_fp4: flow path regulator_mode -> heat_control;	
	  		*/
	}
	
}