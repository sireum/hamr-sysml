// Negative model: NoSend on a port alias backed by an INCOMING event port.
// Expected GclResolver error: "is backed by an incoming port".
package Composition_Send_Incoming_Alias {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Send_Incoming_Alias::proc_impl;
        part p2 : Composition_Send_Incoming_Alias::proc_impl;

        language "GUMBO" /*{
            composition sends {
                components
                  a = p1.t;
                  b = p2.t;

                ports
                  a_val = a.val;
                  a_in = a.inTick;

                schema {
                  a; b
                }

                property sent {
                  after a "a received nothing":
                    NoSend(a_in);
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Send_Incoming_Alias::t;
    }

    part def t :> AADL::Thread {
        out port val : AADL::EventDataPort {
            out :>> type : Base_Types::Integer_32;
        }
        out port tick : AADL::EventPort;
        in port inTick : AADL::EventPort;
    }
}
