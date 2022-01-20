package study.hlf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 이메일 Async 전송 위한 설정
 */

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
}
