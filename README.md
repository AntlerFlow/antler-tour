# Antler Tour

This project provides a flexible and powerful guided tour (onboarding) integration for Vaadin Flow. Unlike simple wrappers, this add-on provides a unified Java API to drive two of the most popular JavaScript tour engines: `Shepherd.js` and `Driver.js`.

## Features
- Dual Engine Support: Choose between `Shepherd.js` (rich, highly customizable) and `Driver.js` (lightweight, powerful focus features).
- Unified Java API: Switch engines without rewriting your tour logic.
- Server-side Control: Define steps, content, and logic entirely in Java.
- Event Handling: Listen for tour completion or cancellation on the server side.
- Auto-Focus & Scrolling: Intelligent handling of element positioning and smooth scrolling.

## Dependencies

- driver.js@1.4.0
- shepherd.js@13.0.3

## Installation

### Prerequisites
- Java 17+
- Vaadin 24+

Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.vaadin.addons.antlerflow</groupId>
    <artifactId>antler-tour</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick start

### Define your Tour

Below is a sample code snippet that shows you have to define a sample tour with two steps.

```java
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
```

### Handle Tour Events

```java
tour.addTourCompletedListener(event -> {
    Notification.show("User finished the tour!");
});

tour.addTourCanceledListener(event -> {
    Notification.show("Tour skipped.");
});
```
## Styling

Below is a sample CSS snippet that customizes the default styles of the Shepherd.js and Driver.js.

```css
.shepherd-title, .driver-popover-title {
  font-size: var(--lumo-font-size-l) !important;
  font-weight: var(--font-weight-bold) !important;
  font-family: var(--lumo-font-family), serif !important;
}

.shepherd-text, .driver-popover-description {
  padding-top: var(--lumo-space-m) !important;
  padding-bottom: var(--lumo-space-m) !important;
  font-size: var(--lumo-font-size-m) !important;
  font-family: var(--lumo-font-family), serif !important;
}

.shepherd-button, .driver-button {
  display: flex !important;
  align-items: center !important;
  background-color: var(--vaadin-button-primary-background) !important;
  font-size: var(--vaadin-button-font-size) !important;
  font-weight: var(--vaadin-button-font-weight) !important;
  font-family: var(--vaadin-button-font-family), serif !important;
  color: var(--lumo-body-text-color) !important;
}

.shepherd-button.shepherd-button-secondary, .driver-button.secondary {
  background-color: var(--vaadin-button-background) !important;
}
```

## Engine Comparison

| Feature  | Shepherd.js                  | Driver.js                       |
|----------|------------------------------|---------------------------------|
| Best for | Highly styled, complex tours | Fast, performant, focused tours |
| Visuals  | Floating cards with arrows   | High-contrast overlays          |

## Development instructions

JavaScript modules can either be published as an NPM package or be kept as local 
files in your project. The local JavaScript modules should be put in 
`src/main/resources/META-INF/frontend` so that they are automatically found and 
used in the using application.

If the modules are published then the package should be noted in the component 
using the `@NpmPackage` annotation in addition to using `@JsModule` annotation.


Starting the test/demo server:
1. Run `mvn jetty:run`
2. Open http://localhost:8080 in the browser

## Publishing to Vaadin Directory

You can create the zip package needed for [Vaadin Directory](https://vaadin.com/directory/) using
```
mvn versions:set -DnewVersion=1.0.0 # You cannot publish snapshot versions 
mvn install -Pdirectory
```

The package is created as `target/antler-tour-1.0.0.zip`

For more information or to upload the package, visit https://vaadin.com/directory/my-components?uploadNewComponent
