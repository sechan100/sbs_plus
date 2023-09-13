package org.sbsplus.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Answer extends Datetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean accepted;
}