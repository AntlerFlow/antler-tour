package org.vaadin.addons.antlerflow.tour.engines;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import org.vaadin.addons.antlerflow.tour.EngineType;
import org.vaadin.addons.antlerflow.tour.TourEngine;

/**
 * Implementation of the {@link TourEngine} interface using the "driver.js" library as the
 * underlying engine for guided tours.
 *
 * <p>This class facilitates the integration of the "driver" tour engine into a Vaadin application,
 * allowing developers to easily implement guided tours using the methods defined in the {@link
 * TourEngine} interface.
 *
 * <p>The engine is identified by its unique identifier "driver", which is provided by the
 * overridden {@code getId} method. This identifier is used to distinguish this engine from others,
 * such as "shepherd," when managing multiple engine types in the application.
 *
 * <pre>
 * Dependencies:
 * - {@code driver.js}: Version 1.4.0, included as an NPM package.
 * - {@code bridge.ts}: JavaScript bridge module for communication between server-side and
 *   client-side components.
 *
 * Notes:
 * - This class relies on the {@link EngineType#DRIVER} enumeration value to retrieve the unique
 *   engine identifier.
 * - The {@code getId} method is used internally by the framework to ensure the correct engine is
 *   activated for a guided tour.
 * </pre>
 */
@JsModule("./antlerflow/tour/engines/bridge.ts")
@NpmPackage(value = "driver.js", version = "1.4.0")
public class DriverEngine implements TourEngine {

    @Override
    public String getId() {
        return EngineType.DRIVER.getId();
    }
}
