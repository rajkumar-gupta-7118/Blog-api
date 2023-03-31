package com.rk.blog.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentDto {

	private int commentId;

	private String content;

	private UserDto user;
}
