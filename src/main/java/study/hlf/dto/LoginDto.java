package study.hlf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginDto {

    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @NotBlank
    @Size(min = 4, max = 12)
    private String password;

}
