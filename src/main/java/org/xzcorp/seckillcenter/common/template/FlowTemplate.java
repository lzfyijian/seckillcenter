package org.xzcorp.seckillcenter.common.template;

import org.xzcorp.seckillcenter.common.model.result.Result;

public  interface FlowTemplate {

    public Result execute(FlowCallBack flowCallBack);
}
