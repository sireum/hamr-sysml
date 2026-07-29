// Negative model: a single composition declares the component alias 'a' twice.
// Expected lint (per-composition uniqueness): "Duplicate alias name 'a'"
// (parity with the OSATE-side GumboValidator.checkCompositionAliasUniqueness).
package Composition_Duplicate_Component_Alias {
    private import AADL::*;

    part def sys :> AADL::System;

    part def sys_impl :> sys {
        part p1 : Composition_Duplicate_Component_Alias::proc_impl;
        part p2 : Composition_Duplicate_Component_Alias::proc_impl;

        language "GUMBO" /*{
            composition badAliases {
                components
                  a = p1.t;
                  a = p2.t;

                schema {
                  a
                }
            }
        }*/
    }

    part def proc :> AADL::Process;

    part def proc_impl :> proc {
        part t : Composition_Duplicate_Component_Alias::t;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }
}
