package org.sbsplus.domain.notice.service;

import org.sbsplus.domain.notice.dto.NoticeDto;
import org.sbsplus.domain.notice.entity.Notice;
import org.sbsplus.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface NoticeService {

    Page<NoticeDto> findBySearchMatcher(int page, String orderColumn, String searchMatcher);

    NoticeDto findById(Integer noticeId);

    void increaseHit(Integer noticeId);

    void save(NoticeDto noticeDto);

    void delete(Integer noticeId) throws AccessDeniedException;

    List<Notice> findByUser(User user);
}
