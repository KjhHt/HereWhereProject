package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Alias("ReservationDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class ReservationDto {
	private String reservation_no;
	private String reservation_lat;
	private String reservation_lng;
	private String reservation_pricename;
	private String reservation_price;
	private String reservation_purchastime;
	private String reservation_receipturl;
	private String id;
}

