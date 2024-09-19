#include "../../../include/printf.h"
#include "tcp_tempControl.h"

int on = 1;
int off = 0;

void tcp_tempControl_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);
}

void tcp_tempControl_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  int value = 0;
  getCurrentTemp(&value);

  if (value > 80) {
    putFanCmd(&on);
    printf("%s: sent fan on command\n", microkit_name);
  } else if (value < 70) {
    putFanCmd(&off);
    printf("%s: sent fan off command\n", microkit_name);
  }

}
