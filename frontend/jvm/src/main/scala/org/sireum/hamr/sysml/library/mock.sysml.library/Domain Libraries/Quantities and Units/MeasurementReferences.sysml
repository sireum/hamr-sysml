standard library package MeasurementReferences {
    attribute def UnitPrefix :> SI::UnitPrefix;

	attribute def ConversionByPrefix {
		attribute prefix: UnitPrefix;
		attribute referenceUnit: UnitPrefix;
	}

	attribute def DerivedUnit { }

	attribute def TensorMeasurementReference {}
 }