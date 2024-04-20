package com.sds.smoking.domain;

import lombok.Data;

@Data
public class SmokingArea {

	private int smokingarea_idx;
	private String area_nm; 		// 흡연구역명
	private String area_desc;		// 흡연구역범위상세
	private String ctprvnnm;		// 시도명
	private String signgunm;		// 시군구명
	private String emdnm;			// 읍면동명
	private String area_se;			// 흡연구역구분
	private float area_ar;			// 흡연구역면적
	private String rdnmadr;			// 소재지 도로명주소
	private String lnmadr;			// 소재지 지번주소
	private String inst_nm;			// 관리기관명
	private float latitude;			// 위도
	private float longitude;		// 경도
	private String ref_date;		// 데이터기준일자
	private String fclty_knd;		// 시설이미지
	
	public int getSmokingarea_idx() {
		return smokingarea_idx;
	}
	public void setSmokingarea_idx(int smokingarea_idx) {
		this.smokingarea_idx = smokingarea_idx;
	}
	public String getArea_nm() {
		return area_nm;
	}
	public void setArea_nm(String area_nm) {
		this.area_nm = area_nm;
	}
	public String getArea_desc() {
		return area_desc;
	}
	public void setArea_desc(String area_desc) {
		this.area_desc = area_desc;
	}
	public String getCtprvnnm() {
		return ctprvnnm;
	}
	public void setCtprvnnm(String ctprvnnm) {
		this.ctprvnnm = ctprvnnm;
	}
	public String getSigngunm() {
		return signgunm;
	}
	public void setSigngunm(String signgunm) {
		this.signgunm = signgunm;
	}
	public String getEmdnm() {
		return emdnm;
	}
	public void setEmdnm(String emdnm) {
		this.emdnm = emdnm;
	}
	public String getArea_se() {
		return area_se;
	}
	public void setArea_se(String area_se) {
		this.area_se = area_se;
	}
	public float getArea_ar() {
		return area_ar;
	}
	public void setArea_ar(float area_ar) {
		this.area_ar = area_ar;
	}
	public String getRdnmadr() {
		return rdnmadr;
	}
	public void setRdnmadr(String rdnmadr) {
		this.rdnmadr = rdnmadr;
	}
	public String getLnmadr() {
		return lnmadr;
	}
	public void setLnmadr(String lnmadr) {
		this.lnmadr = lnmadr;
	}
	public String getInst_nm() {
		return inst_nm;
	}
	public void setInst_nm(String inst_nm) {
		this.inst_nm = inst_nm;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getRef_date() {
		return ref_date;
	}
	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}
	public String getFclty_knd() {
		return fclty_knd;
	}
	public void setFclty_knd(String fclty_knd) {
		this.fclty_knd = fclty_knd;
	}

}
