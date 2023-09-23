package org.sbsplus.domain.notice.repository;

import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.notice.entity.Notice;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Page<Notice> findByIdContaining(Pageable pageRequest, Integer Id);

    Page<Notice> findByContentContaining(Pageable pageRequest, String searchMatcher);

    Page<Notice> findByTitleContaining(Pageable pageRequest, String searchMatcher);

    List<Notice> findByUser(User user);

}