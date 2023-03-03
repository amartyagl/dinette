package com.menuservice.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.menuservice.dto.TimeTableDto;
import com.menuservice.exception.FoodNameAlreadyExistException;
import com.menuservice.exception.ResourceNotFoundException;
import com.menuservice.model.TimeTable;
import com.menuservice.repository.TimeTableRepository;
import com.menuservice.service.TimeTableService;
import com.mongodb.MongoException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RefreshScope
public class TimeTableServiceImpl implements TimeTableService {

	@Autowired
	TimeTableRepository timeTableRepository;

	@Autowired
	TimeTable timeTable;


	/*
	 * This Method is to schedule the food items for a week
	 */
	@Override
	public TimeTableDto addTimeTable(TimeTableDto timeTableDto) {
		log.info("Request {}", timeTableDto);
		List<TimeTable> allTimeTable = timeTableRepository.findAll();
		Iterator<TimeTable> iterator = allTimeTable.iterator();
		while (iterator.hasNext()) {
			TimeTable iteratingMenu = iterator.next();
			if (iteratingMenu.getEmailId().equalsIgnoreCase(timeTableDto.getEmailId()))
				throw new FoodNameAlreadyExistException("SERVICE.TIME_TABLE_ALREADY_EXIST");
		}
		timeTable.setEmailId(timeTableDto.getEmailId().toLowerCase());
		timeTable.setFirstName(timeTableDto.getFirstName());
		timeTable.setLastName(timeTableDto.getLastName());
		timeTable.setMenuItems(timeTableDto.getMenuItems());
		try {
			TimeTable savedTimeTable = timeTableRepository.save(timeTable);
			timeTableDto.setTimetableId(savedTimeTable.getTimetableId());
			log.info("Response {}", timeTableDto);
		} catch (Exception e) {
			log.error("Error while adding Table {}", e.getMessage());
		}
		return timeTableDto;
	}

	/*
	 * This Method is to get all the food items for a week added by the customer
	 */
	@Override
	public List<TimeTableDto> getAllTimeTable() {
		log.info("Request for get all menu items {}");
		List<TimeTable> findAll = timeTableRepository.findAll();
		List<TimeTableDto> allTimeTable = new ArrayList<>();
		TimeTableDto tableDto = new TimeTableDto();
		for (TimeTable timeTable : findAll) {
			tableDto.setTimetableId(timeTable.getTimetableId());
			tableDto.setEmailId(timeTable.getEmailId());
			tableDto.setFirstName(timeTable.getFirstName());
			tableDto.setLastName(timeTable.getLastName());
			tableDto.setMenuItems(timeTable.getMenuItems());
			try {
				allTimeTable.add(tableDto);
			} catch (Exception e) {
				log.error("Error while getting Table {}", e.getMessage());
			}
		}
		log.info("Response {}", allTimeTable);
		return allTimeTable;
	}

	/*
	 * This Method is to get the food items added for a week by using emailId.
	 */
	@Override
	public TimeTableDto getTimeTableByEmail(String emailId) {
		log.info("Request for Get Time Table  By Email {}", emailId);
		Optional<TimeTable> findByEmail = timeTableRepository.findByEmailId(emailId.toLowerCase());
		TimeTableDto tableDto = new TimeTableDto();
		if (findByEmail.isPresent()) {
			TimeTable timeTableByEmail = findByEmail.get();
			tableDto.setTimetableId(timeTableByEmail.getTimetableId());
			tableDto.setEmailId(timeTableByEmail.getEmailId());
			tableDto.setFirstName(timeTableByEmail.getFirstName());
			tableDto.setLastName(timeTableByEmail.getLastName());
			tableDto.setMenuItems(timeTableByEmail.getMenuItems());
			log.info("Response {}", tableDto);
			return tableDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.TIME_TABLE_NOT_FOUND_BY_EMAIl");
		}
	}

	/*
	 * This Method is to get the food items added for a week by using Time Table ID.
	 */
	@Override
	public TimeTableDto getTimeTableById(String timetableId) {
		log.info("Request for Get Time Table  By ID {}", timetableId);
		Optional<TimeTable> findById = timeTableRepository.findById(timetableId);
		TimeTableDto tableDto = new TimeTableDto();
		if (findById.isPresent()) {
			TimeTable timeTableById = findById.get();
			tableDto.setTimetableId(timeTableById.getTimetableId());
			tableDto.setEmailId(timeTableById.getEmailId());
			tableDto.setFirstName(timeTableById.getFirstName());
			tableDto.setLastName(timeTableById.getLastName());
			tableDto.setMenuItems(timeTableById.getMenuItems());
			log.info("Response {}", tableDto);
			return tableDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.TIME_TABLE_NOT_FOUND_BY_ID");
		}
	}

	/*
	 * This Method is to update the food items added for a week by using Time Table
	 * ID.
	 */
	@Override
	public TimeTableDto updateTimeTable(String timetableId, TimeTableDto timeTableDto) {
		log.info("Request for update menu item {}", timetableId);
		Optional<TimeTable> findById = timeTableRepository.findById(timetableId);
		if (findById.isPresent()) {
			TimeTable updateTimeTable = findById.get();
			timeTableDto.setTimetableId(updateTimeTable.getTimetableId());
			updateTimeTable.setEmailId(timeTableDto.getEmailId());
			updateTimeTable.setFirstName(timeTableDto.getFirstName());
			updateTimeTable.setLastName(timeTableDto.getLastName());
			updateTimeTable.setMenuItems(timeTableDto.getMenuItems());
			try {
				timeTableRepository.save(updateTimeTable);
				log.info("Response {}", timeTableDto);
			} catch (MongoException e) {
				log.error("Error while Updating table {}",e.getMessage());
			}
			return timeTableDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.TIME_TABLE_NOT_FOUND_TO_UPDATE");
		}
	}

	/*
	 * This Method is to update the food items added for a week by using Email ID.
	 */
	@Override
	public TimeTableDto updateTimeTableByEmail(String emailId, TimeTableDto timeTableDto) {
		log.info("Request for update menu item {}", emailId);
		Optional<TimeTable> findById = timeTableRepository.findByEmailId(emailId.toLowerCase());
		if (findById.isPresent()) {
			TimeTable updateTimeTable = findById.get();
			timeTableDto.setTimetableId(updateTimeTable.getTimetableId());
			updateTimeTable.setEmailId(timeTableDto.getEmailId().toLowerCase());
			updateTimeTable.setFirstName(timeTableDto.getFirstName());
			updateTimeTable.setLastName(timeTableDto.getLastName());
			updateTimeTable.setMenuItems(timeTableDto.getMenuItems());
			try {
				timeTableRepository.save(updateTimeTable);
				log.info("Response {}", timeTableDto);
			} catch (MongoException e) {
				log.error("Error while Updating table {}",e.getMessage());
			}
			return timeTableDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.TIME_TABLE_NOT_FOUND_TO_UPDATE");
		}
	}

	/*
	 * This Method is to update the food items added for a week by using Time Table
	 * ID.
	 */
	@Override
	public void deleteTimeTableById(String timetableId) {
		log.info("Request for Delete Time Table {}", timetableId);
		if (timeTableRepository.existsById(timetableId)) {
			try {
				timeTableRepository.deleteById(timetableId);
				log.info("Time Table deleted");
			} catch (MongoException e) {
				log.error("Error while deleting table {}",e.getMessage());
			}
			
		} else {
			throw new ResourceNotFoundException("SERVICE.TIME_TABLE_NOT_FOUND");
		}
	}

}
