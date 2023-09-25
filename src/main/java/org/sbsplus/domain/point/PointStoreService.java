package org.sbsplus.domain.point;

import java.util.List;

interface PointStoreService {
    
    
    List<PointStoreItem> findAll();
    
    boolean purchase(Integer itemId);
    
    Integer getQuantity(Integer itemId);
}
