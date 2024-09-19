#include "../../../include/printf.h"
#include "tsp_tempSensor.h"

int temp = 70;
int incr = 2;

void tsp_tempSensor_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);

  putCurrentTemp(&temp);
}

void tsp_tempSensor_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  temp = temp + incr;
  if (temp > 80) {
    incr = -2;
  } else if (temp < 70) {
    incr = 2;
  }
  putCurrentTemp(&temp);

  //printf("%s: Sent %d\n", microkit_name, temp);
}
