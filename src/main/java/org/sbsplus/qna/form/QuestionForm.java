package org.sbsplus.qna.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.type.Category;

@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    private int point;

    private Category category;
    public int bettingPoint(int point){
        if (point < 100){
            throw new IllegalArgumentException("최소 채택 제공 포인트는 100포인트입니다");
        } else {
            return point;
        }
    }
}
