package org.mechaflow;

import org.entityflow.world.ConcurrentWorld;
import org.entityflow.world.World;
import org.flowutils.service.ServiceProviderBase;
import org.flowutils.time.RealTime;
import org.flowutils.time.Time;
import org.mechaflow.entity.MachineComponent;
import org.mechaflow.entity.MachineProcessor;
import org.mechaflow.standard.machines.*;

/**
 *
 */
public class MechaflowExample extends ServiceProviderBase {

    private World world;
    private Time time;

    public static void main(String[] args) {
        new MechaflowExample().run();
    }

    @Override protected void registerServices() {
        time = new RealTime();
        world = addService(new ConcurrentWorld(time));

        world.addProcessor(new MachineProcessor(0.1));
    }

    public void run() {
        init();

        //setupOscillatorTestWorld();
        setupElectricityTestWorld();

        world.start();

        shutdown();
    }


    private void setupOscillatorTestWorld() {
        final Oscillator slowOsc = addMachine(new Oscillator());
        final Oscillator fastOsc = addMachine(new Oscillator());
        final PrintMachine view = addMachine(new PrintMachine());
        final SignalJunction junction = addMachine(new SignalJunction());

        slowOsc.wavelength.set(6);
        slowOsc.offset.set(6);
        slowOsc.amplitude.set(5);
        slowOsc.riseShape.set(0);
        slowOsc.fallShape.set(0);
        slowOsc.dutyCycle.set(0);
        slowOsc.symmetry.set(0.5f);

        fastOsc.wavelength.set(1.3f);
        fastOsc.amplitude.set(1);
        fastOsc.offset.set(0);

        fastOsc.signal.connect(junction.input);
        //junction.output1.connect(slowOsc.symmetry);
        junction.output2.connect(view.inputB);


        //fastOsc.wavelength.connect(slowOsc.signal);

        view.inputA.connect(slowOsc.signal);
        //view.inputB.connect(fastOsc.signal);
    }

    private void setupElectricityTestWorld() {
        final Generator generator = addMachine(new Generator());
        final Resistor resistor = addMachine(new Resistor(50));
        final Resistor resistor2 = addMachine(new Resistor(100));
        final Resistor resistor3 = addMachine(new Resistor(9850));
        final ElectricMeter electricMeter = addMachine(new ElectricMeter());

        final PrintMachine view = addMachine(new PrintMachine());

        generator.plusPole.connect(electricMeter.a);
        electricMeter.b.connect(resistor.a);
        resistor.b.connect(resistor2.a);
        resistor2.b.connect(resistor3.a);
        resistor3.b.connect(generator.minusPole);

        view.inputA.connect(electricMeter.measuredVoltage);
        view.inputB.connect(electricMeter.measuredCurrent);
    }

    private <T extends Machine> T addMachine(final T machine) {
        world.createEntity(new MachineComponent(machine));
        return machine;
    }

}
