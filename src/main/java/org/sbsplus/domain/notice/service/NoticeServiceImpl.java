package org.sbsplus.domain.notice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.domain.admin.AdminService;
import org.sbsplus.domain.notice.dto.NoticeDto;
import org.sbsplus.domain.notice.entity.Notice;
import org.sbsplus.domain.notice.repository.NoticeRepository;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminService adminService;
    private final UserService userService;
    private final Rq rq;

    @Override
    public Page<NoticeDto> findBySearchMatcher(int page, String orderColumn, String searchMatcher) {

        Page<Notice> notices_ = null;
        long totalContent = 0;

        Pageable pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, orderColumn));

        notices_ = noticeRepository.findByContentContaining(pageRequest, searchMatcher);
        totalContent += notices_.getTotalElements();
        List<Notice> content1 = notices_.getContent();

        notices_ = noticeRepository.findByTitleContaining(pageRequest, searchMatcher);
        totalContent += notices_.getTotalElements();
        List<Notice> content2 = notices_.getContent();

        // Content 리스트 합침
        Set<Notice> filterSameNotice = new HashSet<>();
        filterSameNotice.addAll(content1);
        filterSameNotice.addAll(content2);

        List<Notice> combinedContent = new ArrayList<>(filterSameNotice);

        if (orderColumn.equals("hit")) {
            List<Notice> content_ = new ArrayList<>(combinedContent);
            content_.sort((notice1, notice2) -> Integer.compare(notice2.getHit(), notice1.getHit()));
            combinedContent = content_;
            pageRequest = PageRequest.of(page, 20);
        }

        // 합친 Content 리스트를 페이지로 변환
        notices_ = new PageImpl<>(combinedContent, pageRequest, totalContent);

        return notices_.map(notice -> (new ModelMapper()).map(notice, NoticeDto.class));
    }
    @Override
    public NoticeDto findById(Integer noticeId) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        return (new ModelMapper()).map(notice,NoticeDto.class);
    }

    @Override
    @Transactional
    public void increaseHit(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        notice.increaseHit();
    }

    @Override
    @Transactional
    public void save(NoticeDto noticeDto) {

        // 새로운 글 작성
        if(noticeDto.getId() == null){

            Notice notice = (new ModelMapper()).map(noticeDto, Notice.class);

            if(notice.getUser() == null){
                notice.setUser(rq.getUser());
            }

            noticeRepository.save(notice);

            // 기존 글 수정
        } else {
            if(rq.isAdmin()) {
                Notice notice = noticeRepository.findById(noticeDto.getId()).orElseThrow();
                notice.setTitle(noticeDto.getTitle());
                notice.setContent(noticeDto.getContent());
            }
        }

    }



    @Override
    public void delete(Integer noticeId) throws AccessDeniedException {

        // 수정은 소유자만 가능, 삭제는 admin도 가능.
        if(adminService.isAdmin()){
            noticeRepository.deleteById(noticeId);
        } else {
            throw new AccessDeniedException("게시물에 대한 접근 권한이 없습니다.");
        }
    }

    @Override
    public List<Notice> findByUser(User user) {

        return noticeRepository.findByUser(user);
    }

    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

}