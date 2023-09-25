package org.sbsplus.general.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.cummunity.dto.ArticleDto;
import org.sbsplus.domain.cummunity.service.ArticleService;
import org.sbsplus.domain.event.entity.Event;
import org.sbsplus.domain.event.service.EventService;
import org.sbsplus.domain.event.service.EventServiceImpl;
import org.sbsplus.domain.notice.dto.NoticeDto;
import org.sbsplus.domain.notice.entity.Notice;
import org.sbsplus.domain.notice.service.NoticeService;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.service.QuestionService;
import org.sbsplus.util.DataCreator;
import org.sbsplus.util.Rq;
import org.sbsplus.util.Ut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class GeneralController {

    private final Rq rq;
    private final DataCreator dataCreator;
    private final NoticeService noticeService;
    private final ArticleService articleService;
    private final EventService eventService;
    private final QuestionService questionService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoValue;

    // ************ TEST DATA CREATE ***************
    private boolean isTestDataCreated = false;
    // *********************************************


    @GetMapping("/")
    public String home(Model model) {


        // ************ TEST DATA CREATE ***************
        if (!isTestDataCreated && ddlAutoValue.equals("create")) {
            dataCreator.createTestData();
            isTestDataCreated = true;
        }// *********************************************

        List<Notice> notices = noticeService.getAllNotices();
        List<ArticleDto> top10Articles = articleService.findTop10();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        List<Event> events = eventService.getCalendarEvents(year, month);

        // 아래의 변수들을 모델에 추가합니다.
        Map<Integer, List<Event>> forCalendarEvents = events.stream()
                .collect(Collectors.groupingBy(event -> event.getEventDate().getDayOfMonth()));

        DayOfWeek firstDayOfMonth = today.withDayOfMonth(1).getDayOfWeek();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        LocalDate prevMonth = startDate.minusMonths(1);
        LocalDate nextMonth = startDate.plusMonths(1);


        // EventServiceImpl을 사용하여 해당 월의 달력 데이터를 가져옵니다.
//        List<Question> questionList = questionService.getAllQuestions();

        // 모델에 QuestionList 추가
//        model.addAttribute("questionList", questionList);
        // 모델에 데이터를 추가합니다.
        // EventServiceImpl을 사용하여 해당 월의 달력 데이터를 가져옵니다.
        List<Question> top10Questions = questionService.findTop10();

        // 모델에 QuestionList 추가
        model.addAttribute("top10Questions", top10Questions);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("events", events);
        model.addAttribute("forCalendarEvents", forCalendarEvents);
        model.addAttribute("firstDayOfMonth", firstDayOfMonth.getValue() + 1);
        model.addAttribute("daysInMonth", daysInMonth);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", String.format("%02d", prevMonth.getMonthValue()));
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", String.format("%02d", nextMonth.getMonthValue()));
        model.addAttribute("notices", notices);
        model.addAttribute("top10Articles", top10Articles);

        return "/home/main";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model, HttpServletRequest request, @RequestParam(required = false) String requiredAuthority) {

        // "/admin/**"에 접근을 시도한 경우
        if (requiredAuthority != null && requiredAuthority.equals("admin")) {
            model.addAttribute("msg", "관리자 권한이 없습니다.");
        } else {
            String msg = (String) request.getAttribute("msg");
            model.addAttribute("msg", msg);
        }

        return "/exception/accessDenied";
    }

    @GetMapping("/404")
    public String pageNotFound(Model model, HttpServletRequest request) {

        String msg = (String) request.getAttribute("msg");

        model.addAttribute("msg", msg);

        return "/exception/404";
    }


}