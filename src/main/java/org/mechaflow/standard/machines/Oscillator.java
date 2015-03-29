package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.SignalPort;

import static java.lang.Math.*;
import static org.flowutils.MathUtils.*;

/**
 * Generates a repeating signal with a tunable waveform.
 */
public final class Oscillator extends StandardMachineBase {

    private static final float INVERSE_QUARTER_TAU = 1f / (TauFloat * 0.25f);
    private static final float HALF_TAU = TauFloat * 0.5f;

    private float currentPhase = Float.NaN;

    public final SignalPort wavelength = inputSignal("Wavelength", 1, "Duration of each wave in seconds.");
    public final SignalPort amplitude = inputSignal("Amplitude", 1, "How high and low the signal goes from the median level (offset).  With an amplitude of 1 and offset of 0, the signal will range from -1 to 1");
    public final SignalPort offset = inputSignal("Offset", "Offset to add to the signal.  Controls the median level of the signal.");
    public final SignalPort riseShape = inputSignal("Rise shape", "Range -1..1.  At -1, the rise of the signal is sharp, at 0 it is a sine wave, at 1 it is a triangle wave, and at 2 it approaches a chainsaw wave and 3 is flat.");
    public final SignalPort fallShape = inputSignal("Fall shape", "Range -1..1.  At -1, the fall of the signal is sharp, at 0 it is a sine wave, at 1 it is a triangle wave, and at 2 it approaches a chainsaw wave and 3 is flat.");
    public final SignalPort dutyCycle = inputSignal("Duty cycle", "Range -1..1.  Controls how much of the time the signal will spend at the minimum and maximum levels. If > 0 it will spend some fraction of the wave at the maximum level, if < 0 it will spend some time at the minimum level.");
    public final SignalPort symmetry = inputSignal("Symmetry", "Range -1..1.  Controls the symmetry of the produced wave.  Below 0 more time will be spent on the rising part of the wave, and above 0 more time will be spent on the falling part of the wave.");
    public final SignalPort phase = inputSignal("Phase", "Range 0..1.  Tells what part of the wave the oscillator should start in after a reset.");
    public final SignalPort reset = inputSignal("Reset", "When 0.5 or above, the signal will be fixed at the position indicated by Phase.  When it goes below 0.5 again, the signal will resume from that position.");
    public final SignalPort signal = outputSignal("Signal", "The produced output signal.  It will vary between offset - amplitude and offset + amplitude every wavelength seconds, unless reset is 0.5 or above.");

    /*
     Waveform example, with approximate parameters riseShape 1, fallShape 0, dutyCycle 0.3, and symmetry -0.3

     rise | duty |fall
           ______
          /      -
         /        \
        /         |
       /          \
      /            -_

     */

    @Override public void update(Time time) {
        // Determine current wave phase
        if (reset.isHigh() || Float.isNaN(currentPhase)) {
            // Reset wave phase if reset is high, or if this is the first call to update.
            currentPhase = phase.get();
        }
        else {
            // Advance the wave phase
            final float wavelength = this.wavelength.get();
            if (wavelength != 0) {
                // Allow negative wavelengths as well, it will simply advance the phase backward.
                currentPhase += time.getSecondsSinceLastStepAsFloat() / wavelength;
            }
        }

        // Make sure the phase is in the allowed range
        currentPhase = wrap0To1(currentPhase);

        // Determine relative length of the phases in the waveform (rising, high duty, falling, low duty)
        // NOTE: This only needs to be recalculated when the dutyCycle or symmetry is updated, although it probably isn't very heavy to calculate
        final float dutyCycle = clampMinus1To1(this.dutyCycle.get());
        final float highDutyFraction = max(dutyCycle, 0);
        final float lowDutyFraction = abs(min(dutyCycle, 0));
        final float edgeFraction = 1f - highDutyFraction - lowDutyFraction;
        final float symmetry = clampMinus1To1(this.symmetry.get());
        final float risingFraction = map(symmetry, -1, 1, edgeFraction, 0);
        final float fallingFraction = edgeFraction - risingFraction;

        final float highDutyFractionEnd = risingFraction + highDutyFraction;
        final float fallingFractionEnd = highDutyFractionEnd + fallingFraction;

        // Determine in which part of the waveform we are, and calculate the waveform value at the current position
        float value;
        if (currentPhase < risingFraction) {
            // Rising edge
            float pos = currentPhase / risingFraction;
            value = calculateWaveform(pos, riseShape.get());
        }
        else if (currentPhase < highDutyFractionEnd) {
            // High duty phase
            value = 1;
        }
        else if (currentPhase < fallingFractionEnd) {
            // Falling edge
            float pos = map(currentPhase, highDutyFractionEnd, fallingFractionEnd, 0, 1);
            value = calculateWaveform(1f - pos, fallShape.get());
        }
        else {
            // Low duty phase
            value = -1;
        }

        // Add amplitude and offset
        value *= amplitude.get();
        value += offset.get();

        // Store calculated waveform value in the output signal
        signal.set(value);
    }

    /**
     * @param pos position in the waveform, in the 0 to 1 range
     * @param shape shape of the waveform, -1 square wave, 0 sine wave, 1 triangle wave, 2 arcsin wave, 3 flat
     * @return value of the waveform, in the -1 to 1 range.
     */
    private float calculateWaveform(float pos, float shape) {
        if (shape <= -1) {
            // Pure square wave
            return pos < 0.5f ? -1 : 1;
        }
        else if (shape < 0) {
            // Mix square wave and sine
            // Project position to a section in the middle of the range, sine wave is generated there.
            // Expand the position as we approach sine wave from square wave
            final float start = mix(shape+1f, 0.5f, 0f);
            final float end = mix(shape+1f, 0.5f, 1f);
            pos = mapAndClamp(pos, start, end, 0f, 1f);
            return (float) sin((pos - 0.5f) * HALF_TAU);
        }
        else if (shape == 0) {
            // Pure sine
            return (float) sin((pos - 0.5f) * HALF_TAU);
        }
        else if (shape < 1) {
            // Mix sine and linear
            final float sine = (float) sin((pos - 0.5f) * HALF_TAU);
            final float linear = pos * 2f - 1f;
            return mix(shape, sine, linear);
        }
        else if (shape == 1) {
            // Pure linear
            return pos * 2f - 1f;
        }
        else if (shape < 2) {
            // Mix linear and chainsaw
            final float linear = pos * 2f - 1f;
            final float chainsaw = ((float) asin(pos * 2f - 1f)) * INVERSE_QUARTER_TAU;
            return mix(shape - 1f, linear, chainsaw);
        }
        else if (shape == 2) {
            // Pure chainsaw
            return ((float) asin(pos * 2f - 1f)) * INVERSE_QUARTER_TAU;
        }
        else if (shape < 3) {
            // Mix chainsaw and flat with peaks
            final float chainsaw = ((float) asin(pos * 2f - 1f)) * INVERSE_QUARTER_TAU;
            final float flat = pos <= 0f ? -1f : (pos >= 1f ? 1f : 0f);
            return mix(shape - 2f, chainsaw, flat);
        }
        else {
            // Pure flat
            return pos <= 0f ? -1f : (pos >= 1f ? 1f : 0f);
        }
    }

}
