package com.onloadcode.quartz.lesson.service;

import com.onloadcode.quartz.lesson.bean.request.JobDetailRequestBean;
import com.onloadcode.quartz.lesson.bean.response.SchedulerResponseBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.quartz.JobKey.jobKey;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Scheduler scheduler;

    /**
     * Create job scheduler response bean.
     *
     * @param jobGroup             the job group
     * @param jobDetailRequestBean the job detail request bean
     * @return the scheduler response bean
     */
    public SchedulerResponseBean createJob(
            String jobGroup, JobDetailRequestBean jobDetailRequestBean) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        jobDetailRequestBean.setGroup(jobGroup);
        JobDetail jobDetail = jobDetailRequestBean.buildJobDetail();
        Set<Trigger> triggersForJob = jobDetailRequestBean.buildTriggers();
        log.info("About to save job with key - {}", jobDetail.getKey());
        try {
            scheduler.scheduleJob(jobDetail, triggersForJob, false);
            log.info("Job with key - {} saved sucessfully", jobDetail.getKey());
            responseBean.setResult(jobDetailRequestBean);
            responseBean.setResultCode(HttpStatus.CREATED);
        } catch (SchedulerException e) {
            log.error(
                    "Could not save job with key - {} due to error - {}",
                    jobDetail.getKey(),
                    e.getLocalizedMessage());
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
        return responseBean;
    }

    /**
     * Find job scheduler response bean.
     *
     * @param jobGroup the job group
     * @param jobName  the job name
     * @return the scheduler response bean
     */
    public SchedulerResponseBean findJob(String jobGroup, String jobName) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey(jobName, jobGroup));
            if (Objects.nonNull(jobDetail))
                responseBean.setResult(
                        Optional.of(
                                JobDetailRequestBean.buildJobDetail(
                                        jobDetail, scheduler.getTriggersOfJob(jobKey(jobName, jobGroup)))));
            responseBean.setResultCode(HttpStatus.OK);

        } catch (SchedulerException e) {
            String errorMsg =
                    String.format(
                            "Could not find job with key - %s.%s  due to error -  %s",
                            jobGroup, jobName, e.getLocalizedMessage());
            log.error(errorMsg);
            responseBean.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setResult(errorMsg);
        }
        log.warn("Could not find job with key - {}.{}", jobGroup, jobName);
        return responseBean;
    }

    /**
     * Update job scheduler response bean.
     *
     * @param jobGroup             the job group
     * @param jobName              the job name
     * @param jobDetailRequestBean the job detail request bean
     * @return the scheduler response bean
     */
    public SchedulerResponseBean updateJob(
            String jobGroup, String jobName, JobDetailRequestBean jobDetailRequestBean) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        try {
            JobDetail oldJobDetail = scheduler.getJobDetail(jobKey(jobName, jobGroup));
            if (Objects.nonNull(oldJobDetail)) {
                JobDataMap jobDataMap = oldJobDetail.getJobDataMap();
                jobDataMap.put("orgCode", jobDetailRequestBean.getOrgCode());
                jobDataMap.put("jobType", jobDetailRequestBean.getJobType());
                jobDataMap.put("uniqueKey", jobDetailRequestBean.getUniqueKey());
                jobDataMap.put("data", jobDetailRequestBean.getData());
                JobBuilder jb = oldJobDetail.getJobBuilder();
                JobDetail newJobDetail = jb.usingJobData(jobDataMap).storeDurably().build();
                scheduler.addJob(newJobDetail, true);
                log.info("Updated job with key - {}", newJobDetail.getKey());
                responseBean.setResult(jobDetailRequestBean);
                responseBean.setResultCode(HttpStatus.CREATED);
            }
            log.warn("Could not find job with key - {}.{} to update", jobGroup, jobName);
        } catch (SchedulerException e) {
            String errorMsg =
                    String.format(
                            "Could not find job with key - %s.%s to update due to error -  %s",
                            jobGroup, jobName, e.getLocalizedMessage());
            log.error(errorMsg);
            responseBean.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setResult(errorMsg);
        }
        return responseBean;
    }

    /**
     * Delete job scheduler response bean.
     *
     * @param jobGroup the job group
     * @param jobName  the job name
     * @return the scheduler response bean
     */
    public SchedulerResponseBean deleteJob(String jobGroup, String jobName) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        try {
            scheduler.deleteJob(jobKey(jobName, jobGroup));
            String msg = "Deleted job with key - " + jobGroup + "." + jobName;
            responseBean.setResult(msg);
            responseBean.setResultCode(HttpStatus.OK);
            log.info(msg);
        } catch (SchedulerException e) {
            String errorMsg =
                    String.format(
                            "Could not find job with key - %s.%s to Delete due to error -  %s",
                            jobGroup, jobName, e.getLocalizedMessage());
            log.error(errorMsg);
            responseBean.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setResult(errorMsg);
        }
        return responseBean;
    }

    /**
     * Pause job scheduler response bean.
     *
     * @param jobGroup the job group
     * @param jobName  the job name
     * @return the scheduler response bean
     */
    public SchedulerResponseBean pauseJob(String jobGroup, String jobName) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        try {
            scheduler.pauseJob(jobKey(jobName, jobGroup));
            String msg = "Paused job with key - " + jobGroup + "." + jobName;
            responseBean.setResult(msg);
            responseBean.setResultCode(HttpStatus.OK);
        } catch (SchedulerException e) {
            String errorMsg =
                    String.format(
                            "Could not find job with key - %s.%s  due to error -  %s",
                            jobGroup, jobName, e.getLocalizedMessage());
            log.error(errorMsg);
            responseBean.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setResult(errorMsg);
        }
        return responseBean;
    }

    /**
     * Resume job scheduler response bean.
     *
     * @param jobGroup the job group
     * @param jobName  the job name
     * @return the scheduler response bean
     */
    public SchedulerResponseBean resumeJob(String jobGroup, String jobName) {
        SchedulerResponseBean responseBean = new SchedulerResponseBean();
        try {
            scheduler.resumeJob(jobKey(jobName, jobGroup));
            String msg = "Resumed job with key - " + jobGroup + "." + jobName;
            responseBean.setResult(msg);
            responseBean.setResultCode(HttpStatus.OK);
        } catch (SchedulerException e) {
            String errorMsg =
                    String.format(
                            "Could not find job with key - %s.%s  due to error -  %s",
                            jobGroup, jobName, e.getLocalizedMessage());
            log.error(errorMsg);
            responseBean.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setResult(errorMsg);
        }
        return responseBean;
    }
}
