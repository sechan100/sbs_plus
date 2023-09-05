package org.sbsplus.type;

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
public enum Subject {


    // 해당 수업 과정 없음
      NONE("NONE", "과정 없음")

    // 자격증
    , CERTIFICATE("CERTIFICATE", "자격증")

    // 모션, CG쪽 편집 툴
    , MAYA("MAYA", "영상/모션/유튜브")

    // 인테리어
    , INTERIOR("INTERIOR", "건축/인테리어디자인")

    // IT, 개발
    , IT("IT", "웹디자인/IT")

    // Desk Top Publishing: 데스크톱 출판 디자인
    , DTP("DTP", "광고편집디자인")

    // 제품 디자인
    , PRODUCT("PRODUCT", "제품디자인")

    // 아트웍, 디자인
    , DESIGN("DESIGN", "아트웍");





    private final String subject_str;
    private final String categoryName;

    @Getter
    private static List<Subject> subjects = new ArrayList<>();
        static {
            subjects.add(NONE);
            subjects.add(CERTIFICATE);
            subjects.add(MAYA);
            subjects.add(INTERIOR);
            subjects.add(IT);
            subjects.add(DTP);
            subjects.add(PRODUCT);
            subjects.add(DESIGN);
        }



    public static Subject convertStringToEnum(String subject_){
        switch(subject_) {
            case "CERTIFICATE" -> {
                return Subject.CERTIFICATE;
            }
            case "MAYA" -> {
                return Subject.MAYA;
            }
            case "INTERIOR" -> {
                return Subject.INTERIOR;
            }
            case "IT" -> {
                return Subject.IT;
            }
            case "DTP" -> {
                return Subject.DTP;
            }
            case "PRODUCT" -> {
                return Subject.PRODUCT;
            }
            case "DESIGN" -> {
                return Subject.DESIGN;
            }
            default -> {
                return Subject.NONE;
            }
        }
    }
}
