package com.yxkj.job;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yxkj.common.log.LogUtil;
import com.yxkj.service.PropertyKeeperService;

@Component
@Lazy(false)
public class ProKeeperSalesJob {


  @Resource(name = "propertyKeeperServiceImpl")
  private PropertyKeeperService propertyKeeperService;



  @Scheduled(cron = "${job.daily_proKeeper_salesReport.cron}")
  // 每天2点0分0秒执行 0 0 2 * * ?
  // @Scheduled(cron = "0/60 * * * * ?")
  public void proKeeperSalesInfoReport() {

    Calendar startTime = Calendar.getInstance();
    startTime.setTime(new Date());
    startTime.add(Calendar.DATE, -1);
    startTime.set(Calendar.HOUR_OF_DAY, 0);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.SECOND, 0);
    startTime.set(Calendar.MILLISECOND, 0);

    Calendar endTime = Calendar.getInstance();
    endTime.setTime(new Date());
    endTime.add(Calendar.DATE, -1);
    endTime.set(Calendar.HOUR_OF_DAY, 23);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);
    endTime.set(Calendar.MILLISECOND, 999);

    LogUtil.debug(ProKeeperSalesJob.class, "proKeeperSalesInfoReport",
        "daily propertyKeeper sales info report start! Time Period:" + startTime.getTime() + "-"
            + endTime.getTime());

    propertyKeeperService.callProcedure("propertyKeeperProc");
    LogUtil.debug(ProKeeperSalesJob.class, "proKeeperSalesInfoReport",
        "daily propertyKeeper sales info report end! Time Period:" + startTime.getTime() + "-"
            + endTime.getTime());
  }
}
