#include <stdint.h>
#include <microkit.h>

#define PORT_TO_TSP_TEMPSENSOR 61
#define PORT_TO_TCP_TEMPCONTROL 60
#define PORT_TO_FP_FAN 59
#define PORT_TO_OIP_OPERATORINTERFACE 58
#define PORT_TO_END_OF_FRAME_COMPONENT 57
#define PORT_FROM_END_OF_FRAME_COMPONENT 56

void paceComponents(){
  microkit_notify(PORT_TO_TSP_TEMPSENSOR);
  microkit_notify(PORT_TO_TCP_TEMPCONTROL);
  microkit_notify(PORT_TO_FP_FAN);
  microkit_notify(PORT_TO_OIP_OPERATORINTERFACE);
  microkit_notify(PORT_TO_END_OF_FRAME_COMPONENT);
}

void init(void) {}

void notified(microkit_channel channel) {
  switch(channel) {
    case PORT_FROM_END_OF_FRAME_COMPONENT:
      paceComponents();
      break;
  }
}
