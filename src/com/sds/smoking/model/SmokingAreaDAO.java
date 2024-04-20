package com.sds.smoking.model;

import java.util.List;

import com.sds.smoking.domain.SmokingArea;

public interface SmokingAreaDAO {
	public List selectAll();
	public SmokingArea select(int smokingarea_idx);
	public void insert(SmokingArea smokingArea);
}
