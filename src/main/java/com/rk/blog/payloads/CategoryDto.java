package com.rk.blog.payloads;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

	private int catId;

	@NotBlank
	private String catName;

	private String catDescription;
}
