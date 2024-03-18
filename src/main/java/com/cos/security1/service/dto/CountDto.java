package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("CountDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class CountDto {
	private String count;
}
