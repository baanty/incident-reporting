package com.ing.reporting.common.to;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AssetTo {
	
	private int id;

	private String assetName;

	private long totalIncidents;

	private long totalUpTime;
	
	private long totalDownTime;
	
	private long rating;
	
	private Date currentTimestamp;
	
}