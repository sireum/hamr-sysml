#include "oip_operatorInterface.h"

void oip_operatorInterface_initialize(void);
void oip_operatorInterface_timeTriggered(void);

volatile int *currentTemp;
volatile int *setPoint;

#define PORT_FROM_PACER 58

void getCurrentTemp(int *value) {
  *value = *currentTemp;
}

void putSetPoint(int *value) {
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
