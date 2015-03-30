package org.mechaflow.standard.heat;

/**
 * A machine or internal part of a machine that can absorb and transfer heat.
 */
// TODO: ways to connect heatables together for heat transfer / flow
public interface Heatable {

    /**
     * Add some heat energy to this heatable object.
     * @param heatEnergy heat energy to add, measured as joules.
     */
    void addHeatEnergy(double heatEnergy);

}
