package com.notificationservice.modal;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SubscriptionEmail {
private String emailId;
private String name;
private String subscriptionId;
private String subscriptionName;
private double subscriptionPrice;

private LocalDate endDate;
private String subscriptionDescription;
private String subscriptionImage;

}
