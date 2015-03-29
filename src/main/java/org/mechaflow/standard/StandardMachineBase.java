package org.mechaflow.standard;

import org.mechaflow.MachineBase;
import org.mechaflow.PortDirection;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Provides convenience methods for adding standard ports.
 */
public abstract class StandardMachineBase extends MachineBase {

    // Signals

    protected final SignalPort inputSignal(String name) {
        return inputSignal(name, null);
    }

    protected final SignalPort inputSignal(String name, String description) {
        return inputSignal(name, 0f, description);
    }

    protected final SignalPort inputSignal(String name, float initialValue) {
        return inputSignal(name, initialValue, null);
    }

    protected final SignalPort inputSignal(String name, float initialValue, String description) {
        return addPort(new SignalPort(name, PortDirection.IN, initialValue, description));
    }

    protected final SignalPort outputSignal(String name) {
        return outputSignal(name, null);
    }

    protected final SignalPort outputSignal(String name, String description) {
        return addPort(new SignalPort(name, PortDirection.OUT, description));
    }


    // Electricity
    protected final ElectricPort electricPort(String name) {
        return electricPort(name, null);
    }

    protected final ElectricPort electricPort(String name, String description) {
        return addPort(new ElectricPort(name, description));
    }

}
