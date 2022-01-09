package study.hlf.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.hlf.entity.Comment;
import study.hlf.entity.QBoard;

import java.util.ArrayList;
import java.util.List;

import static study.hlf.entity.QBoard.*;
import static study.hlf.entity.QComment.*;
import static study.hlf.entity.QUser.*;

@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findComments(Long postId, Pageable pageable) {
        List<Comment> content = queryFactory
                .selectFrom(comment)
                .innerJoin(comment.board, board)
                .fetchJoin()
                .innerJoin(comment.user, user)
                .fetchJoin()
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.board.id.eq(postId))
                .orderBy(comment.groupId.asc(), comment.createdDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Comment> countQuery = queryFactory
                .selectFrom(comment)
                .where(comment.board.id.eq(postId));

        log.info("요청 페이지 : {}", pageable.getPageNumber());

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }
}
