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

	private Long plan_no;
	private String plan_name;
	private String plan_img;
	private String plan_placeid;
	private Date plan_registrationdate;
	private Long schedule_no;
	
}
