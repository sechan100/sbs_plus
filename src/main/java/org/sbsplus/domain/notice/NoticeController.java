package org.sbsplus.domain.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.form.AnswerForm;
import org.sbsplus.domain.qna.form.QuestionForm;
import org.sbsplus.domain.qna.service.AnswerService;
import org.sbsplus.domain.qna.service.QuestionService;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Notice> paging = this.noticeService.getList(page);
        model.addAttribute("paging", paging);
        return "/notice/notice_list";
    }
    @GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "/notice/notice_detail";
    }
    @GetMapping("/create")
    public String noticeCreate(Model model, NoticeForm noticeForm) {
        model.addAttribute("categories", Category.getCategories());
        return "/notice/notice_form";
    }
    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult, @RequestParam String category) {
        if(bindingResult.hasErrors()) {
            return "/notice/notice_form";
        }
        this.noticeService.create(noticeForm.getSubject(), noticeForm.getContent(), category);
        return "redirect:/notice";
    }

    @GetMapping("/modify/{id}")
    public String noticeModify(Model model, NoticeForm noticeForm, @PathVariable("id") Long id, Rq rq) {
        Notice notice = this.noticeService.getNotice(id);

        if(!rq.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        model.addAttribute("categories", Category.getCategories());
        model.addAttribute("notice", notice);

        noticeForm.setSubject(notice.getSubject());
        noticeForm.setContent(notice.getContent());
        return "/notice/notice_modify";
    }

    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                                 Rq rq, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "/notice/notice_modify";
        }
        Notice notice = this.noticeService.getNotice(id);
        if (!rq.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.noticeService.modify(notice, noticeForm.getSubject(), noticeForm.getContent(), noticeForm.getCategory());
        return String.format("redirect:/notice/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String noticeDelete(Rq rq, @PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);
        if (!rq.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.noticeService.delete(notice);
        return "redirect:/notice";
    }
}