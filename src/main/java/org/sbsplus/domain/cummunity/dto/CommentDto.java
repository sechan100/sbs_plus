package org.sbsplus.domain.cummunity.dto;

import lombok.Data;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDto {
    
    private Integer id;
    
    private User user;
    
    private List<Like> likes = new ArrayList<>();
    
    private String content;
    
    private LocalDateTime createAt;
    
    private LocalDateTime updateAt;
    
    public String convertRelativeTime(){
        int SEC = 60;
        int MIN = 60;
        int HOUR = 24;
        int DAY = 30;
        int MONTH = 12;
        
        LocalDateTime now = LocalDateTime.now();
        
        long diffTime = this.createAt.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -
        
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
