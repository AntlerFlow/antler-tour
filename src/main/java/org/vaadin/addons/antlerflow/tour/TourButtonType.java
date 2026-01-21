package org.vaadin.addons.antlerflow.tour;

/**
 * Represents the type of a button used in a guided tour. Each type defines the role or action
 * associated with the button during the tour flow.
 *
 * <pre>
 * The following button types are supported:
 * - PREVIOUS: Represents a button that navigates to the previous step in the tour.
 * - NEXT: Represents a button that navigates to the next step in the tour.
 * - CANCEL: Represents a button that cancels and exits the tour.
 * </pre>
 *
 * This enum is used in conjunction with {@link TourButton} to define the behavior and purpose of
 * tour navigation buttons within a {@link TourStep}.
 */
public enum TourButtonType {
    PREVIOUS,
    NEXT,
    CANCEL;
}
