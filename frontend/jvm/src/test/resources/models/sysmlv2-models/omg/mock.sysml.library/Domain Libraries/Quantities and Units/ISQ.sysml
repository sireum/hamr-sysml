standard library package ISQ {

//*
	private import ScalarValues::Real;
	private import Quantities::*;
	private import MeasurementReferences::*;

	import ISQBase::*;                  // ISO/IEC 80000 base quantities and general concepts
    import ISQSpaceTime::*;             // ISO 80000-3 "Space and Time"
    import ISQMechanics::*;             // ISO 80000-4 "Mechanics"
    import ISQThermodynamics::*;        // ISO 80000-5 "Thermodynamics"
    import ISQElectromagnetism::*;      // IEC 80000-6 "Electromagnetism"
    import ISQLight::*;                 // ISO 80000-7 "Light"
    import ISQAcoustics::*;             // ISO 80000-8 "Acoustics"
    import ISQChemistryMolecular::*;    // ISO 80000-9 "Physical chemistry and molecular physics"
    import ISQAtomicNuclear::*;         // ISO 80000-10 "Atomic and nuclear physics"
    import ISQCharacteristicNumbers::*; // ISO 80000-11 "Characteristic numbers"
    import ISQCondensedMatter::*;       // ISO 80000-12 "Condensed matter physics"
    import ISQInformation::*;           // IEC 80000-13 "Information science and technology"
*/

    // FIXME: DurationValue is actually defined in ISQBase. ISQ imports that
    //        namespace publicly (default) and therefore AADL_Project can refer to it
    //        via 'ISQ::DurationValue' rather than (or in addition) to ISQBase::DurationValue
    attribute def DurationValue { //:> ScalarQuantityValue {
        //*doc
        /*
         * source: item 3-9 duration, time
         * symbol(s): `t`
         * application domain: generic
         * name: Duration
         * quantity dimension: T^1
         * measurement unit(s): s
         * tensor order: 0
         * definition: measure of the time difference between two events
         * remarks: Duration is often just called time. Time is one of the seven base quantities in the International System of Quantities, ISQ (see ISO 80000-1). Duration is a measure of a time interval.
         */
        //attribute :>> num: Real;
        attribute num: ScalarValues::Number;
        //attribute :>> mRef: DurationUnit[1];
    }
}
