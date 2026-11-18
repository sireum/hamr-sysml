// Negative model: a composition property binds one schedule place under two of its
// names: in schema { a; b }, `after a` and `before b` are the same place.
// Expected (ScheduleNextRel.decorate, run by GclResolver): "binds place ... more than once"
package Composition_Place_Bound_Twice {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Place_Bound_Twice::proc_impl;
        part p2 : Composition_Place_Bound_Twice::proc_impl;

        language "GUMBO" /*{
            composition twice {
                components
                  a = p1.t;
                  b = p2.t;

                schema {
                  a; b
                }

                property twice {
                  after a "after a":
                    true;

                  before b "the same place, as before b":
                    true;
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Place_Bound_Twice::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
