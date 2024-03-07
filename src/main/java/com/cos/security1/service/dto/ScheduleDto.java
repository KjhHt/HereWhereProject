package com.cos.security1.service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleDto {
	
	private Long schedule_id;
	private String title;
	private Date start_date;
	private Date end_date;
	private String id;

}
