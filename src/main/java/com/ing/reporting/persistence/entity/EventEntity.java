package com.ing.reporting.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@Entity
@Table(name = "EVENT")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
	@SequenceGenerator(name="event_generator", sequenceName = "event_seq", initialValue = 8 )
	private int id;

	@Column(name = "ASSET_NAME")
	private String assetName;

	@Column(name = "START_TIME")
	private Timestamp starTime;

	@Column(name = "END_TIME")
	private Timestamp endTIme;
	
	@Column(name = "SEVERITY")
	private int severity;
	
}
