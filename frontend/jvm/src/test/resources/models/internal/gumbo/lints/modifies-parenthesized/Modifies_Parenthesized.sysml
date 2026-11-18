// Negative model: `modifies (count, total);` -- GUMBO's modifies list has no parentheses.
// Expected parser error: "modifies takes its state variables without parentheses: write
// 'modifies count, total;'" -- not the generic "comma separated sequencing expressions are
// not currently supported", which did not say what to write.
package Modifies_Parenthesized {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : Modifies_Parenthesized::t_impl;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }

    part def t_impl :> t {
        language "GUMBO" /*{
            state
              count: Base_Types::Integer_32;
              total: Base_Types::Integer_32;

            compute
              modifies (count, total);

              guarantee keeps:
                count == In(count) & total == In(total);
        }*/
    }
}
