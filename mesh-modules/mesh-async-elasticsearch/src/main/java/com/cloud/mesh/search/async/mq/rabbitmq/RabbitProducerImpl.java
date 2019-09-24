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
package com.cloud.mesh.search.async.mq.rabbitmq;

import com.cloud.mesh.common.utils.JsonUtils;
import com.cloud.mesh.search.async.configuration.AmqpConfig;
import com.cloud.mesh.search.async.mq.IMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Rabbit Producer Implement
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component("rabbitProducer")
public class RabbitProducerImpl implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, IMessageSender {

    private static Logger logger = LoggerFactory.getLogger(RabbitProducerImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * Confirmation callback.
     *
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("Send message successful. id={}", correlationData.getId());
        } else {
            logger.info("Send message failed: cause={}", cause);
        }

    }

    /**
     * Returned message callback.
     *
     * @param message    the returned message.
     * @param replyCode  the reply code.
     * @param replyText  the reply text.
     * @param exchange   the exchange.
     * @param routingKey the routing key.
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("ReturnedMessage fail message method: CorrelationId={}", message.getMessageProperties().getCorrelationId());
    }

    @Override
    public void send(com.cloud.mesh.search.async.mq.Message message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        String content = JsonUtils.toJson(message);

        logger.info("RabbitProducer=> Send the producer record: {}", content);

        rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, content, correlationId);
    }
}