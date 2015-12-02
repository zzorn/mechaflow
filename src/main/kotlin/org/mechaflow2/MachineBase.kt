package org.mechaflow2

import org.flowutils.Check
import org.mechaflow.standard.heat.DefaultHeatable
import java.util.*

/**
 * Common functionality for machines.
 */
abstract class MachineBase : DefaultHeatable(), Machine {

    override val ports: MutableList<Port> by lazy() { ArrayList<Port>(5) }

    /**
     * @param port port to add to the machine.
     * *
     * @return the added port, for assigning to public fields or similar.
     */
    protected fun <P : Port> addPort(port: P): P {
        Check.notContained(port, ports, "ports")

        port.init(this)

        ports.add(port)

        return port
    }
}