package com.mobile.safe.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 告警记录实体类
 */
@Data
@TableName("VIEW_ALARM_INFO_TELE")
public class AlarmData2 {
    /**
     * 自增主键，唯一标识每条告警记录
     */
    @TableId(value = "ID")
    @TableField("ID")
    private Long ID;

    @TableField("ALARM_NAME")
    private String alarmName;

    @TableField("ALARM_KEY")
    private String alarmKey;
    /**
     * 攻击源IP地址(支持IPv6)
     */
    @TableField("SRC_IP")
    private String srcIp;

    /**
     * 被攻击的目标IP地址
     */
    @TableField("DST_IP")
    private String dstIp;

    /**
     * 攻击源端口(可选)
     */
    @TableField("PROTOCOL")
    private String protocol;
    @TableField("PAYLOAD")
    private String payload;

    @TableField("AI_TAG")
    private String aiTag;
    @TableField("KEYWORD")
    private String keyword;
    @TableField("AI_ATTACK_TYPE")
    private String aiAttackType;
    @TableField("LAST_ALARM_TIME")
    private Date lastAlarmTime;




}