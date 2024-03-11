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
public class ScheduleDto {
	
	private Long schedule_no;
	private String schedule_title;
	private Date schedule_startdate;
	private Date schedule_enddate;
	private String id;

}
