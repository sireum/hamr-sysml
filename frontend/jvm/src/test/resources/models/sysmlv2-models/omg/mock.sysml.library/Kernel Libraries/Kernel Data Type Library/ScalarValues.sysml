standard library package ScalarValues {
	//*doc
	/*
	 * This package contains a basic set of primitive scalar (non-collection) data types. 
	 * These include Boolean and String types and a hierarchy of concrete Number types, from 
	 * the most general type of Complex numbers to the most specific type of Positive integers.</p>
	 */

    // FIXME: ScalarValues is actually an kerml file

	//*
	abstract datatype ScalarValue specializes DataValue;
	datatype Boolean specializes ScalarValue;
	datatype String specializes ScalarValue;
	abstract datatype NumericalValue specializes ScalarValue;


    abstract datatype Number specializes NumericalValue;
	datatype Complex specializes Number;
	datatype Real specializes Complex;
	datatype Rational specializes Real;
	datatype Integer specializes Rational;
	datatype Natural specializes Integer;
    datatype Positive specializes Natural;
    */

    abstract attribute def ScalarValue :> Base::DataValue;
    attribute def Boolean :> ScalarValue;
    attribute def String :> ScalarValue;
    abstract attribute def NumericalValue :> ScalarValue;

    abstract attribute def Number :> NumericalValue;
    attribute def Complex :> Number;
	attribute def Real :> Complex;
	attribute def Rational :> Real;
	attribute def Integer :> Rational;
	attribute def Natural :> Integer;
	attribute def Positive :> Natural;
}
