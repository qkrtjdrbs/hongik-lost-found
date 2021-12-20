package study.hlf.dto;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SubmitDto {

    @NotBlank
    private String title;

    @Lob
    @Size(min = 1, max = 500)
    private String content;
}
