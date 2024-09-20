#include "../../../include/printf.h"
#include "oip_operatorInterface.h"

void oip_operatorInterface_initialize(void) {
  // add initialization code here
  printf("%s: Init\n", microkit_name);
}

void oip_operatorInterface_timeTriggered() {
  // add compute phase code here
  //printf("%s: timeTriggered\n", microkit_name);

  base_TempControlAadl_Temperature value;
  getCurrentTemp(&value);
  printf("%s: received %.2lf\n", microkit_name, value.degrees);
}
