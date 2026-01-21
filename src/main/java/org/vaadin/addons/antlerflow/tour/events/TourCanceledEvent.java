package org.vaadin.addons.antlerflow.tour.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Event class representing the cancellation of a guided tour.
 *
 * <p>This event is fired when a user cancels the guided tour, either via user interaction or
 * programmatically. It provides details about the source component that triggered the event and
 * whether the event originated from the client-side.
 *
 * <p>The `TourCanceledEvent` is designed to be used in conjunction with the guided tour framework
 * to perform additional actions, such as cleaning up resources or notifying other parts of the
 * application about the cancellation of the tour.
 */
public class TourCanceledEvent extends ComponentEvent<Component> {
    public TourCanceledEvent(Component source, boolean fromClient) {
        super(source, fromClient);
    }
}
