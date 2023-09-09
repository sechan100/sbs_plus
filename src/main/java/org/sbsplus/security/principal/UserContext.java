package org.sbsplus.security.principal;


import lombok.Getter;
import org.sbsplus.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserContext extends org.springframework.security.core.userdetails.User {
    // 사용자 정보(user)와 사용자의 권한(GrantedAuthority)를 함께 담는 UserContext클래스 정의.
    // 사용자 정보와 권한을 하나의 객체로 표현.
    @Getter
    private User user; //사용자 정보


    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        // UserContext 생성자. user객체와 권한 목록을 받아 UserContext 객체를 초기화 시킴.
        super(user.getUsername(), user.getPassword(), authorities);
        // 부모 클래스인 org.springframework.security.core.userdetails.User 의 생성자를 호출하여
        // 사용자 이름,비밀번호,권한 목록 설정
        this.user = user; // 내부의 user 필드에 사용자 정보 저장.
    }
    // 사용자 정보와 권한을 함께 다루기 위해 사용. 사용자 정보를 갖는 user객체와 해당 사용자의 권한 목록을 포함하는
    // userContext 객체를 생성할 수 있도록 도와준다. 사용자 관련 정보를 효율적으로 전달하고 처리 가능

}
