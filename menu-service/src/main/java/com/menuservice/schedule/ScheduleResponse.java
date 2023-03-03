package com.menuservice.schedule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menuservice.model.Menu;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponse {
	private boolean success;
	private String jobId;
	private String jobGroup;
	private String message;
	private List<Menu> menuItems;

	public ScheduleResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ScheduleResponse(boolean success, String jobId, String jobGroup, String message, List<Menu> menuItems) {
		this.success = success;
		this.jobId = jobId;
		this.jobGroup = jobGroup;
		this.message = message;
		this.menuItems = menuItems;
	}

	public List<Menu> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<Menu> menuItems) {
		this.menuItems = menuItems;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}