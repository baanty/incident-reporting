package com.ing.reporting.persistence.dao;


import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.reporting.common.to.AssetTo;
import com.ing.reporting.persistence.entity.EventEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface EventDao extends  GenericDao<EventEntity, Integer>{
	
	/**
	 * Call this method to see if there is any data loaded for the current day.
	 * @return : returns the number of data for the day.
	 */
	@Query("select count(*) from EventEntity e where to_char(e.currentTimestamp,'YYYY MM DD') = to_char(CURRENT_DATE,'YYYY MM DD') ")
	int findDataForCurrentDate();
	
	/**
	 * USe this method to get all the assets in a single day.
	 * @return
	 */
	@Query("select new com.ing.reporting.common.to.AssetTo("
			+ "		1, "
			+ "		e.assetName, count(*), "
			+ "		( ( sum (   ( extract(second from (e.endTIme)) + ( extract(minute from (e.endTIme)) * 60 ) + ( extract(hour from (e.endTIme)) * 3600 ) ) "
			+ "			    - ( extract(second from (e.starTime)) + ( extract(minute from (e.starTime)) * 60 ) + ( extract(hour from (e.starTime)) * 3600 ) )"
			+ "			    ) * 100 ) / ( 24 * 3600 ) ) , "
			+ "		( 100 - ( ( sum (   ( extract(second from (e.endTIme)) + ( extract(minute from (e.endTIme)) * 60 ) + ( extract(hour from (e.endTIme)) * 3600 ) ) " 
			+ "			    - ( extract(second from (e.starTime)) + ( extract(minute from (e.starTime)) * 60 ) + ( extract(hour from (e.starTime)) * 3600 ) ) "
			+ "			    ) * 100 ) / ( 24 * 3600 ) ) ) , "
			+ "		(" 
			+ "		   SUM(CASE  "
			+ "			    WHEN e.severity = 1 THEN 30 "
			+ "			    ELSE 20 "
			+ "			END) "
			+ "		),"
			+ " CURRENT_TIMESTAMP "
			+ ") from EventEntity e "
			+ "		where to_char(e.currentTimestamp,'YYYY MM DD') = to_char(CURRENT_DATE,'YYYY MM DD')"
			+ "		group by e.assetName")
	List<AssetTo> getAssetsForDay() ;

}
