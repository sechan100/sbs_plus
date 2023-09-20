package org.sbsplus.domain.event.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.domain.event.dto.EventDto;
import org.sbsplus.domain.event.entity.Event;
import org.sbsplus.domain.event.repository.EventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public List<Event> findLatest(int count) {
        PageRequest pageRequest = PageRequest.of(0, count);
        return eventRepository.findByOrderByIdDesc(pageRequest);
    }

    public List<Event> findLatestAfterId(int count, long lastId) {
        PageRequest pageRequest = PageRequest.of(0, count);

        if (lastId == 0) return eventRepository.findByOrderByIdDesc(pageRequest);

        return eventRepository.findByIdLessThanOrderByIdDesc(lastId, pageRequest);
    }

    @Transactional
    public Event write(String title, String eventDate, String comment) {
        Event event = Event.builder()
                .title(title)
                .eventDate(LocalDate.parse(eventDate))
                .comment(comment)
                .build();
        return eventRepository.save(event);
    }

    public List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByEventDateBetweenOrderByEventDateAsc(startDate, endDate);
    }

    @Override
    public EventDto findById(Long eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("해당 이벤트는 존재하지 않습니다."));

        return (new ModelMapper()).map(event, EventDto.class);
    }

    @Override
    @Transactional
    public void save(EventDto eventDto) {

        // 새로운 글 작성
        if (eventDto.getId() == null) {

            Event event = (new ModelMapper()).map(eventDto, Event.class);

            eventRepository.save(event);

            // 기존 글 수정
        } else {
            Event event = eventRepository.findById(eventDto.getId()).orElseThrow();
            event.setTitle(eventDto.getTitle());
            event.setEventDate(eventDto.getEventDate());
            event.setComment(eventDto.getComment());
        }
    }

    @Override
    public void delete(Long eventId) throws AccessDeniedException {
        eventRepository.deleteById(eventId);
    }
}