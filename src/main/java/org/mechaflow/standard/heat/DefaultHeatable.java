package org.mechaflow.standard.heat;

/**
 *
 */
// TODO: Thermal mass and heat conductivity and such properties
// TODO: Determine temperature change from added heat energy.
// TODO: Specify Initial ambient temperature
public class DefaultHeatable implements Heatable {

    private double heatEnergy;

    @Override public void addHeatEnergy(double heatEnergy) {
        this.heatEnergy += heatEnergy;
    }
}
