package study.hlf.dto;

import lombok.Data;

@Data
public class CommentDeleteDto {

    private Long commentId;
    private Long userId;
    private Long postId;
}
