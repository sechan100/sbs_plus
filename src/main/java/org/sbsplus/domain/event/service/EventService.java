package org.sbsplus.domain.event.service;


import org.sbsplus.domain.event.dto.EventDto;
import org.sbsplus.domain.event.entity.Event;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    List<Event> findLatest(int count);
    List<Event> findLatestAfterId(int count, long lastId);
    Event write(String title, String eventDate, String comment);
    EventDto findById(Long eventId);
    List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate);
    void save(EventDto eventDto);
    void delete(Long eventId) throws AccessDeniedException;

    List<Event> getCalendarEvents(int year, int month);
}