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
@Table(name = "ERROR_EVENT")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ErrorEventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "error_event_generator")
	@SequenceGenerator(name="error_event_generator", sequenceName = "error_event_seq", initialValue = 8 )
	private int id;

	@Column(name = "ERRONEOUS_RECORD")
	private String erroneousRecord;

	@Column(name = "CRR_STMP")
	private Timestamp currentTimestamp;
	
}
