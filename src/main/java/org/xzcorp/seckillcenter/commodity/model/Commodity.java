package org.xzcorp.seckillcenter.commodity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Commodity {

    @Id
    private String commodityId;
    private String commodityName;
    private Long commodityPrice;

    protected Commodity() {
    }

    public Commodity(String commodityId, String commodityName, Long commodityPrice) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
    }
}
