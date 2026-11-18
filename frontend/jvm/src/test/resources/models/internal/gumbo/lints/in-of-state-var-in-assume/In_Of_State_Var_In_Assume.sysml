// Negative model: In(count) in a compute assume, where the bare state variable already
// denotes its pre-state value.
// Expected GclResolver lint: "In(count) is not needed here" -- not the rewritten
// "In(In(count))" it used to print.
package In_Of_State_Var_In_Assume {
    private import AADL::*;

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : In_Of_State_Var_In_Assume::t_impl;
    }

    part def t :> AADL::Thread {
        out port o : AADL::DataPort {
            out :>> type : Base_Types::Boolean;
        }
    }

    part def t_impl :> t {
        language "GUMBO" /*{
            state
              count: Base_Types::Integer_32;

            initialize
              modifies count;

              guarantee initCount:
                count == 0 [i32];

            compute
              modifies count;

              assume nonNegative "the previous count is not negative":
                In(count) >= 0 [i32];

              guarantee keeps:
                count == In(count);
        }*/
    }
}
