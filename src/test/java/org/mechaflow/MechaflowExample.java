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

        world.addProcessor(new MachineProcessor());
    }

    public void run() {
        init();

        //setupOscillatorTestWorld();
        setupElectricityTestWorld();

        world.start();

        shutdown();
    }


    private void setupOscillatorTestWorld() {

        // TODO: Make timestep actually fixed, just run more if we are lagging behind in real time!
        // Variable timestep introduces a lot of noise into the algorithms!


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
        final Resistor resistor = addMachine(new Resistor(100));
        final Resistor resistor2 = addMachine(new Resistor(110));
        final Resistor resistor3 = addMachine(new Resistor(111));
        final CurrentMeter currentMeter1 = addMachine(new CurrentMeter());
        final CurrentMeter currentMeter2 = addMachine(new CurrentMeter());

        final PrintMachine view = addMachine(new PrintMachine());

        generator.plusPole.connect(currentMeter1.a);
        currentMeter1.b.connect(resistor2.a);
        resistor2.b.connect(currentMeter2.a);
        currentMeter2.b.connect(resistor.a);
        resistor.b.connect(resistor3.a);
        resistor3.b.connect(generator.minusPole);

        view.inputA.connect(currentMeter1.measuredCurrent);
        view.inputB.connect(currentMeter2.measuredCurrent);
    }

    private <T extends Machine> T addMachine(final T machine) {
        world.createEntity(new MachineComponent(machine));
        return machine;
    }

}
