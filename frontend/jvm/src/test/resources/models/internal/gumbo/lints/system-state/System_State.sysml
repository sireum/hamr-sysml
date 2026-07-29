// Negative model: GUMBO state variables attached to a system implementation.
// Expected GclResolver lint: "State variables cannot be attached to system implementations"
// (parity with the OSATE-side GumboValidator.checkSpecSectionForSystemImplementation).
package System_State {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;

        language "GUMBO" /*{
            state
              counter : Base_Types::Integer_32;
        }*/
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : System_State::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
