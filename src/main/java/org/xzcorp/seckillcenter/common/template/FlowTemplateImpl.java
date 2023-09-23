package org.xzcorp.seckillcenter.common.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;

@Component
public class FlowTemplateImpl implements FlowTemplate {

    private static final Logger logger = LoggerFactory.getLogger(FlowTemplateImpl.class);

    @Override
    public  Result execute(FlowCallBack flowCallBack) {
        Result result = new Result();
        try{
            result = flowCallBack.flow();
        }catch (SecException se){
            logger.warn("业务执行异常，errorCode="+se.getErrorCode()+",errorMsg="+se.getMessage());
            result.setSuccess(false);
            result.setErrorCode(se.getErrorCode().getCode());
            result.setErrorDesc(se.getErrorCode().getName());
        }catch (Exception e) {
            logger.error("业务执行未知异常!",e);
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.SYSTEM_ERROR.getCode());
            result.setErrorDesc(ErrorCode.SYSTEM_ERROR.getName());
        }
        return result;
    }

}
