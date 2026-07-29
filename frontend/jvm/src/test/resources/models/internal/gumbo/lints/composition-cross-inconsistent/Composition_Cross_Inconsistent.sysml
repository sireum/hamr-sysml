// Negative model: two compositions reuse the component alias 'oi' but bind it to
// different components (A: p1.t, B: p2.t). A reused alias name must resolve to the
// same element across compositions.
// Expected lint: "an alias reused across compositions must refer to the same element"
package Composition_Cross_Inconsistent {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Cross_Inconsistent::proc_impl;
        part p2 : Composition_Cross_Inconsistent::proc_impl;

        language "GUMBO" /*{
            composition A {
                components
                  oi = p1.t;

                schema {
                  oi
                }
            }

            composition B {
                components
                  oi = p2.t;

                schema {
                  oi
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Cross_Inconsistent::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
