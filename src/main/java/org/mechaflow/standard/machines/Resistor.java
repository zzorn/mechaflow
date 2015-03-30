package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.heat.Heatable;

/**
 * A resistor element.
 */
public class Resistor extends StandardMachineBase {

    public final ElectricPort a = electricPort("A");
    public final ElectricPort b = electricPort("B");

    private double resistance_Ohm = 100;

    public Resistor() {
    }

    public Resistor(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }

    @Override public void update(Time time) {
        conductChargeThroughResistor(a, b, resistance_Ohm, time.getLastStepDurationSeconds(), this);
    }

    public double getResistance_Ohm() {
        return resistance_Ohm;
    }

    public void setResistance_Ohm(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }
}
