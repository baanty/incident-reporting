package com.ing.reporting.service;

import java.util.List;

import com.ing.reporting.to.AssetTo;

public interface AssetService {

	List<AssetTo> findAssetStatisticsForTheDay();


}