package com.slm.redis.pubsub.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisReceiver2 implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("listener 2 receive message: {}", message);
    }

}
