package com.yxkj.beans;

import java.io.Serializable;

/**
 * @author huyong
 * @since 2017/9/26
 */
public class CmdMsg implements Serializable {
    private Long id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     * 1.出货指令
     * 2.APP更新指令
     * 3.广告更新指令
     */
    private int type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
