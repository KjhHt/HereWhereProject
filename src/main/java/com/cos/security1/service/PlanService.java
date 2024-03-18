package com.cos.security1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.security1.service.dto.PlanDto;
import com.cos.security1.service.dto.ScheduleDto;

@Service
public class PlanService {

	@Autowired
	private MybatisMapper mapper;
	
	public void addSchedule(ScheduleDto scheduleDto) {
		mapper.insertSchedule(scheduleDto);
	}
	
	public void addPlan(PlanDto planDto) {
		mapper.insertPlan(planDto);
	}
	
	public List<ScheduleDto> findSchedules(String id){
		return mapper.getSchedules(id);
	}
	
	public List<PlanDto> findPlans(Long schedule_no){
		return mapper.getPlans(schedule_no);
	}

	public ScheduleDto findScheduleByNo(Long schedule_no) {
		return mapper.getScheduleByNo(schedule_no);
	}
	
	public void updateSchedule(ScheduleDto dto) {
		mapper.updateSchedule(dto);
	}
	
	public void deleteSchedule(Long schedule_no) {
		mapper.deleteScheduleByNo(schedule_no);
	}
	
	public void deletePlan(Long plan_no) {
		mapper.deletePlanByNo(plan_no);
	}
	
}
