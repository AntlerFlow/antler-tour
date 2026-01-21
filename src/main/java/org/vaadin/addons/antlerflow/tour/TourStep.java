package org.vaadin.addons.antlerflow.tour;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a single step within a guided tour. Each step contains details about the content to be
 * displayed, its position in the UI, and associated action buttons that allow navigation between
 * steps.
 *
 * <pre>
 * A TourStep typically includes the following:
 * - A unique identifier for the step.
 * - A title for the step.
 * - A description or instructional content to guide the user.
 * - A target UI element to which the step is attached.
 * - The position where the step is displayed relative to the target UI element.
 * - A list of navigation buttons (e.g., Previous, Next) that are customizable.
 * </pre>
 *
 * The default configuration provides "Previous" and "Next" buttons for navigation. Additional
 * buttons can be added as needed to customize the user experience.
 *
 * <p>This class is serializable and can be used to persist tour steps or transmit them over a
 * network.
 */
@Builder
@Data
public class TourStep implements Serializable {
    @Builder.Default private String id = UUID.randomUUID().toString();
    private String title;
    private String attachTo;
    private String position;
    private String content;

    @Builder.Default
    private List<TourButton> buttons =
            new ArrayList<>(
                    List.of(
                            TourButton.builder()
                                    .label("Previous")
                                    .secondary(true)
                                    .type(TourButtonType.PREVIOUS)
                                    .build(),
                            TourButton.builder().label("Next").type(TourButtonType.NEXT).build()));
}
