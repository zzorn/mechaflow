package org.mechaflow2.standard

import org.mechaflow2.MachineBase
import org.mechaflow2.PortDirection
import org.mechaflow2.PortSize
import org.mechaflow2.standard.ports.SignalPort
import org.mechaflow2.standard.ports.SignalRange

/**
 *
 */
abstract class StandardMachineBase : MachineBase() {

    protected fun inputSignal(name: String,
                              desc: String? = null,
                              range: SignalRange = SignalRange.UNLIMITED,
                              size: PortSize = PortSize.MEDIUM): SignalPort =
            inputSignal(name, 0.0, desc, range, size)

    protected fun inputSignal(name: String,
                              defaultValue: Double = 0.0,
                              desc: String? = null,
                              range: SignalRange = SignalRange.UNLIMITED,
                              size: PortSize = PortSize.MEDIUM): SignalPort =
            SignalPort(name, PortDirection.IN, defaultValue, range, desc, size)

    protected fun outputSignal(name: String,
                               desc: String? = null,
                               range: SignalRange = SignalRange.UNLIMITED,
                               size: PortSize = PortSize.MEDIUM): SignalPort =
            outputSignal(name, 0.0, desc, range, size)

    protected fun outputSignal(name: String,
                               defaultValue: Double = 0.0,
                               desc: String? = null,
                               range: SignalRange = SignalRange.UNLIMITED,
                               size: PortSize = PortSize.MEDIUM): SignalPort =
            SignalPort(name, PortDirection.OUT, defaultValue, range, desc, size)


}