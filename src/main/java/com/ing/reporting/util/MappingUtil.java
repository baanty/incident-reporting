package com.ing.reporting.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ing.reporting.entity.AssetEntity;
import com.ing.reporting.entity.EventEntity;
import com.ing.reporting.to.AssetTo;
import com.ing.reporting.to.EventTo;

/**
 * This utility class will do the object to object data 
 * mapping. This class has been designed as a singleton.
 * So, only utility methods. No instance variables.
 * 
 * @author Pijush Kanti Das.
 *
 */
public final class MappingUtil {
	
	/**
	 * The private constructor.
	 */
	private MappingUtil () {
		
	}
	
	/**
	 * Use this method to build Transfer Objects from business object.
	 * @param eventEntity : The BUsiness Object.
	 * @return : The BUilt Transfer Object
	 */
	public static final EventTo buildBusinessObjectToTransferObject(EventEntity eventEntity) {
		return EventTo.builder()
				.assetName(eventEntity.getAssetName())
				.endTIme(eventEntity.getEndTIme())
				.starTime(eventEntity.getStarTime())
				.severity(eventEntity.getSeverity())
				.build();
	}
	
	
	/**
	 * Use this method to build business Objects from Transfer  object.
	 * @param eventEntity : The Transfer Object.
	 * @return : The BUilt business Object
	 */
	public static final EventEntity buildTransferObjectToBusinessObject(EventTo eventTo) {
		return EventEntity.builder()
				.assetName(eventTo.getAssetName())
				.endTIme(eventTo.getEndTIme())
				.starTime(eventTo.getStarTime())
				.severity(eventTo.getSeverity())
				.build();
	}
	
	
	
	/**
	 * Use this method to build Transfer Objects from business object.
	 * @param AssetEntity : The BUsiness Object.
	 * @return : The BUilt Transfer Object
	 */
	public static final AssetTo buildBusinessObjectToTransferObject(AssetEntity assetEntity) {
		return AssetTo.builder()
				.assetName(assetEntity.getAssetName())
				.totalDownTime(assetEntity.getTotalDownTime())
				.totalUpTime(assetEntity.getTotalUpTime())
				.rating(assetEntity.getRating())
				.totalIncidents(assetEntity.getTotalIncidents())
				.build();
	}
	
	
	/**
	 * Use this method to build business Objects from Transfer  object.
	 * @param AssetTo : The Transfer Object.
	 * @return : The BUilt business Object
	 */
	public static final AssetEntity buildTransferObjectToBusinessObject(AssetTo eventTo) {
		return AssetEntity.builder()
				.assetName(eventTo.getAssetName())
				.totalDownTime(eventTo.getTotalDownTime())
				.totalUpTime(eventTo.getTotalUpTime())
				.rating(eventTo.getRating())
				.totalIncidents(eventTo.getTotalIncidents())
				.currentTimestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
}
