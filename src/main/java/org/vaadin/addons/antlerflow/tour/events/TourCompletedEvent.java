package org.vaadin.addons.antlerflow.tour.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Event class representing the completion of a guided tour.
 *
 * <p>This event is triggered when a guided tour successfully reaches its conclusion. It provides
 * information about the source component that initiated the event and whether the event was
 * triggered from the client-side.
 *
 * <p>The `TourCompletedEvent` can be used in conjunction with the guided tour framework to handle
 * post-completion actions, such as showing confirmation messages, updating application state, or
 * triggering additional workflows based on the tour's successful completion.
 */
public class TourCompletedEvent extends ComponentEvent<Component> {

    public TourCompletedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
    }
}
