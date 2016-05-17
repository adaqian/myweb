package com.example.myweb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	Map getUserInfo(Map paraMap);
	
	List<Map> getStudentList(Map paraMap);
}
