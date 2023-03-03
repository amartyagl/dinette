package com.menuservice.serviceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.menuservice.dto.TimeTableDto;
import com.menuservice.model.TimeTable;
import com.menuservice.repository.TimeTableRepository;

@ExtendWith(MockitoExtension.class)
class TimeTableServiceImplTest {
	
	@Mock
	TimeTableRepository timeTableRepository;
	
	@Mock
	TimeTableDto tableDto;
	
	@Mock
	TimeTable timeTable;
	
	@InjectMocks
	TimeTableServiceImpl timeTableServiceImpl;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(timeTableServiceImpl);
	}

	@Test
	void test() {
	}

}
