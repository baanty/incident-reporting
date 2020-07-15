package com.rabo.api.jsonbody;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

@ResponseBody
@Data
public class CustomErrorResponse {

	private LocalDateTime timestamp;
	private String error;
	private String status;
}
