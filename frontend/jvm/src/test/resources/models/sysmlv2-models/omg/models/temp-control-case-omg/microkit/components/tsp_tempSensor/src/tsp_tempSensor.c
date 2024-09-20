#include "tsp_tempSensor.h"

void tsp_tempSensor_initialize(void);
void tsp_tempSensor_timeTriggered(void);

volatile base_TempControlAadl_Temperature *currentTemp;

#define PORT_FROM_PACER 61

void putCurrentTemp(base_TempControlAadl_Temperature *value) {
  *currentTemp = *value;
}

void init(void) {
  tsp_tempSensor_initialize();
}

void notified(microkit_channel channel) {
  switch (channel) {
    case PORT_FROM_PACER:
      tsp_tempSensor_timeTriggered();
      break;
  }
}
