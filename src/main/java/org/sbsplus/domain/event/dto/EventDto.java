package org.sbsplus.domain.event.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventDto{
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private LocalDate eventDate;
    private String title;
    private String comment;
}
