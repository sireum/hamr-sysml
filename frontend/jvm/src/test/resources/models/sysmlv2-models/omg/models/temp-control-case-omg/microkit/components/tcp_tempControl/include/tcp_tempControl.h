#include <stdint.h>
#include <microkit.h>
#include <types.h>

void getCurrentTemp(base_TempControlAadl_Temperature *value);
base_TempControlAadl_FanAck_Type getFanAck();
void getSetPoint(base_TempControlAadl_SetPoint *value);
void putFanCmd(base_TempControlAadl_FanCmd_Type value);
