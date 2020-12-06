package com.onloadcode.quartz.lesson.controller;

import com.onloadcode.quartz.lesson.bean.request.JobDetailRequestBean;
import com.onloadcode.quartz.lesson.bean.response.SchedulerResponseBean;
import com.onloadcode.quartz.lesson.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */
@RestController
@RequestMapping("/api/v1/scheduler")
@Tag(
        name = "SchedulerManagementController",
        description = "MongoDB Quartz | Scheduler Management API")
public class SchedulerManagementController {

    public static final String JOBS = "/job-group/{jobGroup}/jobs";
    public static final String JOBS_BY_NAME = "/job-group/{jobGroup}/jobs/{jobName}";
    public static final String JOBS_PAUSE = "/job-group/{jobGroup}/jobs/{jobName}/pause";
    public static final String JOBS_RESUME = "/job-group/{jobGroup}/jobs/{jobName}/resume";
    @Autowired
    private SchedulerService schedulerService;

    @Operation(
            summary = "MongoDB Quartz | Scheduler create a new Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job Creation API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job Creation type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @PostMapping(path = JOBS)
    public ResponseEntity<SchedulerResponseBean> createJob(
            @PathVariable String jobGroup, @RequestBody JobDetailRequestBean jobDetailRequestBean) {
        return new ResponseEntity<SchedulerResponseBean>(
                schedulerService.createJob(jobGroup, jobDetailRequestBean), CREATED);
    }

    @Operation(
            summary = "MongoDB Quartz | Scheduler find a Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job find API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job find type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @GetMapping(path = JOBS_BY_NAME)
    public ResponseEntity<SchedulerResponseBean> findJob(
            @PathVariable String jobGroup, @PathVariable String jobName) {
        return new ResponseEntity<>(schedulerService.findJob(jobGroup, jobName), OK);
    }

    @Operation(
            summary = "MongoDB Quartz | Scheduler update an existing Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job update API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job update type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @PutMapping(path = JOBS_BY_NAME)
    public ResponseEntity<SchedulerResponseBean> updateJob(
            @PathVariable String jobGroup,
            @PathVariable String jobName,
            @RequestBody JobDetailRequestBean jobDetailRequestBean) {
        return new ResponseEntity<>(schedulerService.updateJob(jobGroup, jobName, jobDetailRequestBean), OK);
    }

    @Operation(
            summary = "MongoDB Quartz | Scheduler delete Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job delete API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job delete type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @DeleteMapping(path = JOBS_BY_NAME)
    public ResponseEntity<SchedulerResponseBean> deleteJob(
            @PathVariable String jobGroup, @PathVariable String jobName) {
        return new ResponseEntity<>(schedulerService.deleteJob(jobGroup, jobName), OK);
    }

    @Operation(
            summary = "MongoDB Quartz | Scheduler pause Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job pause API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job pause type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @PatchMapping(path = JOBS_PAUSE)
    public ResponseEntity<SchedulerResponseBean> pauseJob(
            @PathVariable String jobGroup, @PathVariable String jobName) {
        return new ResponseEntity<>(schedulerService.pauseJob(jobGroup, jobName), OK);
    }

    @Operation(
            summary = "MongoDB Quartz | Scheduler resume Job",
            description = "",
            tags = {"SchedulerManagementController"})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Scheduler Job resume API not found",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request,Scheduler Job resume type not supported",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failure",
                            content = @Content(schema = @Schema(implementation = SchedulerResponseBean.class)))
            })
    @PatchMapping(path = JOBS_RESUME)
    public ResponseEntity<SchedulerResponseBean> resumeJob(
            @PathVariable String jobGroup, @PathVariable String jobName) {
        return new ResponseEntity<>(schedulerService.resumeJob(jobGroup, jobName), OK);
    }
}
