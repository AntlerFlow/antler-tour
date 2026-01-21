package org.vaadin.addons.antlerflow.tour;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a button that can be displayed in a guided tour step. A TourButton allows users to
 * interact with the tour, such as navigating between steps or canceling the tour.
 *
 * <pre>
 * Features of a TourButton include:
 * - A customizable label for the button text.
 * - A flag indicating if the button is styled as a secondary action.
 * - Custom CSS classes to define the button's appearance.
 * - A predefined button type to specify its function (e.g., NEXT, PREVIOUS, CANCEL).
 * </pre>
 *
 * This class is intended to be used in conjunction with {@link TourStep} to create interactive
 * navigation for guided tours.
 *
 * <p>The default type of the button is {@code TourButtonType.NEXT}.
 *
 * <p>This class implements {@link Serializable} to ensure that it can be serialized for persistence
 * or network transmission.
 */
@Data
@Builder
public class TourButton implements Serializable {
    private String label;
    private boolean secondary;
    private String classes;
    @Builder.Default private TourButtonType type = TourButtonType.NEXT;
}
