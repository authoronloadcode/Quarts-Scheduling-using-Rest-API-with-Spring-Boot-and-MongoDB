package com.onloadcode.quartz.lesson.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 * Description  : This class {@link  SchedulerResponseBean} use for collect all the Scheduler
 *  * response from the Scheduler endpoints. and this bean response as API response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulerResponseBean {
    private Object result;
    private HttpStatus resultCode;
}
