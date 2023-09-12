package org.sbsplus.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pager {
    
    public static List<Integer> getPageRange(Integer page, Integer totalPage) {
        
        List<Integer> pageRange = new ArrayList<>();
        if(totalPage < 5){
            for(int i = totalPage; 0 < i; i--){
                pageRange.add(i);
            }
            Collections.sort(pageRange);
            
        } else if(page > totalPage - 3){
            pageRange.add(totalPage - 4);
            pageRange.add(totalPage - 3);
            pageRange.add(totalPage - 2);
            pageRange.add(totalPage - 1);
            pageRange.add(totalPage);
        } else {
            if(page <= 3){
                pageRange.add(1);
                pageRange.add(2);
                pageRange.add(3);
                pageRange.add(4);
                pageRange.add(5);
            } else {
                pageRange.add(page - 2);
                pageRange.add(page - 1);
                pageRange.add(page);
                pageRange.add(page + 1);
                pageRange.add(page + 2);
            }
            
        }
        return pageRange;
    }
}
