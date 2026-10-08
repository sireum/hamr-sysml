package Two_Systems {
    private import HAMR::*;

    // Two systems, of which only the second has a time that must be rounded: a sub-picosecond
    // fraction that still rounds to a usable time (1000000.4 ps rounds to 1000000 ps = 1 us).
    // The front end resolves both systems and codegen resolves the second again; its
    // time-rounding warning must be reported once (hamr-codegen#12, doc/ExactTime-design.md D3)

    part def exact_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 1[ms];
    }

    part def inexact_t :> Thread {
        attribute :>> Dispatch_Protocol = Periodic;
        attribute :>> Period = 1000000.4[ps];
    }

    part def exact_p :> Process {
        part t: exact_t;
    }

    part def inexact_p :> Process {
        part t: inexact_t;
    }

    part def proc_impl :> Processor {
        attribute :>> Frame_Period = 1000[ms];
        attribute :>> Clock_Period = 2[ms];
    }

    part def first_impl :> System {
        part proc: proc_impl;
        part pp: exact_p;
        allocation b0: Deployment_Properties::Actual_Processor_Binding
            allocate pp to proc;
    }

    part def second_impl :> System {
        part proc: proc_impl;
        part pp: inexact_p;
        allocation b0: Deployment_Properties::Actual_Processor_Binding
            allocate pp to proc;
    }
}
