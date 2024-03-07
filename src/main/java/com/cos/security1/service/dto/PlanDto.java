package com.cos.security1.service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {

	private String place_name;
	private String place_img;
	private Long schedule_id;
	private String place_id;
	private Date plan_date;
	
}
