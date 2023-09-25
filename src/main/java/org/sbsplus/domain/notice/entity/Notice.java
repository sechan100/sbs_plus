package org.sbsplus.domain.notice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Datetime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@EqualsAndHashCode(callSuper = false)
@Getter @Setter
@Entity
@NoArgsConstructor
public class Notice extends Datetime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private int hit;

    public void increaseHit(){
        this.setHit(this.getHit()+1);
    }

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