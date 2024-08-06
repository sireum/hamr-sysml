standard library package MeasurementReferences {
	doc
	/*
	 * This package defines the representations for measurement references.
	 */

    //*
	private import Collections::Array;
	private import Collections::List;
	private import ScalarValues::*;
	private import VectorValues::ThreeVectorValue;

	private import SequenceFunctions::size;
	private import SequenceFunctions::equals;
	private import ControlFunctions::forAll;
	private import Quantities::QuantityDimension;
	private import Quantities::VectorQuantityValue;
	private import Quantities::scalarQuantities;
	private import Quantities::ScalarQuantityValue;
	private import Quantities::SystemOfQuantities;
	private import ISQSpaceTime::angularMeasure;
	*/

    attribute def UnitPrefix :> SI::UnitPrefix;

	attribute def ConversionByPrefix {
		attribute prefix: UnitPrefix;
		attribute referenceUnit: UnitPrefix;
	}
}