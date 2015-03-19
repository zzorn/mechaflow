package org.mechaflow.entity;

import org.entityflow.component.ComponentBase;
import org.entityflow.entity.Entity;
import org.mechaflow.Machine;

import static org.flowutils.Check.notNull;

/**
 * A machine component for an entity.
 */
public final class MachineComponent extends ComponentBase {

    private Machine machine;

    public MachineComponent(Machine machine) {
        notNull(machine, "machine");

        this.machine = machine;
    }

    public Machine getMachine() {
        return machine;
    }

    @Override protected void handleRemoved(Entity entity) {
        machine.disconnectAll();
    }
}
