package org.sbsplus.domain.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Datetime;

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Enumerated(STRING)
    private Category category;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Answer> answers = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    private Answer acceptedAnswer;

    private int point;
}