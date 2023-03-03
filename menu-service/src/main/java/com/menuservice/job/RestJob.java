package com.menuservice.job;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.menuservice.model.Order;
import com.menuservice.model.ScheduledInfo;
import com.menuservice.repository.SchedulerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RefreshScope
public class RestJob extends QuartzJobBean {

	private SchedulerRepository schedulerRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) {
		try {
			schedulerRepository = (SchedulerRepository) jobExecutionContext.getScheduler().getContext()
					.get("schedulerRepository");

			rabbitTemplate = (RabbitTemplate) jobExecutionContext.getScheduler().getContext()
					.get("rabbitTemplate");

			String jobKey = jobExecutionContext.getJobDetail().getKey().toString();
			String key = jobKey.substring(26);
			ScheduledInfo findByJobKey = schedulerRepository.findByJobKey(key);
			Order orderInfo = new Order();
			orderInfo.setEmailId(findByJobKey.getEmailId());
			orderInfo.setMenuItems(findByJobKey.getMenuItems());
			rabbitTemplate.convertAndSend("order_exchange", "order_routingKey", orderInfo);
		
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
	}

}