package org.sbsplus.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.cummunity.entity.Comment;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.general.type.Category;

import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;

    private String password;

    private String nickname;
    
    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String role;

    private int point;
    
    private int accumulatedPoint;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Article> articles;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes;
    
    
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    public void addPoints(int point) {
        if (point < 0){
            throw new IllegalArgumentException("음수의 포인트를 더할 수 없습니다.");
        }
        this.point += point;
        this.accumulatedPoint += point;
    }

    public void subtractPoints(int point) {
        if (point < 0){
            throw new IllegalArgumentException("음수의 포인트를 뺄 수 없습니다.");
        }
        this.point -= point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

}









