package com.subscription_system.email_notifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

public class EmailService implements Service {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public EmailService() {
  }

  @Override
  public boolean send(ArrayList<Object> recordsValue) {
    LOGGER.info("SENDING MAIL BASED ON EVENT:");
    LOGGER.info("-----------------------------");
    for(Object object : recordsValue){
      LOGGER.info(object.toString());
    }
    LOGGER.info("-----------------------------");
    LOGGER.info("EMAIL SENT");
    return true;
  }
}
