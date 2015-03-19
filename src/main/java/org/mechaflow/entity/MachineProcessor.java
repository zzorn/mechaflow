package org.mechaflow.entity;

import org.entityflow.entity.Entity;
import org.entityflow.processors.EntityProcessorBase;
import org.flowutils.time.Time;

/**
 * Updates machine components of handled entities.
 */
public final class MachineProcessor extends EntityProcessorBase {

    private static final double DEFAULT_PROCESSING_INTERVAL_SECONDS = 0.1;
    private static final boolean DEFAULT_CONCURRENT_PROCESSING = true;

    /**
     * Creates a concurrent MachineProcessor with 0.1 second processing interval.
     */
    public MachineProcessor() {
        this(DEFAULT_PROCESSING_INTERVAL_SECONDS);
    }

    /**
     * Creates a concurrent MachineProcessor.
     *
     * @param processingIntervalSeconds number of seconds between each process pass of this processors, or zero to process as often as process() is called.
     */
    public MachineProcessor(double processingIntervalSeconds) {
        this(processingIntervalSeconds, DEFAULT_CONCURRENT_PROCESSING);
    }

    /**
     * @param processingIntervalSeconds number of seconds between each process pass of this processors, or zero to process as often as process() is called.
     * @param concurrentProcessing if true, entities may be processed in several threads concurrently, if false, entities are processed sequentially in the same thread.
     *                             If the processor does not modify any other entities or modify other shared data this can usually be set to true to gain some processing speed.
     */
    public MachineProcessor(double processingIntervalSeconds, boolean concurrentProcessing) {
        super(null, processingIntervalSeconds, concurrentProcessing, MachineComponent.class);
    }


    @Override protected final void processEntity(Time time, Entity entity) {
        // Update machine state
        entity.get(MachineComponent.class).getMachine().update(time);
    }

    @Override protected final void postProcess(Time time) {
        // Propagate updated output values from output ports to connected input ports
        for (Entity entity : getHandledEntities()) {
            entity.get(MachineComponent.class).getMachine().propagate(time);
        }
    }
}
