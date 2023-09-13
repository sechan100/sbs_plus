package org.sbsplus.qna.form;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.qna.NonNegative;
import org.sbsplus.qna.entity.Answer;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    @NonNegative(message="포인트는 0 이상이어야 합니다.")
    private int point;

    public int bettingPoint(int point){
        if (point < 100){
            throw new IllegalArgumentException("최소 채택 제공 포인트는 100포인트입니다");
        } else {
            return point;
        }
    }
}
