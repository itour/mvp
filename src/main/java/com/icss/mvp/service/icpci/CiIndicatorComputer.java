package com.icss.mvp.service.icpci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ciComputer")
public class CiIndicatorComputer
{
	@Autowired
	private CiUADPGardingCollector ciUADPGardingCollector;
	@Autowired
	private CiSourceMonitorCollector ciSourceMonitorCollector;
	@Autowired
	private CiCodeDexCollector ciCodeDexCollector;
	@Autowired
	private CiCheckWarningCollector ciCheckWarning;
	@Autowired
	private CiCodeDuplicationCollector  ciCodeDuplication;
	@Autowired
	private CiLltCoverageCollector ciLltCoverageCollector;
	@Autowired
	private CiStructparameter ciStructparameter;
	@Autowired
	private CiProductRateCollector ciProductRate;
	
	
	public void collectIndicators()
	{
		ciCodeDexCollector.compute();
		ciUADPGardingCollector.compute();
		ciSourceMonitorCollector.compute();
		ciLltCoverageCollector.compute();
		ciStructparameter.compute();
		ciCheckWarning.computeBugs();
		ciCodeDuplication.computeCodeDuplication();
		ciProductRate.computeProductRate();
		
	}
}
