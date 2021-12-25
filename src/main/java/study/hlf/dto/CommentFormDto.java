package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CommentFormDto {

    private Long user_id;
    private Long board_id;

    @Lob
    @NotBlank
    private String content;
}
