package org.xzcorp.seckillcenter.activity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzcorp.seckillcenter.activity.model.Activity;
import org.xzcorp.seckillcenter.activity.model.ActivityRepository;
import org.xzcorp.seckillcenter.activity.service.ActivityService;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;

import java.util.Optional;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private FlowTemplate flowTemplate;
    @Override
    public Result createActivity(Activity activity) {
        return  flowTemplate.execute(()->{
            Optional<Activity> activityInDB = activityRepository.findById(activity.getActivityId());
            if(activityInDB.isPresent()){
                throw new SecException(ErrorCode.ACTIVITY_EXIST,"活动已存在，activity="+activity);
            }
            activityRepository.save(activity);
            return new Result(true,activity);
        });


    }

    @Override
    public boolean modifyActivity(Activity activity) {
        return true;
    }
}
