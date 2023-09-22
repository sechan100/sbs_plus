package org.sbsplus.domain.notice;

import org.sbsplus.domain.qna.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Notice findBySubject(String subject);
    Notice findBySubjectAndContent(String subject, String content);
    List<Notice> findBySubjectLike(String subject);

    @Override
    Page<Notice> findAll(Pageable pageable);
}