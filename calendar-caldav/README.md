# calendar-caldav

Headless CalDAV wire + iCalendar mapping + service façade. Use it from
any JVM consumer — CLI tool, sync job, Spring Boot service, or as the
backing layer of the [`calendar-component`](../calendar-component) Vaadin
add-on.

## Coordinates

```xml
<dependency>
    <groupId>com.svenruppert.vaadin.calendar</groupId>
    <artifactId>calendar-caldav</artifactId>
    <version>00.10.00</version>
</dependency>
```

## What's in the box

| Package | Purpose |
|---|---|
| `com.svenruppert.vaadin.calendar.client` | `CalDavClient` (PUT / GET / DELETE / REPORT), `CalDavDiscovery` (three-step PROPFIND), classified `CalDavError` |
| `com.svenruppert.vaadin.calendar.mapping` | `EntryMapper` — VEVENT + VTODO ↔ FullCalendar `Entry` (Biweekly under the hood) |
| `com.svenruppert.vaadin.calendar.service` | `CalendarService` + `Result<T, CalDavError>` boundary; record types (`CalDavConnectionConfig`, `CalDavServerConnection`, `CalendarSubscription`, `CalDavProviderPreset`) |
| `com.svenruppert.vaadin.calendar.state` | `CalendarStateStore` interface (UI-agnostic) |
| `com.svenruppert.vaadin.calendar.i18n` | `CalendarMessages` functional interface |

The headless module brings `org.vaadin.stefan:fullcalendar2` as a
transitive — the FullCalendar `Entry` record is reused as the domain
DTO so the `calendar-component` UI can render it directly. CLI
consumers that only need the wire layer can use `CalDavClient` +
`RemoteEvent` directly and bypass the mapper.

## Minimal example

```java
URI collection = URI.create("https://caldav.example.org/calendars/personal/");
CalDavClient client = new CalDavClient(collection, "alice", "app-password");

CalendarService service = new CalendarService(client, ZoneId.systemDefault());

Result<Stream<Entry>, CalDavError> result =
    service.findInRangeAsResult(LocalDateTime.now(), LocalDateTime.now().plusDays(7));

result
    .peek(stream -> stream.forEach(e -> System.out.println(e.getTitle())))
    .peekFailure(err -> System.err.println("CalDAV failed: " + err));
```

## Quick-connect presets

`CalDavProviderPreset.DEFAULTS` ships with a curated list — currently
Apple iCloud — so a UI can offer one-click provider configuration:

```java
for (CalDavProviderPreset preset : CalDavProviderPreset.DEFAULTS) {
    System.out.println(preset.label() + " → " + preset.entryUri());
}
```

## License

Distributed under the [European Union Public Licence 1.2](https://joinup.ec.europa.eu/software/page/eupl).
