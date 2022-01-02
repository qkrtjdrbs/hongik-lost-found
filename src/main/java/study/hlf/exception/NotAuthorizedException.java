package study.hlf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_GATEWAY, reason = "권한 없음")
public class NotAuthorizedException extends RuntimeException{
}
