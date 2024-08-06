standard library package SI {

//*
    private import MeasurementReferences::*;
    import ISQ::*;
    import SIPrefixes::*;
*/

    // FIXME: DurationValue is actually defined in ISQBase. ISQ imports that namespace publicly (default)
    //        and SI imports ISQ namespace publicly
    //        and therefore AADL_Project can refer to it
    //        via 'SI::DurationValue' rather than (or in addition) to ISQBase::DurationValue
    attribute def DurationUnit :> ISQ::DurationValue {
      attribute unitConversion : MeasurementReferences::ConversionByPrefix;
    }

    attribute def UnitPrefix;

    attribute pico : UnitPrefix;
    attribute nano : UnitPrefix;
    attribute micro : UnitPrefix;
    attribute milli : UnitPrefix;
    attribute s : UnitPrefix;
}
