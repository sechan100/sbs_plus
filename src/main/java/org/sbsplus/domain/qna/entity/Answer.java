package org.sbsplus.domain.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.util.Datetime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Answer extends Datetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean accepted;
}