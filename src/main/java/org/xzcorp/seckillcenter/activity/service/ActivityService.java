package org.xzcorp.seckillcenter.activity.service;

import org.xzcorp.seckillcenter.activity.model.Activity;
import org.xzcorp.seckillcenter.common.model.result.Result;

public interface ActivityService {

    public Result createActivity(Activity activity);
    public boolean modifyActivity(Activity activity);
}
