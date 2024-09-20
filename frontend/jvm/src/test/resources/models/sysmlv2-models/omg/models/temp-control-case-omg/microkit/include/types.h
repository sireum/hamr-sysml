#ifndef TYPES_H
#define TYPES_H

#include <stdint.h>

typedef struct base_TempControlAadl_Temperature base_TempControlAadl_Temperature;

typedef struct base_TempControlAadl_SetPoint base_TempControlAadl_SetPoint;

typedef
  enum {Ok, Error} base_TempControlAadl_FanAck_Type;

typedef
  enum {On, Off} base_TempControlAadl_FanCmd_Type;

typedef
  enum {Fahrenheit, Celsius, Kelvin} base_TempControlAadl_TempUnit_Type;

struct base_TempControlAadl_Temperature {
  double degrees;
  base_TempControlAadl_TempUnit_Type unit;
};

struct base_TempControlAadl_SetPoint {
  base_TempControlAadl_Temperature low;
  base_TempControlAadl_Temperature high;
};

#endif
