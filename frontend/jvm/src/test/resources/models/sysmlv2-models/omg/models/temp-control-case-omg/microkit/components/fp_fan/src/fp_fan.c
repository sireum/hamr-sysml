#include "fp_fan.h"

void fp_fan_initialize(void);
void fp_fan_timeTriggered(void);

volatile int *fanCmd;
volatile int *fanAck;

#define PORT_FROM_PACER 59

void getFanCmd(int *value) {
  *value = *fanCmd;
}

void putFanAck(int *value) {
  *fanAck = *value;
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
