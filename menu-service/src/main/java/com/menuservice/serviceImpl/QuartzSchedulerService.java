package com.menuservice.serviceImpl;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.menuservice.exception.SchedulerFailedException;
import com.menuservice.exception.TableNotScheduledException;
import com.menuservice.job.RestJob;
import com.menuservice.model.Menu;
import com.menuservice.model.ScheduledInfo;
import com.menuservice.model.TimeTable;
import com.menuservice.model.TimeTable.Day;
import com.menuservice.repository.SchedulerRepository;
import com.menuservice.repository.TimeTableRepository;
import com.menuservice.schedule.SchduleRequest;
import com.menuservice.schedule.ScheduleResponse;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PreDestroy;

@Slf4j
@Service
@RefreshScope
public class QuartzSchedulerService {

	@Autowired
	private TimeTableRepository timeTableRepository;

	@Autowired
	private TimeTable timeTable;

	@Autowired
	private SchduleRequest schduleRequest;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private ScheduledInfo scheduledInfo;

	@Autowired
	private SchedulerRepository schedulerRepository;

	public QuartzSchedulerService(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public QuartzSchedulerService(Scheduler scheduler) {
		super();
		this.scheduler = scheduler;
	}

	public QuartzSchedulerService() {
		super();
	}

	/*
	 * This Method is to send the Respective Menu items to the particular day method
	 * When Scheduling is called
	 */
	public Map<Day, ScheduleResponse> scheduleOrder(String emailId) {
		log.info("Request {} ", emailId);
		Optional<TimeTable> findByEmailId = timeTableRepository.findByEmailId(emailId.toLowerCase());
		if (findByEmailId.isPresent()) {
			this.timeTable = findByEmailId.get();
			schduleRequest.setEmailId(timeTable.getEmailId());
			schduleRequest.setFirstName(timeTable.getFirstName());
			schduleRequest.setTimetableId(timeTable.getTimetableId());

			QuartzSchedulerService service = new QuartzSchedulerService();
			Map<Day, List<Menu>> menuItems = timeTable.getMenuItems();

			Map<Day, ScheduleResponse> response = new HashMap<>();

			List<Menu> mondayItems = menuItems.get(Day.MONDAY);
			ScheduleResponse scheduleOrderForMonday = service.scheduleOrderForMonday(mondayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.MONDAY, scheduleOrderForMonday);

			List<Menu> tuesdayItems = menuItems.get(Day.TUESDAY);
			ScheduleResponse scheduleOrderForTuesday = service.scheduleOrderForTuesday(tuesdayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.TUESDAY, scheduleOrderForTuesday);

			List<Menu> wednesdayItems = menuItems.get(Day.WEDNESDAY);
			ScheduleResponse scheduleOrderForWednesday = service.scheduleOrderForWednesday(wednesdayItems,
					schduleRequest, scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.WEDNESDAY, scheduleOrderForWednesday);

			List<Menu> thursdayItems = menuItems.get(Day.THURSDAY);
			ScheduleResponse scheduleOrderForThursday = service.scheduleOrderForThursday(thursdayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.THURSDAY, scheduleOrderForThursday);

			List<Menu> fridayItems = menuItems.get(Day.FRIDAY);
			ScheduleResponse scheduleOrderForFriday = service.scheduleOrderForFriday(fridayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.FRIDAY, scheduleOrderForFriday);

			List<Menu> saturdayItems = menuItems.get(Day.SATURDAY);
			ScheduleResponse scheduleOrderForSaturday = service.scheduleOrderForSaturday(saturdayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.SATURDAY, scheduleOrderForSaturday);

			List<Menu> sundayItems = menuItems.get(Day.SUNDAY);
			ScheduleResponse scheduleOrderForSunday = service.scheduleOrderForSunday(sundayItems, schduleRequest,
					scheduledInfo, schedulerRepository, rabbitTemplate);
			response.put(Day.SUNDAY, scheduleOrderForSunday);

			log.info("Response {} ", response);
			return response;

		} else {
			log.error("Timetable not yet scheduled to order");
			throw new TableNotScheduledException("Timetable not yet scheduled to order");
		}
	}

	/*
	 * This Method is to create JobDetail which allow us to add the upcoming jobs
	 */
	private JobDetail buildJobDetail(ScheduledInfo scheduledInfo) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("order", "food is going to be order now");
		return JobBuilder.newJob(RestJob.class).withIdentity(scheduledInfo.getJobKey(), scheduledInfo.getJobvalue())
				.withDescription("Send subscription order Job").usingJobData(jobDataMap).storeDurably().build();
	}

	/*
	 * This Method is to create Trigger which allow us to allocate the time to a job
	 */
	private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "rest-triggers")
				.withDescription("Send subscription order Trigger").startAt(Date.from(startAt.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24 * 7).withRepeatCount(4))
				.build();
	}

	/*
	 * This Method is responsible to schedule Order for Monday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForMonday(List<Menu> mondayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Monday items {}", mondayItems);
		if (mondayItems != null && !mondayItems.isEmpty()) {
			scheduledInfo.setDay("Monday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(mondayItems);
			schduleRequest.setMenuItems(mondayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", mondayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This Method is responsible to schedule Order for Tuesday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForTuesday(List<Menu> tuesdayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Tuesday items {}", tuesdayItems);
		if (tuesdayItems != null && !tuesdayItems.isEmpty()) {
			scheduledInfo.setDay("Tuesday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(tuesdayItems);
			schduleRequest.setMenuItems(tuesdayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", tuesdayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This Method is responsible to schedule Order for Wednesday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForWednesday(List<Menu> wednesdayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Wednesday items {}", wednesdayItems);
		if (wednesdayItems != null && !wednesdayItems.isEmpty()) {
			scheduledInfo.setDay("Wednesday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(wednesdayItems);
			schduleRequest.setMenuItems(wednesdayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", wednesdayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}

	}

	/*
	 * This Method is responsible to schedule Order for Thursday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForThursday(List<Menu> thursdayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Thursday items {}", thursdayItems);
		if (thursdayItems != null && !thursdayItems.isEmpty()) {
			scheduledInfo.setDay("Thursday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(thursdayItems);
			schduleRequest.setMenuItems(thursdayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", thursdayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This Method is responsible to schedule Order for Friday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForFriday(List<Menu> fridayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Friday items {}", fridayItems);
		if (fridayItems != null && !fridayItems.isEmpty()) {
			scheduledInfo.setDay("Friday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(fridayItems);
			schduleRequest.setMenuItems(fridayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", fridayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This Method is responsible to schedule Order for Saturday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForSaturday(List<Menu> saturdayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Saturday items {}", saturdayItems);
		if (saturdayItems != null && !saturdayItems.isEmpty()) {
			scheduledInfo.setDay("Saturday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(saturdayItems);
			schduleRequest.setMenuItems(saturdayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", saturdayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This Method is responsible to schedule Order for Sunday by using jobDetail
	 * and Trigger Scheduler will schedule the job by using JobDetail and trigger
	 */
	public ScheduleResponse scheduleOrderForSunday(List<Menu> sundayItems, SchduleRequest schduleRequest,
			ScheduledInfo scheduledInfo, SchedulerRepository schedulerRepository, RabbitTemplate rabbitTemplate) {
		log.info("Request for Sunday items {}", sundayItems);
		if (sundayItems != null && !sundayItems.isEmpty()) {
			scheduledInfo.setDay("Sunday");
			scheduledInfo.setEmailId(schduleRequest.getEmailId());
			scheduledInfo.setMenuItems(sundayItems);
			schduleRequest.setMenuItems(sundayItems);
			String key = UUID.randomUUID().toString();
			String value = "food auto scheduling jobs";
			scheduledInfo.setJobKey(key);
			scheduledInfo.setJobvalue(value);

			LocalDate ld = LocalDate.now();
			ld = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

			LocalTime of = LocalTime.of(12, 30);
			LocalDateTime atTime = ld.atTime(of);

			ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime dateTime = ZonedDateTime.of(atTime, zoneid1);

			try {
				JobDetail jobDetail = buildJobDetail(scheduledInfo);
				Trigger trigger = buildJobTrigger(jobDetail, dateTime);
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getContext().put("schedulerRepository", schedulerRepository);
				scheduler.getContext().put("rabbitTemplate", rabbitTemplate);
				scheduler.scheduleJob(jobDetail, trigger);
				schedulerRepository.save(scheduledInfo);
				scheduler.start();
				return new ScheduleResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
						"Scheduled Successfully!", sundayItems);
			} catch (SchedulerException ex) {
				log.error("Error scheduling order", ex);
				return new ScheduleResponse(false, "Error scheduling order. Please try later!");
			}
		} else {
			return new ScheduleResponse(false, "No food items to schedule");
		}
	}

	/*
	 * This method is to cancel all the schedules.
	 */
	public String deletescheduleOrder(String emailId) {
		log.info("Request to cancel the Schedule items {}", emailId);
		try {
			scheduler.shutdown();
			List<ScheduledInfo> persistedScheduledInfo = schedulerRepository.findByEmailId(emailId);
			if (persistedScheduledInfo != null && !persistedScheduledInfo.isEmpty()) {
				schedulerRepository.deleteAll(persistedScheduledInfo);
			}
			return "Cancelled all scheduled order";
		} catch (SchedulerException exception) {
			log.error("Error scheduling order", exception);
			return "Error scheduling order. Please try later!";
		}
	}

	/*
	 * This Method is to destroy the scheduler once all the scheduling is completed
	 */
	@PreDestroy
	public void preDestroy() {
		log.info("To shutdown the scheduler");
		try {
			scheduler.shutdown();
			log.info("scheduler Destroyed");
		} catch (SchedulerException exception) {
			log.error("Error while Destroying the scheduler", exception);
			throw new SchedulerFailedException("Error while shutdown");
		}
	}
}
