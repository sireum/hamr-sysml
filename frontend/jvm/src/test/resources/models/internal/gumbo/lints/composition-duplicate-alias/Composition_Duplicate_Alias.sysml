// Negative model: two port aliases share the name 'dup' within a composition
// (component, port, and state-variable aliases share one namespace). The
// pre-existing component-alias resolution check does not cover port aliases, so
// this exercises the GclResolver.validateSchemaLabels alias-uniqueness lint.
// Expected lint: "Duplicate alias name 'dup'"
// (parity with the OSATE-side GumboValidator.checkCompositionAliasUniqueness).
package Composition_Duplicate_Alias {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Duplicate_Alias::proc_impl;

        language "GUMBO" /*{
            composition badAliases {
                components
                  c = p1.t;

                ports
                  dup = c.o;
                  dup = c.o;

                schema {
                  c
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Duplicate_Alias::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
