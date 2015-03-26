package org.mechaflow;

import org.flowutils.collections.ringbuffer.RingBufferFloat;
import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.SignalPort;
import org.uiflow.desktop.ui.SimpleFrame;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.flowutils.MathUtils.map;

/**
 * Simple debug view that can show two inputs over time
 */
public final class PrintMachine extends StandardMachineBase {
    private static final int BUFFER_WIDTH = 1000;

    public final SignalPort inputA = inputSignal("Inout A");
    public final SignalPort inputB = inputSignal("Inout B");
    public final SignalPort updateInterval = inputSignal("Update Interval", 0.001f, "Seconds between updates");

    private final RingBufferFloat aBuffer = new RingBufferFloat(BUFFER_WIDTH);
    private final RingBufferFloat bBuffer = new RingBufferFloat(BUFFER_WIDTH);

    private double secondsLeft = 0;

    private final SimpleFrame frame;

    PrintMachine() {
        frame = new SimpleFrame("Print Machine", new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                final int width = getWidth();
                final int height = getHeight();

                // Determine value extents
                float minA = Float.POSITIVE_INFINITY;
                float maxA = Float.NEGATIVE_INFINITY;
                float minB = Float.POSITIVE_INFINITY;
                float maxB = Float.NEGATIVE_INFINITY;
                for (int i = 0; i < aBuffer.getSize(); i++) {
                    maxA = max(maxA, aBuffer.get(i));
                    minA = min(minA, aBuffer.get(i));
                    maxB = max(maxB, bBuffer.get(i));
                    minB = min(minB, bBuffer.get(i));
                }

                // Clear background
                g2.setColor(Color.BLACK);
                g2.fillRect(0,0,width,height);

                // Draw values
                int prevAPos = 0;
                int prevBPos = 0;
                for (int x = 0; x < width; x++) {
                    int index = (int) map(x, 0, width -1, BUFFER_WIDTH-1, 0);

                    if (index < aBuffer.getSize()) {
                        int aPos = (int) map(aBuffer.getFromEnd(index), minA, maxA, height, 0);
                        int bPos = (int) map(bBuffer.getFromEnd(index), minB, maxB, height, 0);

                        if (x > 0) {
                            g2.setColor(Color.BLUE);
                            g2.drawLine(x-1, prevBPos, x, bPos);

                            g2.setColor(Color.GREEN);
                            g2.drawLine(x-1, prevAPos, x, aPos);
                        }

                        prevAPos = aPos;
                        prevBPos = bPos;
                    }
                }
            }
        }, BUFFER_WIDTH, BUFFER_WIDTH/2);
    }

    @Override protected void doUpdate(Time time) {
        secondsLeft -= time.getSecondsSinceLastStep();

        if (secondsLeft <= 0) {
            // Update visible values
            secondsLeft = updateInterval.get();

            aBuffer.addLast(inputA.get());
            bBuffer.addLast(inputB.get());

            frame.repaint();
        }
    }
}
