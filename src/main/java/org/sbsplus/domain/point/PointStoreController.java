package org.sbsplus.domain.point;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PointStoreController {
    
    private final PointStoreService pointStoreService;
    
    @GetMapping("/point/store")
    public String pointStore(Model model) {
        
        List<PointStoreItem> storeItems = pointStoreService.findAll();
        
        model.addAttribute("storeItems", storeItems);
        
        return "/point/store";
    }
    
    @GetMapping("/point/purchase")
    @ResponseBody
    public Integer purchase(@RequestParam Integer id) {
        
        boolean purchaseSuccess = pointStoreService.purchase(id);
        
        if (purchaseSuccess) {
            return pointStoreService.getQuantity(id);
        } else {
            return -1;
        }
    }
    
    
    
}