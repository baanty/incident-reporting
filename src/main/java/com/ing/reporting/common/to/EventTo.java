package com.ing.reporting.common.to;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventTo {

	private String assetName;

	private Timestamp starTime;

	private Timestamp endTIme;
	
	private int severity;
	
}
