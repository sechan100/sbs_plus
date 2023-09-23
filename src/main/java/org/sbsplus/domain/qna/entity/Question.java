package org.sbsplus.domain.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Datetime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @OneToMany( cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Answer> answers = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    private Answer acceptedAnswer;

    private int point;

    public String extractThumbNail(){
        Pattern pattern = Pattern.compile("<img[^>]*>");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {

            return matcher.group();

        } else {
            return "<img class='w-1/16 h-1/16' src='https://ifh.cc/g/9AqA7q.png' />";
        }
    }

    public String convertRelativeTime(){
        int SEC = 60;
        int MIN = 60;
        int HOUR = 24;
        int DAY = 30;
        int MONTH = 12;

        LocalDateTime now = LocalDateTime.now();

        long diffTime = super.getCreateAt().until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        String msg = null;
        if (diffTime < SEC){
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / MONTH;
        return diffTime + "년 전";
    }
}