standard library package SI {

    public import MeasurementReferences::*;
    public import ISQ::*;
    public import SIPrefixes::*;

    attribute <s> second : DurationUnit;
    attribute <s> s : DurationUnit;

    attribute <B> byte : StorageCapacityUnit;
    attribute <bit> bit : StorageCapacityUnit;
    attribute <Hz> hertz : FrequencyUnit;
    attribute <o> octet : StorageCapacityUnit;
}
