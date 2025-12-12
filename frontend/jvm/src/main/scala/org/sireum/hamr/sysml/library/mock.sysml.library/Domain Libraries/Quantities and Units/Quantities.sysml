standard library package Quantities {

	abstract attribute def ScalarQuantityValue { // :> VectorQuantityValue, NumericalValue {
	  attribute num: ScalarValues::Number;
	  attribute mRef: MeasurementReferences::TensorMeasurementReference;
	/*
		attribute :>> mRef: MeasurementReferences::ScalarMeasurementReference {
			:>> mRef::dimensions, MeasurementReferences::ScalarMeasurementReference::dimensions;
			:>> mRef::isBound, MeasurementReferences::ScalarMeasurementReference::isBound;
			:>> mRef::order, MeasurementReferences::ScalarMeasurementReference::order;
			:>> mRef::mRefs, MeasurementReferences::ScalarMeasurementReference::mRefs;
		}
		*/
	}

		attribute def QuantityPowerFactor {
    		//doc
    		/*
    		 * Representation of a quantity power factor, being the combination of a quantity and an exponent.
    		 *
    		 * A sequence of QuantityPowerFactors for the baseQuantities of a SystemOfQuantities define the QuantityDimension of a scalar quantity.
    		 */

    		attribute quantity: Quantities::ScalarQuantityValue[1];
    		attribute exponent: ScalarValues::Real[1];
    	}
}