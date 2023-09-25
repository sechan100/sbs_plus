package org.sbsplus.domain.point;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.repository.UserRepository;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointStoreServiceImpl implements PointStoreService {
    
    private final Rq rq;
    private final UserRepository userRepository;
    private final PointStoreItemRepository pointStoreRepository;
    @Override
    public List<PointStoreItem> findAll() {
        return pointStoreRepository.findAll();
    }
    
    @Override
    public boolean purchase(Integer itemId) {
        
        // 해당 아이템의 포인트만큼 사용자의 포인트가 차감
        PointStoreItem item = pointStoreRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
        
        int itemPrice = item.getPrice();
        User currentUser = userRepository.findByUsername(rq.getUser().getUsername());
        int currentUserPoint = currentUser.getPoint();
        
        
        // quntity가 0이면 구매 불가
        if (item.getQuantity() == 0) {
            return false;
        }
        
        
        // 구매 실패
        if (currentUserPoint < itemPrice) {
            return false;
            
        // 구매 성공
        } else {
            currentUser.setPoint(currentUserPoint - itemPrice);
            userRepository.save(currentUser);
            
            item.setQuantity(item.getQuantity() - 1);
            pointStoreRepository.save(item);
            
            return true;
            
        }
    }
    
    @Override
    public Integer getQuantity(Integer itemId) {
        
        PointStoreItem item = pointStoreRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
        
        return item.getQuantity();
    }
    
    @Override
    public PointStoreItem findById(Integer id) {
        
        return pointStoreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
    }
}
