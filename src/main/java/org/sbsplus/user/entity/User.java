package org.sbsplus.user.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.type.Category;

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
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean emailAuth;
    
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
    public boolean getEmailAuth() {
        return this.emailAuth;
    }

}