package org.sbsplus.general.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 실제 웹 SBS 웹 사이트 HTML 코드에 영어로 써있는 이름대로 가져왔음.
 *  MAYA: 영상, CG 쪽에서 주로 사용하는 툴
 *  DTP: "Desk Top Publishing: 데스크톱 출판 디자인"의 약어. 개인용 컴퓨터 단위의 퍼블리싱
 */
@RequiredArgsConstructor
@Getter
public enum Category {

    // 해당 수업 과정 없음
      NONE("NONE", "과정 없음", "bg-white")
    
    // 전체 카테고리
    ,  ALL("ALL", "전체 카테고리", "bg-white")

    // 자격증
    , CERTIFICATE("CERTIFICATE", "자격증", "badge-gray")

    // 모션, CG쪽 편집 툴
    , MAYA("MAYA", "영상/모션/유튜브", "badge-red")

    // 인테리어
    , INTERIOR("INTERIOR", "건축/인테리어디자인", "badge-yellow")

    // IT, 개발
    , IT("IT", "웹디자인/IT", "badge-purple")

    // Desk Top Publishing: 데스크톱 출판 디자인
    , DTP("DTP", "광고편집디자인", "badge-pink")

    // 제품 디자인
    , PRODUCT("PRODUCT", "제품디자인", "badge-green")

    // 아트웍, 디자인
    , DESIGN("DESIGN", "아트웍", "badge-blue");





    private final String value;
    private final String name;
    private final String tailwindBadge;
    
    @Getter
    private static List<Category> categories = new ArrayList<>();
    
        static {
            categories.add(CERTIFICATE);
            categories.add(MAYA);
            categories.add(INTERIOR);
            categories.add(IT);
            categories.add(DTP);
            categories.add(PRODUCT);
            categories.add(DESIGN);
        }



    public static Category convertStringToEnum(String category){
        
        switch(category) {
            case "CERTIFICATE" -> {
                return Category.CERTIFICATE;
            }
            case "MAYA" -> {
                return Category.MAYA;
            }
            case "INTERIOR" -> {
                return Category.INTERIOR;
            }
            case "IT" -> {
                return Category.IT;
            }
            case "DTP" -> {
                return Category.DTP;
            }
            case "PRODUCT" -> {
                return Category.PRODUCT;
            }
            case "DESIGN" -> {
                return Category.DESIGN;
            }
            case "NONE" -> {
                return Category.NONE;
            }
            case "ALL" -> {
                return Category.ALL;
            }
            default ->  {
                return null;
            }
        }
    }
}
