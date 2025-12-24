package com.mobile.safe.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mobile.safe.dao.AlarmData2Dao;
import com.mobile.safe.dao.AlarmRecordResult2Dao;
import com.mobile.safe.dao.AlarmRecordResultDao;
import com.mobile.safe.db.AlarmData2;
import com.mobile.safe.db.AlarmRecordResult;
import com.mobile.safe.db.AlarmRecordResult2;
import com.mobile.safe.service.AlarmRecordResultService;
import com.mobile.safe.service.ReadAlarmDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@DS("db2")
public class ReadAlarmDataImpl extends ServiceImpl<AlarmData2Dao, AlarmData2> implements ReadAlarmDataService {

    @Autowired
    private AlarmData2Dao alarmData2Dao;
    @Autowired
    private AlarmRecordResultDao alarmRecordResultdao;
    @Autowired
    private AlarmRecordResult2Dao alarmRecordResult2dao;



    @Override
    public List<AlarmRecordResult> readAlarmData(Long startID,Long endID) {

        List<AlarmData2> data2List = getRecordsGreaterThanId(startID,endID);
        ArrayList<AlarmRecordResult> results = new ArrayList<>();
        log.info("本次更新条数{}",data2List.size());
        data2List.forEach(item -> {
            AlarmRecordResult recordResult = new AlarmRecordResult();
            BeanUtils.copyProperties(item,recordResult);
            recordResult.setId(item.getID());
            results.add(recordResult);
        });
      //  log.info("recordResult数据{}",results.get(0));
        return results;
    }

    @Override
    public AlarmRecordResult getNewData() {
        LambdaQueryWrapper<AlarmData2> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(AlarmData2::getID)  // 按ID倒序排列
                .last("LIMIT 1");

        AlarmData2 alarmData2 = alarmData2Dao.selectOne(wrapper);
        log.info("获取当前最新的告警数据{}",alarmData2);
        AlarmRecordResult recordResult = new AlarmRecordResult();
        BeanUtils.copyProperties(alarmData2,recordResult);
        recordResult.setId(alarmData2.getID());
        return recordResult;
    }

    @Override
    public AlarmRecordResult getOldData() {
        LambdaQueryWrapper<AlarmData2> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("LIMIT 1");

        AlarmData2 alarmData2 = alarmData2Dao.selectOne(wrapper);
        log.info("获取当前最新的告警数据{}",alarmData2);
        AlarmRecordResult recordResult = new AlarmRecordResult();
        BeanUtils.copyProperties(alarmData2,recordResult);
        return recordResult;
    }

    @Override
    public AlarmRecordResult importData() {
        return null;
    }

    public List<AlarmData2> getRecordsGreaterThanId(Long startID,Long endID) {
        LambdaQueryWrapper<AlarmData2> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(AlarmData2::getID, startID, endID);
        // 按 ID 升序排序（可选，根据需求调整）
   //     queryWrapper.orderByAsc(AlarmData2::getID);
        return alarmData2Dao.selectList(queryWrapper);
    }
}