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

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * The Quartz Configuration
 *
 * @author willlu.zheng
 * @date 2019-09-16
 */
@Configuration
public class QuartzConfig {

    private static final String DEFAULT_JOB_GROUP_NAME = "DEFAULT";

    public static JobDetail createJobDetail(String targetObject) {
        return createJobDetail(DEFAULT_JOB_GROUP_NAME, targetObject, "execute");
    }

    public static JobDetail createJobDetail(String targetObject, String targetMethod) {
        return createJobDetail(DEFAULT_JOB_GROUP_NAME, targetObject, targetMethod);
    }

    /**
     * Conveys the detail properties of a given <code>Job</code> instance. JobDetails are
     * to be created/defined with {@link org.quartz.JobBuilder}.
     *
     * @param groupName
     * @param targetObject
     * @param targetMethod
     * @return
     */
    public static JobDetail createJobDetail(String groupName, String targetObject, String targetMethod) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        if (StringUtils.isNotBlank(groupName)) {
            groupName = DEFAULT_JOB_GROUP_NAME;
        }
        jobDetailFactoryBean.setGroup(groupName);
        jobDetailFactoryBean.setName(targetObject);
        jobDetailFactoryBean.setJobClass(InvokingJobDetailFactory.class);
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);
        Map<String, String> map = new HashMap<>(2);
        map.put("targetObject", targetObject);
        map.put("targetMethod", targetMethod);
        jobDetailFactoryBean.setJobDataAsMap(map);
        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean.getObject();
    }

    public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
        cronTrigger.setJobDetail(jobDetail);
        cronTrigger.setCronExpression(cronExpression);
        return cronTrigger;
    }

}
