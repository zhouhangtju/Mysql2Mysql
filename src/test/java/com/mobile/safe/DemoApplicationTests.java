package com.mobile.safe;

import com.mobile.safe.dao.AlarmRecordResultCsvDao;
import com.mobile.safe.dao.AlarmRecordResultDao;
import com.mobile.safe.db.*;
import com.mobile.safe.dto.CommonDto;
import com.mobile.safe.service.AlarmRecordResultService;
import com.mobile.safe.service.SafeInterfaceService;
import com.mobile.safe.util.ThreadPoolUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private AlarmRecordResultCsvDao alarmRecordResultCsvDao;
    @Test
    void test3() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        //Date time = DateUtils.addDays(date,-1);
//        Date time = DateUtils.addDays(date,-1);
//        String ds = sdf.format(time);
//        // 创建分区
//        String dd = ds.replace("-", "");
        String fileName = "D:\\work\\attack_data_042200(3).csv";
        //String fileName = "D:\\work\\demo.csv";
        RFC4180Parser parser = new RFC4180ParserBuilder()
                .build();
        // 创建映射策略并指定字段顺序
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(parser).withSkipLines(1).build()){
            ColumnPositionMappingStrategy<AlarmRecordResultCsv> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(AlarmRecordResultCsv.class);
            // 按照文件中字段的实际顺序设置映射

            strategy.setColumnMapping(new String[]{
                    "id",
                    "alarmTime",
                    "dstIp",
                    "srcIp",
                    "alarmName",
                    "payload",
                    "isSafeAttack",
                    "attackType",
                    "attackField",
                    "attackPort",
            });



            // 使用 CsvToBeanBuilder 将 CSV 数据转换为 Java 对象列表
            List<AlarmRecordResultCsv> list = new CsvToBeanBuilder<AlarmRecordResultCsv>(reader)
                    .withSkipLines(1)
                    .withMappingStrategy(strategy)
                    .build()
                    .parse();


            //分批插入
            int total = list.size();
            System.out.println("=="+total);
            int batch = 2000;
            int times = total/batch;
            for (int i=0;i<=times;i++) {
                if ( (i+1)*batch >= total) {
                    List<AlarmRecordResultCsv> subList = list.subList(i*batch,total);
                    alarmRecordResultCsvDao.insertBatch(subList);
                    System.out.println("===========第"+String.valueOf(i)+"批");
                } else {
                    List<AlarmRecordResultCsv> subList = list.subList(i*batch,(i+1)*batch);
                    alarmRecordResultCsvDao.insertBatch(subList);
                    System.out.println("===========第"+String.valueOf(i)+"批");
                }

            }

//            bpNoRealTimePerformanceMainMapper.insertBatch(list);
        }  catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }
    }
    @Autowired
    private SafeInterfaceService safeInterfaceService;
    @Autowired
    private AlarmRecordResultService alarmRecordResultService;


    @Autowired
    private AlarmRecordResultDao alarmRecordResultDao;

    private final ConcurrentHashMap<Long, AlarmRecordResult> processedResults = new ConcurrentHashMap<>();

    private final Object lock = new Object();


    }
