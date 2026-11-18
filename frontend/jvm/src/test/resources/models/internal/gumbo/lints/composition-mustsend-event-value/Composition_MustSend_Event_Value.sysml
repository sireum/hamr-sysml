// Negative model: MustSend with an expected value on a port alias backed by an
// (outgoing) EVENT port, which carries no value.
// Expected: rejected. The type checker already fails to unify the event port's empty
// payload (art.Empty) with the S32 value, so GclResolver reports the call's type
// as unresolvable before its own "carries no value" check is reached.
package Composition_MustSend_Event_Value {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_MustSend_Event_Value::proc_impl;
        part p2 : Composition_MustSend_Event_Value::proc_impl;

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
                  after a "a ticked 4":
                    MustSend(a_tick, 4[i32]);
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_MustSend_Event_Value::t;
    }

    part def t :> AADL::Thread {
        out port val : AADL::EventDataPort {
            out :>> type : Base_Types::Integer_32;
        }
        out port tick : AADL::EventPort;
    }
}
