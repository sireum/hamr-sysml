#include "../../../include/printf.h"
#include "fp_fan.h"

base_TempControlAadl_FanCmd_Type lastCmd = Off;

void fp_fan_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);
}

void fp_fan_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  base_TempControlAadl_FanCmd_Type cmd = getFanCmd();
  if (cmd != lastCmd) {
    lastCmd = cmd;
    printf("%s: Turned the fan %s\n", microkit_name, cmd == 1 ? "on" : "off");
  }
}
