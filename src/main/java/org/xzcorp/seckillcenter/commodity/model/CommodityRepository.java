package org.xzcorp.seckillcenter.commodity.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommodityRepository extends CrudRepository<Commodity, String> {

     Optional<Commodity> findByCommodityId(String commodityId);

     Commodity save(Commodity activity);

}
