package org.vaadin.addons.antlerflow.tour;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

public class ViewIT extends AbstractViewTest {

    @Test
    public void componentWorks() {
        final TestBenchElement startTourBtn = $("#start-tour-button").waitForFirst();
        startTourBtn.click();
        // Check that axa-text contains at least one other element, which means that
        // is has been upgraded to a custom element and not just rendered as an empty
        // tag
        final TestBenchElement tourElement = $(".shepherd-element").waitForFirst();
        Assert.assertTrue(tourElement.$(TestBenchElement.class).all().size() > 0);
    }
}
