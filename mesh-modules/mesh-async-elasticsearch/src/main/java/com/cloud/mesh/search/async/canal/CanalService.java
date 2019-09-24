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
package com.cloud.mesh.search.async.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.cloud.mesh.common.utils.DateUtils;
import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.async.consts.CommonConstant;
import com.cloud.mesh.search.async.mq.IMessageSender;
import com.cloud.mesh.search.async.mq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * canal客户端服务
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Service("canal")
public class CanalService {

    private static Logger logger = LoggerFactory.getLogger(CanalService.class);

    @Autowired
    @Qualifier(value = "kafkaProducer")
    private IMessageSender messageSender;

    @Resource
    private CanalConnector canalConnector;

    public void start() {
        int emptyCount = 0;
        try {
            while (true) {

                // 获取指定数量的数据
                com.alibaba.otter.canal.protocol.Message message = canalConnector.getWithoutAck(CommonConstant.BATCH_SIZE);
                long batchId = message.getId();
                int size = message.getEntries().size();

                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                } else {
                    emptyCount = 0;
                    try {
                        processEntry(message.getEntries());
                        // 提交确认
                        canalConnector.ack(batchId);
                    } catch (Exception e) {
                        logger.error("发送监听事件失败！batchId回滚,batchId=" + batchId, e);
                        // 处理失败, 回滚数据
                        canalConnector.rollback(batchId);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("canal process error", e);
        } finally {
            canalConnector.disconnect();
        }
    }

    private void processEntry(List<CanalEntry.Entry> entries) {
        List<CanalMsg> msgList = convertToCanalMsgList(entries);
        for (CanalMsg msg : msgList) {
            Message message = new Message();
            message.setId(System.currentTimeMillis());
            message.setMsg(JacksonUtils.toJson(msg.getCanalMsgContent()));
            message.setSendTime(DateUtils.getCurrentDate());
            messageSender.send(message);
//            logger.info("====>msgkey:{},msg:{}", msg.getKey(), msg.getCanalMsgContent());
        }
    }

    private List<CanalMsg> convertToCanalMsgList(List<CanalEntry.Entry> entries) {
        List<CanalMsg> msgList = new ArrayList<>();
        CanalMsgContent canalMsgContent;
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parse error, data:" + entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            canalMsgContent = new CanalMsgContent();
            canalMsgContent.setBinLogFile(entry.getHeader().getLogfileName());
            canalMsgContent.setBinlogOffset(entry.getHeader().getLogfileOffset());
            canalMsgContent.setDbName(entry.getHeader().getSchemaName());
            canalMsgContent.setTableName(entry.getHeader().getTableName());
            canalMsgContent.setEventType(eventType.toString().toLowerCase());

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                canalMsgContent.setDataBefore(convertToCanalChangeInfoList(rowData.getBeforeColumnsList()));
                canalMsgContent.setDataAfter(convertToCanalChangeInfoList(rowData.getAfterColumnsList()));
                CanalMsg canalMsg = new CanalMsg(canalMsgContent);
                msgList.add(canalMsg);
            }
        }

        return msgList;
    }

    private List<CanalChangeInfo> convertToCanalChangeInfoList(List<CanalEntry.Column> columnList) {
        List<CanalChangeInfo> canalChangeInfoList = new ArrayList<CanalChangeInfo>();
        for (CanalEntry.Column column : columnList) {
            CanalChangeInfo canalChangeInfo = new CanalChangeInfo();
            canalChangeInfo.setName(column.getName());
            canalChangeInfo.setValue(column.getValue());
            canalChangeInfo.setUpdate(column.getUpdated());
            canalChangeInfoList.add(canalChangeInfo);
        }

        return canalChangeInfoList;
    }

}
