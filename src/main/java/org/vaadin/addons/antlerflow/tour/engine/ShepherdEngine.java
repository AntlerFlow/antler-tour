package org.vaadin.addons.antlerflow.tour.engine;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import org.vaadin.addons.antlerflow.tour.EngineType;
import org.vaadin.addons.antlerflow.tour.TourEngine;

/**
 * Implementation of the {@link TourEngine} interface using the "shepherd.js" library as the
 * underlying engine for guided tours.
 *
 * <p>This class facilitates the integration of the "shepherd" tour engine into a Vaadin
 * application, enabling developers to create guided tours with the methods defined by the {@link
 * TourEngine} interface.
 *
 * <p>The engine is identified by its unique identifier "shepherd", which is specified in the {@code
 * getId} method. The identifier is used to distinguish this engine from others, such as "driver",
 * for applications that utilize multiple engine types.
 *
 * <pre>
 * Dependencies:
 * - {@code shepherd.js}: Version 13.0.3, included as an NPM package.
 * - {@code bridge.ts}: JavaScript bridge module for enabling communication between server-side
 *   and client-side components.
 *
 * Notes:
 * - This class relies on the {@link EngineType#SHEPHERD} enumeration value to retrieve the unique
 *   engine identifier.
 * - The {@code getId} method is used internally by the framework to ensure that the appropriate
 *   engine is selected and activated for guided tours.
 * </pre>
 */
@JsModule("./antlerflow/tour/engines/bridge.ts")
@NpmPackage(value = "shepherd.js", version = "13.0.3") // < 13.0.3 is MIT license
public class ShepherdEngine implements TourEngine {

    @Override
    public String getId() {
        return EngineType.SHEPHERD.getId();
    }
}
