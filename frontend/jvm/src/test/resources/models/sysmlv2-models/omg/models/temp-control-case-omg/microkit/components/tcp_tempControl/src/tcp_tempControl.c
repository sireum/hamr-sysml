#include "tcp_tempControl.h"

void tcp_tempControl_initialize(void);
void tcp_tempControl_timeTriggered(void);

volatile int *currentTemp;
volatile int *fanAck;
volatile int *setPoint;
volatile int *fanCmd;

#define PORT_FROM_PACER 60

void getCurrentTemp(int *value) {
  *value = *currentTemp;
}

void getFanAck(int *value) {
  *value = *fanAck;
}

void getSetPoint(int *value) {
  *value = *setPoint;
}

void putFanCmd(int *value) {
  *fanCmd = *value;
}

void init(void) {
  tcp_tempControl_initialize();
}

void notified(microkit_channel channel) {
  switch (channel) {
    case PORT_FROM_PACER:
      tcp_tempControl_timeTriggered();
      break;
  }
}
