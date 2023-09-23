package org.xzcorp.seckillcenter.activity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    private String activityId;

    private String userId;

    private String commodityId;

    private Date createDate;

    public Activity() {
    }

    public Activity(String activityId, String userId, String commodityId, Date createDate) {
        this.activityId = activityId;
        this.userId = userId;
        this.commodityId= commodityId;
        this.createDate=createDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Activity[id=%s, userId='%s', commodityId='%s']",
                activityId, userId, commodityId);
    }
}
