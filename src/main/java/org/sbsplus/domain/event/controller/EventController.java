package org.sbsplus.domain.event.controller;

import org.sbsplus.domain.event.dto.EventDto;
import org.sbsplus.domain.event.entity.Event;
import org.sbsplus.domain.event.service.EventService;
import org.sbsplus.util.Ut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/calendar")
    public String showCalendar_() {
        LocalDate today = LocalDate.now();

        return "redirect:/calendar/" + today.getYear() + "/" + Ut.str.padWithZeros(today.getMonthValue(), 2);
    }

    @GetMapping("/calendar/{year}/{month}")
    public String showCalendar(@PathVariable String year, @PathVariable String month, Model model) {
        LocalDate startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Event> events = eventService.findByEventDateBetween(startDate, endDate);

        Map<Integer, List<Event>> forCalendarEvents = events.stream()
                .collect(Collectors.groupingBy(event -> event.getEventDate().getDayOfMonth()));
        model.addAttribute("forCalendarEvents", forCalendarEvents);

        model.addAttribute("events", events);

        DayOfWeek firstDayOfMonth = startDate.getDayOfWeek();
        int daysInMonth = YearMonth.of(startDate.getYear(), startDate.getMonthValue()).lengthOfMonth();

        model.addAttribute("year", startDate.getYear());
        model.addAttribute("month", startDate.getMonthValue());
        model.addAttribute("firstDayOfMonth", firstDayOfMonth.getValue() + 1);
        model.addAttribute("daysInMonth", daysInMonth);

        LocalDate prevMonth = startDate.minusMonths(1);
        LocalDate nextMonth = startDate.plusMonths(1);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", Ut.str.padWithZeros(prevMonth.getMonthValue(), 2));
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", Ut.str.padWithZeros(nextMonth.getMonthValue(), 2));

        return "/calendar/calendar";
    }

    @GetMapping("/event/detail/{eventId}")
    public String eventDetail(@PathVariable Long eventId, Model model){

        EventDto event = eventService.findById(eventId);
        model.addAttribute("event", event);

        return "/calendar/eventDetail";
    }

    @GetMapping("/event/write")
    public String eventWriteForm(Model model, @RequestParam(required = false) Long id) throws AccessDeniedException {

        EventDto eventDto;
        if(id == null) {

            eventDto = new EventDto();

        } else {
            eventDto = eventService.findById(id);
        }
        model.addAttribute("event", eventDto);

        return "/calendar/eventWriteForm";
    }

    @PostMapping("/event/write")
    public String eventWritePrcs(EventDto eventDto, @RequestParam(required = false) Long id){

        eventService.save(eventDto);

        return "redirect:/calendar";
    }

    @GetMapping("/event/delete")
    public String eventDelete(@RequestParam Long id) throws AccessDeniedException {

        eventService.delete(id);

        return "redirect:/calendar";
    }


}








