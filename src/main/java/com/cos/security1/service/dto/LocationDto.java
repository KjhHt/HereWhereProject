package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("LocationDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class LocationDto {
	private String location_no;
	private String location_name;
	private String location_address;
	private String location_imageurl;
	private String location_rating;
	private String location_placeid;
	private String board_no;
	
	//컨트롤러에서 받을 시 데이터를 추가했는지 안했는지 여부 DB에 저장은 안할거임.
	private String isLocation;

	
	
}
