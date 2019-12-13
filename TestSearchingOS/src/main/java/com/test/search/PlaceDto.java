package com.test.search;

import org.springframework.stereotype.Repository;

@Repository
public class PlaceDto {

	private String place_id;
	
	private String place_name;
	
	private String place_address;
	
	private String tel_no;
	
	private String detail_information;
	
	private String open_hours;
	
	private String close_hours;
	
	private String buisness_day;
	
	private String shutdown_flag;
	
	private String category_detail;
	
	private String google_place_id;
	
	private String kakao_place_id;
	
	private String naver_place_id;
	
	private String latitude;
	
	private String longitude;
	
	public String getPlace_id() {
		return place_id;
	}
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getPlace_name() {
		return place_name;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public String getPlace_address() {
		return place_address;
	}
	public void setPlace_address(String place_address) {
		this.place_address = place_address;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getDetail_information() {
		return detail_information;
	}
	public void setDetail_information(String detail_information) {
		this.detail_information = detail_information;
	}
	public String getOpen_hours() {
		return open_hours;
	}
	public void setOpen_hours(String open_hours) {
		this.open_hours = open_hours;
	}
	public String getClose_hours() {
		return close_hours;
	}
	public void setClose_hours(String close_hours) {
		this.close_hours = close_hours;
	}
	public String getBuisness_day() {
		return buisness_day;
	}
	public void setBuisness_day(String buisness_day) {
		this.buisness_day = buisness_day;
	}
	public String getShutdown_flag() {
		return shutdown_flag;
	}
	public void setShutdown_flag(String shutdown_flag) {
		this.shutdown_flag = shutdown_flag;
	}
	public String getCategory_detail() {
		return category_detail;
	}
	public void setCategory_detail(String category_detail) {
		this.category_detail = category_detail;
	}
	public String getGoogle_place_id() {
		return google_place_id;
	}
	public void setGoogle_place_id(String google_place_id) {
		this.google_place_id = google_place_id;
	}
	public String getKakao_place_id() {
		return kakao_place_id;
	}
	public void setKakao_place_id(String kakao_place_id) {
		this.kakao_place_id = kakao_place_id;
	}
	public String getNaver_place_id() {
		return naver_place_id;
	}
	public void setNaver_place_id(String naver_place_id) {
		this.naver_place_id = naver_place_id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


}

