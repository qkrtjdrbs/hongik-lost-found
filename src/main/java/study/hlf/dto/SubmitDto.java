package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.hlf.entity.BoardStatus;
import study.hlf.entity.Coord;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitDto {

    @Size(min = 1, max = 20)
    private String title;

    @Lob
    @Size(min = 1, max = 30000)
    private String content;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BoardStatus status;

    private Double longitude;
    private Double latitude;
}
