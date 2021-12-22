package study.hlf.repository;

import lombok.Data;
import study.hlf.entity.BoardStatus;

@Data
public class BoardSearch {
    private String username;
    private String title;
    private String content;
    private BoardStatus status;
}
