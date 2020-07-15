package com.ing.reporting.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.reporting.dao.AssetDao;
import com.ing.reporting.to.AssetTo;
import com.ing.reporting.util.MappingUtil;

/**
 * This class is the primary implementation of the AssetService 
 * 
 * @author Pijush Kanti Das.
 *
 */
@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	AssetDao assetDao;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public List<AssetTo> findAssetStatisticsForThePresentDay() {
		Timestamp beginingOffset = Timestamp.valueOf(LocalDateTime.now());
		beginingOffset.setHours(0);
		beginingOffset.setMinutes(0);
		beginingOffset.setSeconds(0);
		
		Timestamp endingOffset = Timestamp.valueOf(LocalDateTime.now());
		endingOffset.setHours(0);
		endingOffset.setMinutes(0);
		endingOffset.setSeconds(0);
		return assetDao.findAssetEntitiesBetweenTimeRange(beginingOffset, endingOffset)
				.stream()
				.map(MappingUtil::buildBusinessObjectToTransferObject)
				.collect(Collectors.toList());
	}

}
