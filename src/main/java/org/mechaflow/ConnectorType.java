package org.mechaflow;

/**
 * Some type of connection between ports.
 */
public interface ConnectorType {

    /**
     * @return true if the two connector types can be connected.
     */
    boolean canConnect(ConnectorType connectorType);
}
