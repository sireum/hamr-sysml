standard library package Collections {

    private import ScalarValues::*;
	doc
	/*
	 * This package defines a standard set of Collection data types. Unlike sequences of values
	 * defined directly using multiplicity, these data types allow for the possibility of collections
	 * as elements of collections.
	 */

	 attribute def Array {
	   attribute dimensions: ScalarValues::Positive;//[0..*] ordered nonunique;
	   attribute elements: Base::Anything;
	 }
}