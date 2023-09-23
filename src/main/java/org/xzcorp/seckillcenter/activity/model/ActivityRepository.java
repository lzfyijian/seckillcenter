package org.xzcorp.seckillcenter.activity.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ActivityRepository extends CrudRepository<Activity,String> {

    Optional<Activity> findById(String activityId);

    Activity save(Activity activity);

    List<Activity> findByCommodityId(String commodityId);

    List<Activity> findAll();
}
