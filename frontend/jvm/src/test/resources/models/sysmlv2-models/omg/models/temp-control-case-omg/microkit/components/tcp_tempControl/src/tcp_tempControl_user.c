#include "../../../include/printf.h"
#include "tcp_tempControl.h"

void tcp_tempControl_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);
}

void tcp_tempControl_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  struct base_TempControlAadl_Temperature value;
  getCurrentTemp(&value);

  if (value.degrees > 80) {
    putFanCmd(On);
    printf("%s: sent fan on command\n", microkit_name);
  } else if (value.degrees < 70) {
    putFanCmd(Off);
    printf("%s: sent fan off command\n", microkit_name);
  }

}
