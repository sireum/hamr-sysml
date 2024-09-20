#include "oip_operatorInterface.h"

void oip_operatorInterface_initialize(void);
void oip_operatorInterface_timeTriggered(void);

volatile base_TempControlAadl_Temperature *currentTemp;
volatile base_TempControlAadl_SetPoint *setPoint;

#define PORT_FROM_PACER 58

void getCurrentTemp(base_TempControlAadl_Temperature *value) {
  *value = *currentTemp;
}

void putSetPoint(base_TempControlAadl_SetPoint *value) {
  *setPoint = *value;
}

void init(void) {
  oip_operatorInterface_initialize();
}

void notified(microkit_channel channel) {
  switch (channel) {
    case PORT_FROM_PACER:
      oip_operatorInterface_timeTriggered();
      break;
  }
}
