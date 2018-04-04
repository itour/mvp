package com.icss.mvp.service.dts;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service("computer")
public class DtsIndicatorComputer
{
	@Autowired
	private DtsNumCollector dtsNumCollector;
	
	@Autowired
	private DtsNotPassNumCollector dtsNotPassNumCollector;
	
	@Autowired
	private DtsResolutionRateCollector dtsResolutionRateCollector;
	
	@Autowired
	private IterationDefectDensityCollector iterationDefectDensityCollector;
	
	@Autowired
	private DtsLeaveDINumCollector dtsLeaveDINumCollector;
	
	@Autowired
	private Tr5DICollector tr5diCollector;
	
	@Autowired
	private Tr6DICollector tr6diCollector;
	
	@Autowired
	private DtsSITWithSVTDefectDensity dtsSITWithSVTDefectDensity;
	
	public void collectIndicators()
	{
		dtsNumCollector.compute();
		dtsNotPassNumCollector.compute();
		dtsLeaveDINumCollector.compute();
		dtsResolutionRateCollector.compute();
		tr5diCollector.compute();
		tr6diCollector.compute();
		//迭代测试发现缺陷密度
		iterationDefectDensityCollector.compute();
		dtsSITWithSVTDefectDensity.collectDensityNum();
	}
}
