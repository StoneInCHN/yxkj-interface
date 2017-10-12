package com.yxkj.service;

import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.service.BaseService;

/**
 * @author huyong
 * @since 2017/9/26
 */
public interface CmdService extends BaseService<CommandRecord,Long> {
    public void sendCmd(CmdMsg cmdMsg);
}
