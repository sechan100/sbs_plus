package org.sbsplus.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailAuthentication {
    
    private final Map<String, String> authKeys = new HashMap<>();
    private final Map<String, Boolean> authenticatedEmails = new HashMap<>();
    
    public void addAuthKey(String email, String authKey){
        authKeys.put(email, authKey);
    }
    
    public void removeEamilData(String email){
        authKeys.remove(email);
        authenticatedEmails.remove(email);
    }
    
    public String getAuthKey(String email){
        return authKeys.get(email);
    }
    
    public void addAuthenticatedEmail(String email){
        authenticatedEmails.put(email, true);
    }
    
    public Boolean isAuthenticatedEmail(String email){
        if(authenticatedEmails.get(email) == null){
            return false;
        }
        return authenticatedEmails.get(email);
    }
}
