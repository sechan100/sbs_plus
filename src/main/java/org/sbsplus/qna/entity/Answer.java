package org.sbsplus.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.util.Datetime;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer extends Datetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

}