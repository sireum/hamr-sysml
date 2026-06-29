// Positive model: two compositions reuse the component alias 'oi' consistently
// (both bind it to p1.t). Composition B binds the *same* second component under a
// *different* name (A: other, B: otherx) -- which is allowed; it just requires
// renaming 'other' to 'otherx' in any property fragments copied from A into B.
// Expected: no alias-consistency error.
package Composition_Cross_Consistent {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Cross_Consistent::proc_impl;
        part p2 : Composition_Cross_Consistent::proc_impl;

        language "GUMBO" /*{
            composition A {
                components
                  oi    = p1.t;
                  other = p2.t;

                schema {
                  oi; other
                }
            }

            composition B {
                components
                  oi     = p1.t;
                  otherx = p2.t;

                schema {
                  oi; otherx
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Cross_Consistent::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
