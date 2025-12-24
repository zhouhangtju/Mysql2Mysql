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
@TableName("alarm_data")
public class AlarmRecordResult2 {
    /**
     * 自增主键，唯一标识每条告警记录
     */

    private Long id;


    private String alarmName;


    private String alarmKey;
    /**
     * 攻击源IP地址(支持IPv6)
     */

    private String srcIp;

    /**
     * 被攻击的目标IP地址
     */

    private String dstIp;

    /**
     * 攻击源端口(可选)
     */

    private String protocol;

    private String payload;


    private String aiTag;

    private String keyword;

    private String aiAttackType;

    private Date lastAlarmTime;






}