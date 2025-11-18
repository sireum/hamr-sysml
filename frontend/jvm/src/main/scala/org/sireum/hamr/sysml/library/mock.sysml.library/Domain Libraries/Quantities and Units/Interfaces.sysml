standard library package Interfaces {
    abstract interface def BinaryInterface {// :> Interface, BinaryConnection {
        //doc
        /*
         * BinaryInterface is the most general class of links between two Ports
         * on Parts within some containing structure. BinaryInterface is the base
         * type of all binary InterfaceDefinitions which have exactly two ends.
         *
         * Transfers outgoing from each participant Port of a BinaryInterface may be
         * targeted to the other participant Port (depending on any other Interfaces
         * in which the Port is also participating).
         */

        //ref port :>> Interface::participant, BinaryConnection::participant[2] //* nonunique */ ordered;

        //end port source: Port :>> BinaryConnection::source;
        //end port target: Port :>> BinaryConnection::target;
    }
}