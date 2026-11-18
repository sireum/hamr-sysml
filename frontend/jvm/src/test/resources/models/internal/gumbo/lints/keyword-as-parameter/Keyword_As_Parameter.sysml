// Negative model: a GUMBO function parameter named `out`, a reserved word.
// Expected parser error at the parameter: "... ('out' is a reserved word, so it cannot be
// used as a name here; rename it)". ANTLR's message alone ("mismatched input 'out'
// expecting {')', RULE_ID}") did not say the word is reserved.
package Keyword_As_Parameter {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : Keyword_As_Parameter::t_impl;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }

    part def t_impl :> t {
        language "GUMBO" /*{
            functions
              def positive(out: Base_Types::Integer_32): Base_Types::Boolean :=
                out > 0 [i32];
        }*/
    }
}
