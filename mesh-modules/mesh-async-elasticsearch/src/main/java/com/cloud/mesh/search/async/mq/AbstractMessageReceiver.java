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
package com.cloud.mesh.search.async.mq;

import com.cloud.mesh.core.common.holder.SpringContextHolder;
import com.cloud.mesh.search.async.canal.CanalMsgContent;
import com.cloud.mesh.search.async.event.DeleteCanalEvent;
import com.cloud.mesh.search.async.event.InsertCanalEvent;
import com.cloud.mesh.search.async.event.UpdateCanalEvent;

/**
 * The type abstract message receiver
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
public abstract class AbstractMessageReceiver {

    private static final String INSERT = "INSERT";

    private static final String UPDATE = "UPDATE";

    private static final String DELETE = "DELETE";

    public void publishCanalEvent(CanalMsgContent entry) {
        String eventType = entry.getEventType();
        System.out.println("======"+eventType);
        if (eventType.equalsIgnoreCase(INSERT)) {
            SpringContextHolder.getApplicationContext().publishEvent(new InsertCanalEvent(entry));
        } else if (eventType.equalsIgnoreCase(UPDATE)) {
            SpringContextHolder.getApplicationContext().publishEvent(new UpdateCanalEvent(entry));
        } else if (eventType.equalsIgnoreCase(DELETE)) {
            SpringContextHolder.getApplicationContext().publishEvent(new DeleteCanalEvent(entry));
        } else {
            return;
        }
    }

    protected abstract void onMessage(String message) throws Exception;

}