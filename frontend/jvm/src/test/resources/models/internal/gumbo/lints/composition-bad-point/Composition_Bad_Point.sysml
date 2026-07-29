// Negative model: a composition property binds 'at nonexistent_label', which is
// not START/END nor a declared schema label.
// Expected GclResolver lint: "'nonexistent_label' does not name a schema label"
// (parity with the OSATE-side GumboValidator.checkSchemaPointResolution).
package Composition_Bad_Point {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Bad_Point::proc_impl;
        part p2 : Composition_Bad_Point::proc_impl;

        language "GUMBO" /*{
            composition badPoint {
                components
                  a = p1.t;
                  b = p2.t;

                schema {
                  a; b
                }

                property bad {
                  at nonexistent_label "references a label that does not exist":
                    true;
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Bad_Point::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
