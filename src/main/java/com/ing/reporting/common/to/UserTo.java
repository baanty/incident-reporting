package com.ing.reporting.common.to;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserTo {
	
	private int id;

	private String userId;

	private String firstName;
	
	private String lastName;
	
}