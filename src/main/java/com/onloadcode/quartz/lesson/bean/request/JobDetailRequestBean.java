package com.onloadcode.quartz.lesson.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onloadcode.quartz.lesson.job.SampleJob;
import lombok.Data;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */
@Data
public class JobDetailRequestBean implements Serializable {
    @NotBlank
    private String name;
    private String group;
    @JsonProperty("triggers")
    private List<TriggerDetailsRequestBean> triggerDetails = new ArrayList<>();
    @NotEmpty
    private String orgCode;
    @NotEmpty
    private String jobType;
    @NotEmpty
    private String uniqueKey;
    private Map<String, Object> data = new LinkedHashMap<>();

    public static JobDetailRequestBean buildJobDetail(JobDetail jobDetail, List<? extends Trigger> triggersOfJob) {
        List<TriggerDetailsRequestBean> triggerDetailsRequestBeanList = triggersOfJob.stream()
                .map(TriggerDetailsRequestBean::buildTriggerDetails)
                .collect(Collectors.toList());

        return new JobDetailRequestBean()
                .setName(jobDetail.getKey().getName())
                .setGroup(jobDetail.getKey().getGroup())
                .setOrgCode(jobDetail.getJobDataMap().getString("orgCode"))
                .setJobType(jobDetail.getJobDataMap().getString("jobType"))
                .setUniqueKey(jobDetail.getJobDataMap().getString("uniqueKey"))
                .setData((Map<String, Object>) jobDetail.getJobDataMap().get("data"))
                .setTriggerDetails(triggerDetailsRequestBeanList);
    }

    public JobDetailRequestBean setTriggerDetails(final List<TriggerDetailsRequestBean> triggerDetails) {
        this.triggerDetails = triggerDetails;
        return this;
    }

    public JobDetailRequestBean setData(final Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public JobDetailRequestBean setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
        return this;
    }

    public JobDetailRequestBean setJobType(String jobType) {
        this.jobType = jobType;
        return this;
    }

    public JobDetailRequestBean setOrgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public JobDetailRequestBean setGroup(final String group) {
        this.group = group;
        return this;
    }

    public JobDetailRequestBean setName(final String name) {
        this.name = name;
        return this;
    }

    public JobDetail buildJobDetail() {
        JobDataMap jobDataMap = new JobDataMap(getData());
        jobDataMap.put("orgCode", orgCode);
        jobDataMap.put("jobType", jobType);
        jobDataMap.put("uniqueKey", uniqueKey);
        jobDataMap.put("data", data);
        return newJob(SampleJob.class)
                .withIdentity(getName(), getGroup())
                .usingJobData(jobDataMap)
                .build();
    }

    @JsonIgnore
    public Set<Trigger> buildTriggers() {
        return triggerDetails.stream()
                .map(TriggerDetailsRequestBean::buildTrigger)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
