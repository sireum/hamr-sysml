standard library package Quantities {

	abstract attribute def ScalarQuantityValue { // :> VectorQuantityValue, NumericalValue {
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

    		//attribute quantity: ScalarQuantityValue[1];
    		//attribute exponent: Real[1];
    	}
}