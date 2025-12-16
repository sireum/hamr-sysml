standard library package ISQBase {

    abstract attribute def UnitConversion;

    abstract attribute def MeasurementUnit {
     attribute unitConversion : UnitConversion;
    }

    abstract attribute def SimpleUnit :> MeasurementUnit;

    attribute def DurationValue {
        attribute num: ScalarValues::Number;
    }

    attribute def DurationUnit :> SimpleUnit;
}