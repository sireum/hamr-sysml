#include "../../../include/printf.h"
#include "tsp_tempSensor.h"

struct base_TempControlAadl_Temperature temp = { .degrees = 72.0, .unit = Fahrenheit };

//int temp = 70;
int incr = 2;

void tsp_tempSensor_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);

  putCurrentTemp(&temp);
}

void tsp_tempSensor_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  temp.degrees = temp.degrees + incr;
  if (temp.degrees > 80) {
    incr = -2;
  } else if (temp.degrees < 70) {
    incr = 2;
  }
  putCurrentTemp(&temp);

  //printf("%s: Sent %d\n", microkit_name, temp);
}
