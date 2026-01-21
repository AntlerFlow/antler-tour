package org.vaadin.addons.antlerflow.tour;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.shared.Registration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vaadin.addons.antlerflow.tour.events.TourCanceledEvent;
import org.vaadin.addons.antlerflow.tour.events.TourCompletedEvent;

import java.util.*;

/**
 * Represents a tour component that provides guided tours for user interfaces. A tour is a sequence
 * of steps, each representing an instructional message or action aimed at guiding users through
 * specific features or workflows in the application.
 *
 * <p>This class utilizes a pluggable {@link TourEngine} that defines the mechanics of how the tour
 * is executed. The engine can be selected by setting the {@code engineType}.
 *
 * <pre>
 * Features include:
 * - Option to show or hide a cancel button during the tour.
 * - Ability to control whether the tour can be closed.
 * - Events to listen for when a tour is canceled or completed.
 * - Customizable options for configuring behavior and appearance.
 * </pre>
 *
 * The necessary sequence of steps for the tour should be defined through the {@code steps} field.
 */
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Tag("antler-tour")
@JsModule("./antlerflow/tour/core/tour-facade.ts")
public class Tour extends Component {
    @Builder.Default private List<TourStep> steps = new ArrayList<>();
    @Builder.Default private boolean showCancelButton = true;
    @Builder.Default private boolean allowClose = false;
    @Builder.Default private final Map<Object, Object> options = new HashMap<>();
    @Builder.Default private EngineType engineType = EngineType.DRIVER;
    private TourEngine engine;

    public void start() {
        engine =
                findEngine(engineType)
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "Tour engine not found: " + engineType.getId()));
        engine.wireEvents(this);
        engine.start(this, steps, unifyOptions());
    }

    public void cancel() {
        if (engine == null) return;
        engine.cancel(this);
    }

    public Registration addTourCanceledListener(
            ComponentEventListener<TourCanceledEvent> listener) {
        return this.addListener(TourCanceledEvent.class, listener);
    }

    public Registration addTourCompletedListener(
            ComponentEventListener<TourCompletedEvent> listener) {
        return this.addListener(TourCompletedEvent.class, listener);
    }

    @ClientCallable
    private void onCanceled() {
        this.fireEvent(new TourCanceledEvent(this, false));
    }

    @ClientCallable
    private void onCompleted() {
        this.fireEvent(new TourCompletedEvent(this, false));
    }

    private Optional<TourEngine> findEngine(EngineType engineType) {
        return Optional.ofNullable(engineType).map(EngineType::getEngineInstance);
    }

    private Map<Object, Object> unifyOptions() {
        Map<Object, Object> unifiedOptions = new HashMap<>(this.options);
        unifiedOptions.put("showCancelButton", showCancelButton);
        unifiedOptions.put("allowClose", allowClose);
        return unifiedOptions;
    }
}
