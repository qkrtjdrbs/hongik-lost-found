package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.hlf.entity.Coord;

import javax.persistence.Embedded;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitDto {

    @NotBlank
    private String title;

    @Lob
    @Size(min = 1, max = 3000)
    private String content;

}
