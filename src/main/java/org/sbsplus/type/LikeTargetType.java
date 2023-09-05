package org.sbsplus.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum LikeTargetType {
    ARTICLE("ARTICLE"),
    COMMENT("COMMENT");
    
    
    
    private String likeTargetType_str;
    LikeTargetType(String likeTargetType_str){
        this.likeTargetType_str = likeTargetType_str;
    }
    
}
