#include "tcp_tempControl.h"

void tcp_tempControl_initialize(void);
void tcp_tempControl_timeTriggered(void);

volatile base_TempControlAadl_Temperature *currentTemp;
volatile base_TempControlAadl_FanAck_Type *fanAck;
volatile base_TempControlAadl_SetPoint *setPoint;
volatile base_TempControlAadl_FanCmd_Type *fanCmd;

#define PORT_FROM_PACER 60

void getCurrentTemp(base_TempControlAadl_Temperature *value) {
  *value = *currentTemp;
}

base_TempControlAadl_FanAck_Type getFanAck() {
  return *fanAck;
}

void getSetPoint(base_TempControlAadl_SetPoint *value) {
  *value = *setPoint;
}

void putFanCmd(base_TempControlAadl_FanCmd_Type value) {
  *fanCmd = value;
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
