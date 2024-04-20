package com.sds.smoking.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sds.smoking.domain.SmokingArea;

@Repository
public class MybatisSmokingAreaDAO implements SmokingAreaDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("SmokingArea.selectAll");
	}

	@Override
	public SmokingArea select(int smokingarea_idx) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("SmokingArea.select", smokingarea_idx);
	}

	@Override
	public void insert(SmokingArea smokingArea) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("SmokingArea.insert", smokingArea);
	}
	
}
