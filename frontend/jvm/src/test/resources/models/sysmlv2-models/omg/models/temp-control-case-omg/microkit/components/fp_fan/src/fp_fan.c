#include "fp_fan.h"

void fp_fan_initialize(void);
void fp_fan_timeTriggered(void);

volatile base_TempControlAadl_FanCmd_Type *fanCmd;
volatile base_TempControlAadl_FanAck_Type *fanAck;

#define PORT_FROM_PACER 59

base_TempControlAadl_FanCmd_Type getFanCmd() {
  return *fanCmd;
}

void putFanAck(base_TempControlAadl_FanAck_Type value) {
  *fanAck = value;
}

void init(void) {
  fp_fan_initialize();
}

void notified(microkit_channel channel) {
  switch (channel) {
    case PORT_FROM_PACER:
      fp_fan_timeTriggered();
      break;
  }
}
