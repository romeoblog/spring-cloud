/*
 *  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cloud.mesh.quartz.configuration;

import com.cloud.mesh.core.common.holder.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Simple implementation of the Quartz Job interface, applying the
 * passed-in JobDataMap and also the SchedulerContext as bean property values.
 *
 * @author willlu.zheng
 * @date 2019-09-16
 */
@Slf4j
@DisallowConcurrentExecution
public class InvokingJobDetailFactory extends QuartzJobBean {

    private String targetObject;

    private String targetMethod;

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     *
     * @see #execute
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        log.info("Running Job service name: {} ", map.get("targetObject"));
        long start = System.nanoTime();

        try {
            Object object = SpringContextHolder.getBean(targetObject);
            Method m;
            try {
                m = object.getClass().getMethod(targetMethod);
                m.invoke(object);

                long stop = System.nanoTime();
                long diffNanos = stop - start;
                long diff = TimeUnit.MILLISECONDS.convert(diffNanos, TimeUnit.NANOSECONDS);

                log.info("Running Job has been completed, cost time: {}ms\n", diff);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }

    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
