package study.hlf.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.hlf.entity.Board;
import study.hlf.entity.BoardStatus;
import study.hlf.entity.QComment;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static study.hlf.entity.QBoard.*;
import static study.hlf.entity.QComment.*;
import static study.hlf.entity.QUser.*;

@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> findBoardDynamic(BoardSearch condition, Pageable pageable) {
        log.info("{}, {}, {}, {}",
                condition.getContent(),
                condition.getUsername(),
                condition.getStatus(),
                condition.getTitle());

        List<Board> content = queryFactory
                .selectFrom(board)
                .innerJoin(board.user, user)
                .fetchJoin()
                .leftJoin(board.comments, comment)
                .fetchJoin()
                .where(usernameEq(condition.getUsername()),
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        statusEq(condition.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetch();

        JPAQuery<Board> countQuery = queryFactory
                .selectFrom(board)
                .leftJoin(board.user, user)
                .where(
                        usernameEq(condition.getUsername()),
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        statusEq(condition.getStatus())
                );
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression statusEq(BoardStatus statusCond) {
        return statusCond == null ? null : board.status.eq(statusCond);
    }

    private BooleanExpression contentContains(String contentCond) {
        return !hasLength(contentCond) ? null : board.content.contains(contentCond);
    }

    private BooleanExpression titleContains(String titleCond) {
        return !hasLength(titleCond) ? null : board.title.contains(titleCond);
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return !hasLength(usernameCond) ? null : user.username.eq(usernameCond);
    }
}
