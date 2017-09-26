package com.yxkj.service.impl;

import com.yxkj.beans.CmdMsg;
import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CmdService;
import com.yxkj.utils.JedisUtil;
import com.yxkj.utils.ObjectUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.yxkj.beans.CommonAttributes.JEDIS_MESSAGE_KEY;

/**
 * @author huyong
 * @since 2017/9/26
 */
@Service("cmdServiceImpl")
public class CmdServiceImpl extends BaseServiceImpl<CommandRecord, Long> implements CmdService {

    @Override
    public void sendCmd(CmdMsg cmdMsg) {
        try {
            JedisUtil.rpush(JEDIS_MESSAGE_KEY.getBytes(), ObjectUtil.object2Bytes(cmdMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
