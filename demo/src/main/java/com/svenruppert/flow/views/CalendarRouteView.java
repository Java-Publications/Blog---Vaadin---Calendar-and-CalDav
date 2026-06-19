/*
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the EUPL, Version 1.2 (the "Licence");
 * you may not use this file except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *     https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package com.svenruppert.flow.views;

import com.svenruppert.vaadin.calendar.i18n.CalendarMessages;
import com.svenruppert.vaadin.calendar.state.CalendarStateStore;
import com.svenruppert.vaadin.calendar.state.VaadinSessionCalendarStateStore;
import com.svenruppert.vaadin.calendar.ui.CalendarView;
import com.svenruppert.flow.i18n.I18nSupport;
import com.svenruppert.flow.security.roles.VisibleFor;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static com.svenruppert.flow.security.roles.AuthorizationRole.USER;

/**
 * Host-side wrapper that carries the project-specific route +
 * permission annotations and embeds the portable {@link CalendarView}
 * composite. Once the calendar is extracted into its own Maven
 * artifact, this wrapper class remains in the consuming application
 * — it is the seam where the host stack (jSentinel role gate,
 * {@code MainLayout} drawer, {@code I18nSupport} translation
 * provider) plugs into the otherwise host-neutral {@code CalendarView}.
 */
@Route(value = CalendarRouteView.NAV, layout = MainLayout.class)
@VisibleFor(USER)
public final class CalendarRouteView extends Composite<VerticalLayout>
    implements I18nSupport {

  public static final String NAV = "calendar";

  public CalendarRouteView() {
    CalendarStateStore store = new VaadinSessionCalendarStateStore();
    CalendarMessages messages = this::tr;
    CalendarView calendar = new CalendarView(store, messages);
    getContent().setSizeFull();
    getContent().setPadding(false);
    getContent().setSpacing(false);
    getContent().add(calendar);
    getContent().expand(calendar);
  }
}
