package org.xzcorp.seckillcenter.commodity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private  String activityId;

    private String commodityId;

    private Long totalCount;

    private Long lockedCount;

    private Long userCount;

    private Date createDate;
}
