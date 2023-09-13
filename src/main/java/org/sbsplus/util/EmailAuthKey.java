package org.sbsplus.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailAuthKey {
    
    private final Map<String, String> authKeys = new HashMap<>();
    
    public void addAuthKey(String email, String authKey){
        authKeys.put(email, authKey);
    }
    
    public void removeAuthKey(String email){
        authKeys.remove(email);
    }
    
    public String getAuthKey(String email){
        return authKeys.get(email);
    }
}
