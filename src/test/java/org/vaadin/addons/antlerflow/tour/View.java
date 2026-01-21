package org.vaadin.addons.antlerflow.tour;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;
import java.util.Map;

@Route("")
public class View extends VerticalLayout {

    public View() {

        List<TourStep> steps =
                List.of(
                        TourStep.builder()
                                .id("step-filter-panel")
                                .title("Filter & Search Data")
                                .content(
                                        "Use this panel to filter records by date, status, or keyword. "
                                                + "Filters help you narrow down large datasets and find information faster.")
                                .attachTo("#filter-panel")
                                .buttons(
                                        List.of(
                                                TourButton.builder()
                                                        .label("Next")
                                                        .type(TourButtonType.NEXT)
                                                        .build())
                                )
                                .build(),
                        TourStep.builder()
                                .id("step-dashboard-overview")
                                .title("Dashboard Overview")
                                .content(
                                        "This dashboard gives you a real-time overview of key metrics, system status, and recent activity. "
                                                + "Use it to quickly understand what’s happening in your application.")
                                .attachTo(".dashboard-summary")
                                .position("right")
                                .buttons(
                                        List.of(
                                                TourButton.builder()
                                                        .label("Back")
                                                        .secondary(true)
                                                        .type(TourButtonType.PREVIOUS)
                                                        .build(),
                                                TourButton.builder()
                                                        .label("Finish")
                                                        .type(TourButtonType.NEXT)
                                                        .build())
                                )
                                .build()
                );

        Tour tour =
                Tour.builder()
                        .engineType(EngineType.SHEPHERD)
                        .showCancelButton(true)
                        .allowClose(false)
                        .steps(steps)
                        .options(
                                Map.of(
                                        "keyboardNavigation", "false"))
                        .build();

        Button startTourBtn = new Button("Start Tour", e -> tour.start());
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setPadding(true);
        buttonRow.add(startTourBtn);
        startTourBtn.addClassName(LumoUtility.Padding.MEDIUM);
        add(createFilterPanel(), createDashboardSummary(), buttonRow, tour);
    }

    private Component createFilterPanel() {
        HorizontalLayout filterPanel = new HorizontalLayout();
        filterPanel.setPadding(true);
        filterPanel.setId("filter-panel");
        filterPanel.setAlignItems(Alignment.END);

        DatePicker fromDate = new DatePicker("From date");
        DatePicker toDate = new DatePicker("To date");

        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems("All", "Pending", "Completed", "Failed");
        status.setValue("All");

        TextField keyword = new TextField("Keyword");
        keyword.setPlaceholder("Search by ID or name");

        Button applyFilter = new Button("Apply Filters");

        filterPanel.add(fromDate, toDate, status, keyword, applyFilter);

        return filterPanel;
    }

    private Component createDashboardSummary() {
        HorizontalLayout dashboardSummary = new HorizontalLayout();
        dashboardSummary.setPadding(true);
        dashboardSummary.addClassName("dashboard-summary");

        dashboardSummary.add(
                createMetricCard("Total Orders", "1,248", "+12%"),
                createMetricCard("Active Users", "342", "+5%"),
                createMetricCard("Revenue", "$84,560", "+8%")
        );
        return dashboardSummary;
    }

    private Div createMetricCard(String title, String value, String trend) {
        Div card = new Div();
        card.getStyle()
                .set("padding", "16px")
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "8px")
                .set("min-width", "180px");

        card.add(
                new H4(title),
                new Span(value),
                new Span(trend)
        );
        return card;
    }
}
