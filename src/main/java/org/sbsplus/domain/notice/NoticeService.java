package org.sbsplus.domain.notice;

import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.DataNotFoundException;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.repository.QuestionRepository;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final Rq rq;

    public Page<Notice> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.noticeRepository.findAll(pageable);
    }
    public Notice getNotice(Long id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if(notice.isPresent()) {
            return notice.get();
        } else {
            throw new DataNotFoundException("notice not found");
        }
    }

    public void create(String subject, String content, String category) {
       Category c = Category.convertStringToEnum(category);
        Notice n = new Notice();
        n.setSubject(subject);
        n.setContent(content);
        n.setCategory(c);
        n.setUser(rq.getUser());
        this.noticeRepository.save(n);
    }

    public void modify(Notice notice, String subject, String content, Category category) {
        notice.setSubject(subject);
        notice.setContent(content);
        notice.setCategory(category);
        this.noticeRepository.save(notice);
    }

    public void delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }
}