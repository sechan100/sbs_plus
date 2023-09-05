package org.sbsplus.util;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class Datetime {
    
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
    
    @PreUpdate // 데이터 수정이 이루어질때 사전 작업
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
