package study.hlf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 이메일 Async 전송 위한 설정
 */

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 이 메소드를 오버라이딩 하지않으면 비동기 요청 시 매번 쓰레드를 생성한다
     * 쓰레드풀에 미리 일정 쓰레드를 미리 만들어놓고 요청 시 바로 할당한다
     */
    @Override
    public Executor getAsyncExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(processors);
        executor.setMaxPoolSize(processors * 2);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }

}
