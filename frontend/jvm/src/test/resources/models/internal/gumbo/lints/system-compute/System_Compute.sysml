// Negative model: GUMBO compute clause attached to a system implementation.
// Expected GclResolver lint: "Compute clauses cannot be attached to system implementations"
// (parity with the OSATE-side GumboValidator.checkSpecSectionForSystemImplementation).
package System_Compute {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;

        language "GUMBO" /*{
            compute
              guarantee badComputeOnSystem "compute is not allowed on system implementations":
                true;
        }*/
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : System_Compute::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
