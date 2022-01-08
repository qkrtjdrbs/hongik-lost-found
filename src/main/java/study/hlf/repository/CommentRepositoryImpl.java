package study.hlf.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.hlf.entity.Comment;

import java.util.ArrayList;
import java.util.List;

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

        int total = content.size();
        content = convertNestedStructure(content);
        content = new ArrayList<>(content.subList(pageable.getPageNumber() / 10,
                Math.min(pageable.getPageNumber() / 10 + 10, total)));

        return new PageImpl<>(content, pageable, total);
    }

    private List<Comment> convertNestedStructure(List<Comment> comments) {
        List<Comment> result = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            if(comments.get(i).getParent() == null){
                result.add(comments.get(i));
            }
            for (int j = i+1; j < comments.size(); j++) {
                if(comments.get(j).getParent() == null){
                    continue;
                }
                if(comments.get(j).getParent().getId() == comments.get(i).getId()){
                    result.add(comments.get(j));
                }
            }
        }
        return result;
    }
}
