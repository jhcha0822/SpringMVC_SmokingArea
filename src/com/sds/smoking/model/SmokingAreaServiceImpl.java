package com.sds.smoking.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.smoking.domain.SmokingArea;

@Service
public class SmokingAreaServiceImpl implements SmokingAreaService {

	@Autowired
	private SmokingAreaDAO smokingAreaDAO;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return smokingAreaDAO.selectAll();
	}

	@Override
	public SmokingArea select(int smokingarea_idx) {
		// TODO Auto-generated method stub
		return smokingAreaDAO.select(smokingarea_idx);
	}

	@Override
	public void insert(SmokingArea smokingArea) {
		// TODO Auto-generated method stub
		smokingAreaDAO.insert(smokingArea);
	}
	
}
