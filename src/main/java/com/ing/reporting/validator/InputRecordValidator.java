package com.ing.reporting.validator;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Use this class to validate an incoming record 
 * @author Pijush Kanti Das
 *
 */
@Slf4j
@Component
public class InputRecordValidator {

	/**
	 * Use this method to validate if a CSV record is valid. Please 
	 * pass the single CSV record. from one row.
	 * 
	 * @param singleCsvRecord : The Single Csv Record.
	 * @return
	 */
	public boolean isValidRecord(List<String> singleCsvRecord) {
		
		if ( CollectionUtils.isEmpty(singleCsvRecord)) {
			return false;
		}
		
		if ( singleCsvRecord.size() < 4) {
			return false;
		}
		
		try {
			Timestamp.valueOf(singleCsvRecord.get(1));
			Timestamp.valueOf(singleCsvRecord.get(2));
			Integer.parseInt(singleCsvRecord.get(3));
		} catch (Exception exception) {
			log.error("The record " + singleCsvRecord + "is not valid");
			return false;
		}
		return true;
	}
}
