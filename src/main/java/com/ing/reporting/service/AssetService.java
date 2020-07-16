package com.ing.reporting.service;

import java.util.List;

import com.ing.reporting.common.to.AssetTo;

/**
 * Use this interface to get data from the DB 
 * about the asset data.
 * @author Pijush Kanti Das
 *
 */
public interface AssetService {

	List<AssetTo> findAssetStatisticsForThePresentDay();


}