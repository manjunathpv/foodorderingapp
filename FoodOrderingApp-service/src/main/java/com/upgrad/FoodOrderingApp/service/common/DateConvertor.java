package com.upgrad.FoodOrderingApp.service.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConvertor {

  public Date convertLocalDateTimeToDate(){
    Date in = new Date();
    LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
    Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    return out;
  }
}
