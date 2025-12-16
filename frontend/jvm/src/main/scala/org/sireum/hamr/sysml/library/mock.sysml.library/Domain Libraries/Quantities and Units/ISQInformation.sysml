standard library package ISQInformation {

	abstract attribute def DerivedUnit { // :> MeasurementUnit {
		doc
		/*
		 * Representation of a derived measurement unit that depends on one or more powers of other measurement units.
		 *
		 * VIM defines "derived unit" as "measurement unit for a derived quantity", see https://jcgm.bipm.org/vim/en/1.11.html .
		 */
	}

	attribute def DimensionOneUnit :> DerivedUnit {
		doc
		/*
		 * Explicit definition of "unit of dimension one", also known as "dimensionless unit".
		 */

		//attribute :>> unitPowerFactors = ();
	}

    attribute def StorageCapacityValue { }

    attribute def StorageCapacityUnit :> DimensionOneUnit { }

    attribute def BinaryDigitRateValue { }

    attribute def FrequencyUnit { }
}