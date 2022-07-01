package com.thanthu.orgservice.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponseDto {
	
	private int code;
	
	private List<String> messages;

}
