//Isolette_Requirements.sysml

package Isolette_Requirements {
	
	// ===========================================================================================================
    // 
	//   T h e r m o s t a t   R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Thermostat_Requirements {
		
		requirement REQ_TH_1 {
			doc /* REQ_TH_1: The Thermostat shall set the value of the Heat Control. */
		}
		
		requirement REQ_TH_2 {
			doc /* REQ-TH-2: The Thermostat Function shall set the value of the Regulator Status. */
		}
		
		requirement REQ_TH_3 {
			doc /* REQ-TH-3: The Thermostat shall set the value of the Display Temperature. */
		}
		
		requirement REQ_TH_4 {
			doc /* REQ-TH-4: The Thermostat shall set the value of the Alarm Control. */
		}
		
		requirement REQ_TH_5 {
			doc /* REQ-TH-5: The Thermostat shall set the value of the Monitor Status. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   R e g u l a t e    T e m p e r a t u r e    R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Regulate_Temperature_Requirements {
		
		requirement REQ_RT_1 {
			doc /* The Regulate Temperature Function shall set the value of the Heat Control. */
		}
		
		requirement REQ_RT_2 {
			doc /* REQ-RT-2: The Regulate Temperature Function shall set the value of the Regulator
				Status. */
		}
		
		requirement REQ_RT_3 {
			doc /* REQ-RT-3: The Regulate Temperature Function shall set the value of the Display
				Temperature. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    R e g u l a t o r    I n t e r f a c e    R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Regulator_Interface_Requirements {
		
		requirement REQ_MRI_1 {
			doc /* REQ-MRI-1: If the Regulator Mode is INIT, the Regulator Status shall be set to Init. */
		}
		
		requirement REQ_MRI_2 {
			doc /* REQ-MRI-2: If the Regulator Mode is NORMAL, the Regulator Status shall be set to On. */
		}
		
		requirement REQ_MRI_3 {
			doc /* REQ-MRI-3: If the Regulator Mode is FAILED, the Regulator Status shall be set to Failed. */
		}
		
		requirement REQ_MRI_4 {
			doc /* REQ-MRI-4: If the Regulator Mode is NORMAL, the Display Temperature shall be set
				to the value of the Current Temperature rounded to the nearest integer. */
		}
		
		requirement REQ_MRI_5 {
			doc /* REQ-MRI-5: If the Regulator Mode is not NORMAL, the value of the Display Temperature 
				is UNSPECIFIED. */
		}
		
		requirement REQ_MRI_6 {
			doc /* REQ-MRI-6: If the Status attribute of the Lower Desired Temperature or the Upper
				Desired Temperature is Invalid, the Regulator Interface Failure shall be set to True. */
		}
		
		requirement REQ_MRI_7 {
			doc /* REQ-MRI-7: If the Status attribute of the Lower Desired Temperature and the Upper
				Desired Temperature is Valid, the Regulator Interface Failure shall be set to False. */
		}
		
		requirement REQ_MRI_8 {
			doc /* REQ-MRI-8: If the Regulator Interface Failure is False, the Desired Range shall be set to
				the Desired Temperature Range. */
		}
		
		requirement REQ_MRI_9 {
			doc /* REQ-MRI-9: If the Regulator Interface Failure is True, the Desired Range is UNSPECIFIED. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    R e g u l a t o r    M o d e    R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Regulator_Mode_Requirements {
		
		requirement REQ_MRM_1 {
			doc /* REQ-MRM-1: The Regulator Mode shall be initialized to INIT. */
		}
		
		requirement REQ_MRM_2 {
			doc /* REQ-MRM-2: If the Regulator Mode is INIT and the Regulator Status is Valid, the 
				Regulator Mode shall be set to NORMAL. */
		}
		
		requirement REQ_MRM_3 {
			doc /* REQ-MRM-3: If the Regulator Mode is NORMAL and the Regulator Status is not Valid, 
				the Regulator Mode shall be set to Failed. */
		}
		
		requirement REQ_MRM_4 {
			doc /* REQ-MRM-4: If the Regulator Mode is INIT and doesn't change within the timeout
				period, the Regulator Mode shall be set to Failed. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    H e a t    S o u r c e    R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Heat_Source_Requirements {
		
		requirement REQ_MHS_1 {
			doc /* REQ-MHS-1: If the Regulator Mode is INIT, the Heat Control shall be set to Off. */
		}
		
		requirement REQ_MHS_2 {
			doc /* REQ-MHS-2: If the Regulator Mode is NORMAL and the Current Temperature is less
				than the Lower Desired Temperature, the Heat Control shall be set to On. */
		}
		
		requirement REQ_MHS_3 {
			doc /* REQ-MHS-3: If the Regulator Mode is NORMAL and the Current Temperature is
				greater than the Upper Desired Temperature, the Heat Control shall be set to Off. */
		}
		
		requirement REQ_MHS_4 {
			doc /* REQ-MHS-4: If the Regulator Mode is NORMAL and the Current Temperature is
				greater than or equal to the Lower Desired Temperature and less than or equal to the
				Upper Desired Temperature, the value of the Heat Control shall not be changed. */
		}
		
		requirement REQ_MHS_5 {
			doc /* REQ-MHS-5: If the Regulator Mode is FAILED, the Heat Control shall be set to Off. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M o n i t o r    T e m p e r a t u r e   R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Monitor_Temperature_Requirements {
		
		requirement REQ_MT_1 {
			doc /* REQ-MT-1: The Monitor Temperature Function shall set the value of the Alarm
				Control. */
		}
		
		requirement REQ_MT_2 {
			doc /* REQ-MT-2: The Monitor Temperature Function shall set the value of the Monitor
				Status. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    M o n i t o r   I n t e r f a c e   R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Monitor_Interface_Requirements {
	
		requirement REQ_MMI_1 {
			doc /* REQ-MMI-1: If the Manage Monitor Interface mode is INIT, the Monitor Status 
				shall be set to Init. */
		}
		
		requirement REQ_MMI_2 {
			doc /* REQ-MMI-2: If the Manage Monitor Interface mode is NORMAL, the Monitor Status 
				shall be set to On. */
		}
		
		requirement REQ_MMI_3 {
			doc /* REQ-MMI-3: If the Manage Monitor Interface mode is FAILED, the Monitor Status 
				shall be set to Failed. */
		}
		
		requirement REQ_MMI_4 {
			doc /* REQ-MMI-4: If the Status attribute of the Lower Alarm Temperature or the Upper
				Alarm Temperature is Invalid, the Monitor Interface Failure shall be set to True. */
		}
		
		requirement REQ_MMI_5 {
			doc /* If the Status attribute of the Lower Alarm Temperature and the Upper Alarm 
				Temperature is Valid, the Monitor Interface Failure shall be set to False. */
		}
		
		requirement REQ_MMI_6 {
			doc /* REQ-MMI-6: If the Monitor Interface Failure is False, the Alarm Range variable shall
				be set to the Desired Temperature Range. */
		}
		
		requirement REQ_MMI_7 {
			doc /* REQ-MMI-7: If the Monitor Interface Failure is True, the Alarm Range variable is
				UNSPECIFIED. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    M o n i t o r   M o d e   R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Monitor_Mode_Requirements {
		
		requirement REQ_MMM_1 {
			doc /* REQ-MMM-1: The Monitor Mode shall be initialized to INIT. */
		}
		
		requirement REQ_MMM_2 {
			doc /* REQ-MMM-2: If the Monitor Mode is INIT and the Monitor Status is Valid, the 
				Monitor Mode shall be set to NORMAL. */
		}
		
		requirement REQ_MMM_3 {
			doc /* REQ-MMM-3: If the Monitor Mode is NORMAL and the Monitor Status is not Valid, 
				the Monitor Mode shall be set to Failed. */
		}
		
		requirement REQ_MMM_4 {
			doc /* REQ-MMM-4: If the Monitor Mode is INIT and doesn't change within the timeout
				period, the Monitor Mode shall be set to Failed. */
		}
		
	}
	
	// ===========================================================================================================
    // 
	//   M a n a g e    A l a r m   R e q u i r e m e n t s
	// 
	// ===========================================================================================================
	
	requirement def Manage_Alarm_Requirements {
		
		requirement REQ_MA_1 {
			doc /* REQ-MA-1: If the Monitor Mode is INIT, the Alarm Control shall be set to Off. */
		}
		
		requirement REQ_MA_2 {
			doc /* REQ-MA-2: If the Monitor Mode is NORMAL and the Current Temperature is less than
				the Lower Alarm Temperature or greater than the Upper Alarm Temperature, the Alarm
				Control shall be set to On. */
		}
		
		requirement REQ_MA_3 {
			doc /* REQ-MA-3: If the Monitor Mode is NORMAL and the Current Temperature is greater
				than or equal to the Lower Alarm Temperature and less than the Lower Alarm
				Temperature +0.5°, or the Current Temperature is greater than the Upper Alarm
				Temperature -0.5° and less than or equal to the Upper Alarm Temperature, the value of
				the Alarm Control shall not be changed. */
		}
		
		requirement REQ_MA_4 {
			doc /* REQ-MA-4: If the Monitor Mode is NORMAL and the value of the Current Temperature 
			 	is greater than or equal to the Lower Alarm Temperature +0.5° and less than or 
			 	equal to the Upper Alarm Temperature -0.5°, the Alarm Control shall be set to Off. */
		}
		
		requirement REQ_MA_5 {
			doc /* If the Monitor Mode is FAILED, the Alarm Control shall be set to On. */
		}
		
	}
	
}