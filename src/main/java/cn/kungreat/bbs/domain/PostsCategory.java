package cn.kungreat.bbs.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostsCategory {
    private Long id;

    private String categoryName;

    private Byte state;
}