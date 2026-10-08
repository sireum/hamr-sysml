package Time_Units {
    private import HAMR::*;

    // One periodic thread per HAMR_Time_Units literal, each with a period of 2 of that unit, so the
    // front end's conversion of every unit to picoseconds can be checked (hamr-codegen#12)

    part def ps_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 2[ps];
    }

    part def ns_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 2[ns];
    }

    part def us_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 2[us];
    }

    part def ms_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 2[ms];
    }

    part def s_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 2[s];
    }

    // a decimal value, which the front end used to fail on
    part def dec_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 1.5[ms];
    }

    part def p :> Process {
        part t_ps: ps_t;
        part t_ns: ns_t;
        part t_us: us_t;
        part t_ms: ms_t;
        part t_s: s_t;
        part t_dec: dec_t;
    }

    part def proc_impl :> Processor {
        attribute :>> Frame_Period = 1000[ms];
        attribute :>> Clock_Period = 2[ms];
    }

    part def top_impl :> System {
        part proc: proc_impl;
        part pp: p;
        allocation b0: Deployment_Properties::Actual_Processor_Binding
            allocate pp to proc;
    }
}
