package com.mobile.safe.db;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GroupResult {
    private String ids;

    private String groupName;

    private Date alarmTime;

    private Integer truePayload;

    private String srcIp;

    private String dstIp;

    private String isSafeAttack;

    private String attackType;

}
