package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.hlf.entity.BaseTimeEntity;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends BaseTimeEntity {

    private String username;

    @Lob
    private String content;

    private LocalDateTime createdDate;

}
