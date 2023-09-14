package org.sbsplus.general.security.principal;


import lombok.Getter;
import org.sbsplus.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserContext extends org.springframework.security.core.userdetails.User {

    private final User user;


    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }


}
