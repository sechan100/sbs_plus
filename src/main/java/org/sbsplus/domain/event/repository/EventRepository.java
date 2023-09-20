package org.sbsplus.domain.event.repository;


import org.sbsplus.domain.event.entity.Event;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrderByIdDesc(PageRequest pageRequest);

    List<Event> findByIdLessThanOrderByIdDesc(Long id, PageRequest pageRequest);

    List<Event> findByEventDateBetweenOrderByEventDateAsc(LocalDate startDate, LocalDate endDate);

}