package com.yxkj.service.impl;

import com.yxkj.beans.RedisConfig;
import com.yxkj.dao.CmdDao;
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.CommandRecord;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CmdService;
import com.yxkj.utils.JedisUtil;
import com.yxkj.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

import static com.yxkj.beans.CommonAttributes.JEDIS_MESSAGE_KEY;

/**
 * @author huyong
 * @since 2017/9/26
 */
@Service("cmdServiceImpl")
public class CmdServiceImpl extends BaseServiceImpl<CommandRecord, Long> implements CmdService {

    private CmdDao cmdDao;

    @Autowired
    public CmdServiceImpl(RedisConfig redisConfig, CmdDao cmdDao) {
        this.cmdDao = cmdDao;
        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxActive(5000);
        config.setMaxIdle(256);
//        config.setMaxWait(5000L);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        config.setMinEvictableIdleTimeMillis(60000L);
        config.setTimeBetweenEvictionRunsMillis(3000L);
        config.setNumTestsPerEvictionRun(-1);
        JedisUtil.init(config, redisConfig.getJedisIp(), redisConfig.getJedisPort(), redisConfig.getJedisTimeout(), redisConfig.getJedisPassword());
    }

    @Override
    public void sendCmd(CmdMsg cmdMsg) {
        CommandRecord record = new CommandRecord();

        //保存指令到数据库
        record.setCmdContent(cmdMsg.getContent());
        record.setCmdType(CommonEnum.CmdType.values()[cmdMsg.getType()]);
        record.setDeviceNo(cmdMsg.getDeviceNo());
        record.setCmdStatus(CommonEnum.CmdStatus.SendOut);
        cmdDao.persist(record);

        cmdMsg.setId(record.getId());
        try {
            JedisUtil.rpush(JEDIS_MESSAGE_KEY.getBytes(), ObjectUtil.object2Bytes(cmdMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
