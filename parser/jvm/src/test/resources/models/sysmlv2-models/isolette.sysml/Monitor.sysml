// Monitor.sysml
package Monitor {
	
	import Isolette_Data_Model::*;
	import AADL_Properties::*;
	import Isolette_Properties::*;
	import Isolette_Requirements::*;
	
	//A.5.2 Monitor Temperature Function
	// thread to manage the interactions between with operator interface and the rest
    // of the monitor process
	part def Monitor_Temperature {
		@Process_Properties;
		
		// ===== INPUTs (monitored variables) ...based in part on Figure A.2 and Table A.5 
	    // value of current temp (with status flag) from air temp sensor
	    port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
	    // operator-specific alarm range for temperature
	    lower_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
	    upper_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
	    
	    // ===== OUTPUTs (controlled variables)  ...based in part on Figure A.2 and Table A.6
	    // control signal (on/off) to alarm component 
	    port alarm_control { out control : Isolette_Data_Model::On_Off; }
	    // enumerated type describing status/mode of monitor (Init, On/operating, or Failed)
	    port monitor_status { out status : Isolette_Data_Model::Status; }
	    
	    //thread for manage alarm implementation
	    part manage_alarm : Manage_Alarm;
	    //thread for monitor interface implementation
	    part manage_monitor_interface : Manage_Monitor_Interface;
	    //thread for monitor mode implementation
	    part manage_monitor_mode : Manage_Monitor_Mode;
	    
	    
	    // ==== INPUT INTERFACE values to internal components ====
	    // send incoming alarm temperature range to monitor interface manager
	    connection muat
	    	connect upper_alarm_tempWstatus to manage_monitor_interface.upper_alarm_tempWstatus;
	    connection mlat
	    	connect lower_alarm_tempWstatus to manage_monitor_interface.lower_alarm_tempWstatus;
	  
	    // send incoming current temperature (with status value)
	    //   ...to interface manager
	    connection mcti
	    	connect current_tempWstatus to manage_monitor_interface.current_tempWstatus;
  	    //   ...to alarm manager (controls alarm)
	    connection mcta
	    	connect current_tempWstatus to manage_alarm.current_tempWstatus;
	    //   ...to mode manager
	    connection mctm
	    	connect current_tempWstatus to manage_monitor_mode.current_tempWstatus;
	
	    // ==== OUTPUT INTERFACE values from internal components ==== 
	    // send alarm actuation command (on/off) 
	    connection malrm
	    	connect manage_alarm.alarm_control to alarm_control;
	    // send status of monitor subsystem  to operator interface
	    connection mms
	    	connect manage_monitor_interface.monitor_status to monitor_status;
	  
	    // ==== INTERNAL communication ====
	    // send validated alarm range from interface manager to alarm manager (controller)
   	    connection maul
   	    	connect manage_monitor_interface.upper_alarm_temp to manage_alarm.upper_alarm_temp;
	    connection mall
	    	connect manage_monitor_interface.lower_alarm_temp to manage_alarm.lower_alarm_temp;
	
	    // send monitor mode from mode manager 
	    //  ... to interface manager
	    connection mmmi
	    	connect manage_monitor_mode.monitor_mode to manage_monitor_interface.monitor_mode;
	    //  ... to alarm manager
	    connection mmma
	    	connect manage_monitor_mode.monitor_mode to manage_alarm.monitor_mode;
	  
	    // send regulator interface failure to mode manager
	    connection intff
	    	connect manage_monitor_interface.interface_failure to manage_monitor_mode.interface_failure;
	    	
	    requirement MT : Monitor_Temperature_Requirements;
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    M o n i t o r   I n t e r f a c e   T h r e a d
	// 
	// 
	// A.5.2.1 Manage Monitor Interface Function
	// 
	// ===========================================================================================================
	
	part def Manage_Monitor_Interface {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ==== INPUTS ====	(see Figure A.5)
		//  operator specified alarm range
		port upper_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		port lower_alarm_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// current temperature from temp sensor
		port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// current mode of monitor subsystem (init, normal or failed)
		port monitor_mode { in mode : Isolette_Data_Model::Monitor_Mode; }
		
		// ==== OUTPUTS ====	(see Figure A.5)  
		// validated ("valid" status confirmed & removed) alarm temperature range from operator interface
		port upper_alarm_temp { out temp : Isolette_Data_Model::Temp; }
		port lower_alarm_temp { out temp : Isolette_Data_Model::Temp; }
		// overall status of monitor subsystem sent to operator interface
	    port monitor_status { out status : Isolette_Data_Model::Status; }
	    // flag indicating improper inputs; sent to determine overall subsystem mode 
		port interface_failure { out flag : Isolette_Data_Model::Failure_Flag; }
		
		requirement MMI : Manage_Monitor_Interface_Requirements;
	}
	
	// ===========================================================================================================
	//
	//   M a n a g e    M o n i t o r   M o d e   T h r e a d
	//
	//  
	//   A.5.2.2 Manage Monitor Mode Function
	// 
	// ===========================================================================================================
	
	// A.5.2.2 Manage Monitor Mode Function
	
	part def Manage_Monitor_Mode {
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
		// in case of internal failure, a flag is raised
		port internal_failure { in flag : Isolette_Data_Model::Failure_Flag; }
		   
		// ==== OUTPUTS ========		
		// mode of regulator (Init, Normal, Failed)   
		port monitor_mode { out mode : Isolette_Data_Model::Monitor_Mode; }
		
		requirement MMM : Manage_Monitor_Mode_Requirements;
	}
	
	// ===========================================================================================================
	//
	//   M a n a g e    A l a r m    T h r e a d
	//
	//  
	//    A.5.2.3 Manage Alarm Function
	// 
	// ===========================================================================================================
	
	
	// A.5.2.3 Manage Alarm Function
	// The Manage Alarm Function turns the Alarm Control on when the Current
	// Temperature of the Room falls below or rises above the Alarm Temperature Range.
	
	part def Manage_Alarm {
		@Thread_Properties {
			Dispatch_Protocol = Periodic;
			Period = Isolette_Properties::ThreadPeriod;
			Stack_Size = Isolette_Properties::StackSize;
		}
		
		// ======== INPUTS =======
		// current temperature (from temp sensor)
		port current_tempWstatus { in tempWstatus : Isolette_Data_Model::TempWstatus; }
		// lowest and upper bound of operator specified alarm temperature range
		port lower_alarm_temp { in temp : Isolette_Data_Model::Temp; }
		port upper_alarm_temp { in temp : Isolette_Data_Model::Temp; }
		// subsystem mode
		port monitor_mode { in mode : Isolette_Data_Model::Monitor_Mode; }
		  
		// ======== OUTPUTS =======
		// command to turn alarm on/off (actuation command)  
		port alarm_control { out mode : Isolette_Data_Model::On_Off; }
		
		requirement MA : Manage_Alarm_Requirements;
	}
}