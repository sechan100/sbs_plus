package org.sbsplus.security.principal;


import lombok.Getter;
import org.sbsplus.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserContext extends org.springframework.security.core.userdetails.User {

    @Getter
    private User user;


    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }


}
