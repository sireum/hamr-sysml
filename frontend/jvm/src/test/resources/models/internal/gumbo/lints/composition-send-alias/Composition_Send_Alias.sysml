// Positive model: NoSend and MustSend (with and without an expected value) in a
// composition property, on port aliases backed by OUTGOING event and event-data
// ports. GclResolver rewrites them over the channel, as it does HasEvent(alias):
// NoSend(a) -> a.isEmpty, MustSend(a) -> a.nonEmpty, MustSend(a, v) -> a.nonEmpty
// && a.get == v.
// Expected: no resolution error.
package Composition_Send_Alias {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Send_Alias::proc_impl;
        part p2 : Composition_Send_Alias::proc_impl;

        language "GUMBO" /*{
            composition sends {
                components
                  a = p1.t;
                  b = p2.t;

                ports
                  a_val = a.val;
                  a_tick = a.tick;

                schema {
                  a; b
                }

                property sent {
                  after a "a sent 4 and did not tick":
                    MustSend(a_val) & MustSend(a_val, 4[i32]) & NoSend(a_tick);
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Send_Alias::t;
    }

    part def t :> AADL::Thread {
        out port val : AADL::EventDataPort {
            out :>> type : Base_Types::Integer_32;
        }
        out port tick : AADL::EventPort;
    }
}
