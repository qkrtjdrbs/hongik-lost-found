package study.hlf.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.hlf.entity.Comment;
import study.hlf.entity.QBoard;
import study.hlf.entity.QComment;
import study.hlf.entity.QUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static study.hlf.entity.QBoard.*;
import static study.hlf.entity.QComment.*;
import static study.hlf.entity.QUser.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findComments(Long postId, Pageable pageable) {
        List<Comment> content = queryFactory
                .selectFrom(comment)
                .innerJoin(comment.user, user)
                .fetchJoin()
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.board.id.eq(postId))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.createdDate.asc())
                .fetch();

        JPAQuery<Comment> countQuery = queryFactory
                .selectFrom(comment)
                .innerJoin(comment.board, board)
                .where(comment.board.id.eq(postId));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    /*private List<Comment> convertNestedStructure(List<Comment> comments) {
        List<Comment> result = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            result.add(comments.get(i));
            for (int j = i+1; j < comments.size(); j++) {
                if(comments.get(j).getParent() == null) continue;
                if(comments.get(j).getParent().getId() == comments.get(i).getId()){
                    result.add(comments.get(j));
                }
            }
        }
        return result;
    }*/
}
