package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentFormDto {

    private Long user_id;
    private Long board_id;

    @Lob
    @NotBlank
    private String content;
}
