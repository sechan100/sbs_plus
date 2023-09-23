package org.sbsplus.domain.notice.controller;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.cummunity.dto.ArticleDto;
import org.sbsplus.domain.cummunity.service.ArticleService;
import org.sbsplus.domain.notice.dto.NoticeDto;
import org.sbsplus.domain.notice.service.NoticeService;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Pager;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final Rq rq;


    // 게시글 리스트
    @GetMapping("/notice")
    public String noticeList(
            @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "createAt", name = "order") String orderColumn
            , @RequestParam(defaultValue = "") String searchMatcher
            , Model model
    ){

        Page<NoticeDto> notices = null;

        Set<String> orderColumns = new HashSet<>();
        orderColumns.add("createAt");
        orderColumns.add("hit");

        if(!orderColumns.contains(orderColumn)){
            orderColumn = "createAt";
        }

        // 검색 X
        else {

            notices = noticeService.findBySearchMatcher(page - 1, orderColumn, searchMatcher);

        }

        int totalPage = notices.getTotalPages();

        if(page > notices.getTotalPages() && totalPage != 0) {

            return rq.unexpectedRequestForWardUri("존재하지 않는 페이지입니다.");

        }



        model.addAttribute("notices", notices);

        List<Integer> pageRange = Pager.getPageRange(page, totalPage);
        model.addAttribute("pageRange", pageRange);

        model.addAttribute("currentPage", page);

        return "/notice/noticeList";
    }


    // 게시글 디테일 조회
    @GetMapping("/notice/{noticeId}")
    public String noticeDetail(@PathVariable Integer noticeId, Model model){

        // 중복 조회수 카운팅 방지
        Cookie oldCookie = rq.getCookie("viewedNotices");
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + noticeId.toString() + "]")) {
                noticeService.increaseHit(noticeId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + noticeId + "]");
                oldCookie.setPath("/notice");
                oldCookie.setMaxAge(60 * 60 * 24);
                rq.getResponse().addCookie(oldCookie);
            }
        } else {
            noticeService.increaseHit(noticeId);
            Cookie newCookie = new Cookie("viewedNotices","[" + noticeId + "]");
            newCookie.setPath("/notice");
            newCookie.setMaxAge(60 * 60 * 24);
            rq.getResponse().addCookie(newCookie);
        }


        NoticeDto notice = noticeService.findById(noticeId);
        model.addAttribute("notice", notice);

        return "/notice/noticeDetail";
    }

    // 게시글 작성/수정 페이지 폼
    @GetMapping("/notice/write")
    public String noticeWriteForm(Model model, @RequestParam(required = false) Integer id) throws AccessDeniedException {

        // 새로운 글 작성
        NoticeDto noticeDto;
        if(id == null) {

            noticeDto = new NoticeDto();

            // 기존 글 수정
        } else {
            if(rq.isAdmin()){
                noticeDto = noticeService.findById(id);
            } else {
                throw new AccessDeniedException("해당 게시물에 대한 수정권한이 존재하지 않습니다.");
            }
        }
        model.addAttribute("notice", noticeDto);

        return "/notice/writeForm";
    }

    // 게시글 작성 프로세스
    @PostMapping("/notice/write")
    public String noticeWritePrcs(NoticeDto noticeDto, @RequestParam(required = false) Integer id){

        noticeService.save(noticeDto);

        return "redirect:/notice?page=1";
    }

    // 게시글 삭제 프로세스
    @GetMapping("/notice/delete")
    public String noticeDelete(@RequestParam Integer id) throws AccessDeniedException {

        noticeService.delete(id);

        return "redirect:/notice?page=1";
    }
}














