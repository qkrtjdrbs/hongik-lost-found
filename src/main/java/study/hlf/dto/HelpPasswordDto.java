package study.hlf.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class HelpPasswordDto {

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;
}
