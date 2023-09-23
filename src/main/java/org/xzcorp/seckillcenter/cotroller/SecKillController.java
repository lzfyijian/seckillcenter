package org.xzcorp.seckillcenter.cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xzcorp.seckillcenter.SeckillcenterApplication;
import org.xzcorp.seckillcenter.activity.model.Activity;
import org.xzcorp.seckillcenter.activity.model.ActivityRepository;
import org.xzcorp.seckillcenter.activity.service.ActivityService;
import org.xzcorp.seckillcenter.biz.BizService;
import org.xzcorp.seckillcenter.commodity.model.Inventory;
import org.xzcorp.seckillcenter.commodity.model.InventoryRepository;
import org.xzcorp.seckillcenter.commodity.service.InventoryService;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;
import org.xzcorp.seckillcenter.infra.message.MessageService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/seckill")
public class SecKillController {

    private static final Logger log = LoggerFactory.getLogger(SeckillcenterApplication.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private BizService bizService;

    @RequestMapping(value = "/buy") // Map ONLY POST Requests
    public @ResponseBody String buy(@RequestParam String activityId) {
        Result result =  bizService.buy(activityId);
        return result.isSuccess() + "||"+ result.getErrorCode()+ "||" +result.getErrorDesc();
    }

    @PostMapping(path = "/addActivity") // Map ONLY POST Requests
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody String addActivity(@RequestParam String activityId, @RequestParam String userId,
                                             @RequestParam String commodityId,@RequestParam long stocks) {

        Activity  activity = new Activity();
        activity.setActivityId(activityId);
        activity.setUserId(userId);
        activity.setCommodityId(commodityId);
        activity.setCreateDate(new Date());
        Result result  = activityService.createActivity(activity);
        if(result.isSuccess()){
            Inventory inventory = new Inventory();
            inventory.setActivityId(activity.getActivityId());
            inventory.setCommodityId(commodityId);
            inventory.setTotalCount(stocks);
            inventory.setCreateDate(new Date());
            inventoryRepository.save(inventory);
        }
        return result.isSuccess() + "||"+ result.getErrorCode()+ "||" +result.getErrorDesc();
    }

}
