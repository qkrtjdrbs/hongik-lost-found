package study.hlf.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class HelpIdDto {

    @Email
    @NotNull
    private String email;

}
