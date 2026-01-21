package org.vaadin.addons.antlerflow.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the types of engines that can be used for guided tours.
 *
 * <pre>
 * The available engine types include:
 * - DRIVER: Represents the "driver" engine for managing tours.
 * - SHEPHERD: Represents the "shepherd" engine for managing tours.
 * </pre>
 *
 * Each engine type has a unique identifier associated with it, which can be retrieved using the
 * `getId` method.
 *
 * <p>This enum is used in the {@link Tour} class to specify the underlying tour engine that drives
 * the tour functionality.
 */
@AllArgsConstructor
public enum EngineType {
    DRIVER("driver"),
    SHEPHERD("shepherd");

    @Getter private final String id;
}
