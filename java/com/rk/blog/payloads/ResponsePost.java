package com.rk.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePost {

	private List<PostDto> content;

	private Integer pagenumber;

	private Long totalelements;

	private Integer totalpage;

	private boolean lastpage;

}
