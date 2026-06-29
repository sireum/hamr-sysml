// Negative model: GUMBO invariants attached to a thread component.
// Expected GclResolver lint: "Invariants cannot be attached to thread components"
// (parity with the OSATE-side GumboValidator.checkSpecSectionForThreadComponent).
package Thread_Invariants {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : Thread_Invariants::t_impl;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }

    part def t_impl :> t {
        language "GUMBO" /*{
            invariants
              inv badInvariantOnThread "invariants are not allowed on thread components":
                true;
        }*/
    }
}
