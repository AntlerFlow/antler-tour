package org.vaadin.addons.antlerflow.tour;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;

import java.util.*;

/**
 * Interface representing the core functionality of a tour engine. A tour engine facilitates guided
 * tours in a web application, allowing developers to define steps, manage behaviors, and handle
 * user interactions during the tour.
 *
 * <p>Implementing classes must provide the unique identifier of the engine via the {@code getId}
 * method and may extend behavior by overriding default methods.
 *
 * <pre>
 * Core functionality includes:
 * - Starting a tour for a given component with defined steps and options.
 * - Canceling an active tour.
 * - Wiring events to handle tour completion or cancellation.
 *
 * Static Fields:
 * - {@code GSON}: A pre-configured Gson instance for serializing and deserializing data related
 *   to tour steps and options.
 *
 * Methods:
 * - {@code String getId()}: Returns the unique identifier for the engine.
 *
 * Default Methods:
 * - {@code void start(Component host, List<TourStep> steps, Map<Object, Object> options)}:
 *   Starts a guided tour on the given host component, initializing it with the provided steps
 *   and options.
 * - {@code void cancel(Component host)}: Cancels the currently active tour.
 * - {@code void wireEvents(Component host)}: Wires event listeners to handle tour completion
 *   and cancellation and sends notifications to the component's server-side counterpart.
 *
 * Notes:
 * - The host component must be attached to a UI for the interactions with the underlying JavaScript
 *   API to work.
 * - Exceptions during execution are caught and rethrown as {@code RuntimeException}.
 * </pre>
 */
public interface TourEngine {
    Gson GSON = new Gson();

    String getId();

    default void start(Component host, List<TourStep> steps, Map<Object, Object> options) {
        try {
            host.getUI()
                    .ifPresentOrElse(
                            ui ->
                                    ui.getPage()
                                            .executeJs(
                                                    "return window.AntlerTour.start($0, JSON.parse($1), JSON.parse($2));",
                                                    getId(),
                                                    GSON.toJson(steps),
                                                    GSON.toJson(options)),
                            () -> {
                                throw new IllegalStateException(
                                        "Component must be attached to a UI");
                            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default void cancel(Component host) {
        try {
            host.getUI()
                    .ifPresentOrElse(
                            ui -> ui.getPage().executeJs("return window.AntlerTour.cancel();"),
                            () -> {
                                throw new IllegalStateException(
                                        "Component must be attached to a UI");
                            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default void wireEvents(Component host) {
        host.getUI()
                .ifPresent(
                        ui ->
                                ui.getPage()
                                        .executeJs(
                                                """
    window.AntlerTour.on('complete', () => $0.$server.onCompleted());
    window.AntlerTour.on('cancel', () => $0.$server.onCanceled());
    """,
                                                host.getElement()));
    }
}
