package com.ing.reporting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "ASSET")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AssetEntity implements GenericEntity {

	@Id
	@Column(name = "ASSET_NAME")
	private String assetName;

	@Column(name = "TOTAL_INCIDENT")
	private int totalIncidents;

	@Column(name = "TOTAL_UP_TIME")
	private long totalUpTime;
	
	@Column(name = "TOTAL_DOWN_TIME")
	private long totalDownTime;
	
	@Column(name = "RATING")
	private int rating;
	
}
