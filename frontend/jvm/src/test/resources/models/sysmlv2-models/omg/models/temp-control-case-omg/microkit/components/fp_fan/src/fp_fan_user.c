#include "../../../include/printf.h"
#include "fp_fan.h"

int lastCmd = 0;

void fp_fan_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);
}

void fp_fan_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);
  int cmd;
  getFanCmd(&cmd);
  if (cmd != lastCmd) {
    lastCmd = cmd;
    printf("%s: Turned the fan %s\n", microkit_name, cmd == 1 ? "on" : "off");
  }
}
