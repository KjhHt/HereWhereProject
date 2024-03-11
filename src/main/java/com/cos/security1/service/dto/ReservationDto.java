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
	
	private String flight_no; // 고유번호
	private String flight_depcode; // 출발공항코드
	private String flight_arrivalairportcode; // 도착공항코드
	private String flight_departuredate; // 출발일자
	private String flight_productname; // 상품이름
	private String flight_duration; // 운행시간
	private String flight_seattype; // 좌석종류
	private String flight_seatnumber; // 좌석번호
	private String flight_price; // 가격
	private String flight_paymentdate; // 결제일자
	private String flight_receipt; // 영수증
	
	private String id;
}

