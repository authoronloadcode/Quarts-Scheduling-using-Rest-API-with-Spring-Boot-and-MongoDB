package com.onloadcode.quartz.lesson.bean.request;

import lombok.Data;
import org.quartz.JobDataMap;
import org.quartz.Trigger;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static java.time.ZoneId.systemDefault;
import static java.util.UUID.randomUUID;
import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */
@Data
public class TriggerDetailsRequestBean implements Serializable {
    @NotBlank
    private String name;
    private String group;
    private LocalDateTime fireTime;
    private String cron;

    /**
     * Build trigger details trigger details request bean.
     *
     * @param trigger the trigger
     * @return the trigger details request bean
     */
    public static TriggerDetailsRequestBean buildTriggerDetails(Trigger trigger) {
        return new TriggerDetailsRequestBean()
                .setName(trigger.getKey().getName())
                .setGroup(trigger.getKey().getGroup())
                .setFireTime((LocalDateTime) trigger.getJobDataMap().get("fireTime"))
                .setCron(trigger.getJobDataMap().getString("cron"));
    }

    /**
     * Sets cron.
     *
     * @param cron the cron
     * @return the cron
     */
    public TriggerDetailsRequestBean setCron(final String cron) {
        this.cron = cron;
        return this;
    }

    /**
     * Sets fire time.
     *
     * @param fireTime the fire time
     * @return the fire time
     */
    public TriggerDetailsRequestBean setFireTime(final LocalDateTime fireTime) {
        this.fireTime = fireTime;
        return this;
    }

    /**
     * Sets group.
     *
     * @param group the group
     * @return the group
     */
    public TriggerDetailsRequestBean setGroup(final String group) {
        this.group = group;
        return this;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public TriggerDetailsRequestBean setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Build trigger trigger.
     *
     * @return the trigger
     */
    public Trigger buildTrigger() {
        if (!isEmpty(cron)) {
            if (!isValidExpression(cron))
                throw new IllegalArgumentException(
                        "Provided expression " + cron + " is not a valid cron expression");
            return newTrigger()
                    .withIdentity(buildName(), group)
                    .withSchedule(
                            cronSchedule(cron)
                                    .withMisfireHandlingInstructionFireAndProceed()
                                    .inTimeZone(TimeZone.getTimeZone(systemDefault())))
                    .usingJobData("cron", cron)
                    .build();
        } else if (!isEmpty(fireTime)) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("fireTime", fireTime);
            return newTrigger()
                    .withIdentity(buildName(), group)
                    .withSchedule(simpleSchedule().withMisfireHandlingInstructionNextWithExistingCount())
                    .startAt(Date.from(fireTime.atZone(systemDefault()).toInstant()))
                    .usingJobData(jobDataMap)
                    .build();
        }
        throw new IllegalStateException("unsupported trigger details " + this);
    }

    private String buildName() {
        return isEmpty(name) ? randomUUID().toString() : name;
    }
}
