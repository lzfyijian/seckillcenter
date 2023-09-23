package org.xzcorp.seckillcenter.infra.message.model;

import java.io.Serializable;
import java.util.UUID;

public class SecMessage implements Serializable {

    private String topic;

    private Object data;

    private UUID id;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
