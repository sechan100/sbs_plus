package org.sbsplus.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.qna.entity.Answer;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Question extends Datetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(STRING)
    private Category category;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Answer> answers = new ArrayList<>();


}