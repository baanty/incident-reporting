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
public class AssetTo {

	private String assetName;

	private int totalIncidents;

	private long totalUpTime;
	
	private long totalDownTime;
	
	private int rating;
	
}
