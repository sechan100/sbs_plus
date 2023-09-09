package org.sbsplus.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    @GetMapping("")
    public String list() {
        return "qna/question_list";
    }
}
