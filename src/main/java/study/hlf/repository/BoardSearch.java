package study.hlf.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.hlf.entity.BoardStatus;

@Data
@AllArgsConstructor
public class BoardSearch {
    private String username;
    private String title;
    private String content;
    private BoardStatus status;
}
