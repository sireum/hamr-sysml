standard library package SI {

    public import ISQ::*;

    attribute def DurationUnit :> ISQ::DurationValue {
      attribute unitConversion : MeasurementReferences::ConversionByPrefix;
    }

    attribute def UnitPrefix;

    attribute <s> second : DurationUnit;
    attribute <s> s : DurationUnit;

    attribute def StorageCapacityValue { }


    attribute def BinaryDigitRateValue { }

    attribute def StorageCapacityUnit { }

    attribute <B> byte : StorageCapacityUnit;

	attribute yocto: UnitPrefix { :>> longName = "yocto"; :>> symbol = "y"; :>> conversionFactor = 1; }
	attribute zepto: UnitPrefix { :>> longName = "zepto"; :>> symbol = "z"; :>> conversionFactor = 1; }
	attribute atto: UnitPrefix { :>> longName = "atto"; :>> symbol = "a"; :>> conversionFactor = 1; }
	attribute femto: UnitPrefix { :>> longName = "femto"; :>> symbol = "f"; :>> conversionFactor = 1; }
	attribute pico: UnitPrefix { :>> longName = "pico"; :>> symbol = "p"; :>> conversionFactor = 1; }
	attribute nano: UnitPrefix { :>> longName = "nano"; :>> symbol = "n"; :>> conversionFactor = 1; }
	attribute micro: UnitPrefix { :>> longName = "micro"; :>> symbol = "μ"; :>> conversionFactor = 1; }
	attribute milli: UnitPrefix { :>> longName = "milli"; :>> symbol = "m"; :>> conversionFactor = 1; }
	attribute centi: UnitPrefix { :>> longName = "centi"; :>> symbol = "c"; :>> conversionFactor = 1; }
	attribute deci: UnitPrefix { :>> longName = "deci"; :>> symbol = "d"; :>> conversionFactor = 1; }
	attribute deca: UnitPrefix { :>> longName = "deca"; :>> symbol = "da"; :>> conversionFactor = 1; }
	attribute hecto: UnitPrefix { :>> longName = "hecto"; :>> symbol = "h"; :>> conversionFactor = 1; }
	attribute kilo: UnitPrefix { :>> longName = "kilo"; :>> symbol = "k"; :>> conversionFactor = 1; }
	attribute mega: UnitPrefix { :>> longName = "mega"; :>> symbol = "M"; :>> conversionFactor = 1; }
	attribute giga: UnitPrefix { :>> longName = "giga"; :>> symbol = "G"; :>> conversionFactor = 1; }
	attribute tera: UnitPrefix { :>> longName = "tera"; :>> symbol = "T"; :>> conversionFactor = 1; }
	attribute peta: UnitPrefix { :>> longName = "peta"; :>> symbol = "P"; :>> conversionFactor = 1; }
	attribute exa: UnitPrefix { :>> longName = "exa"; :>> symbol = "E"; :>> conversionFactor = 1; }
	attribute zetta: UnitPrefix { :>> longName = "zetta"; :>> symbol = "Z"; :>> conversionFactor = 1; }
	attribute yotta: UnitPrefix { :>> longName = "yotta"; :>> symbol = "Y"; :>> conversionFactor = 1; }
}
