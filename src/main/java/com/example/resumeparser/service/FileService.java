package com.example.resumeparser.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/***
 * @title FileService
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/13 0:04
 **/
public interface FileService {

    // 增加一条飞书记录
    String uploadFeiShuAndAddRecord(File file, String rowJson);

    // 获取pdf转txt结果
    String getResumeStr(File file);

    // 与Coze机器人对话
    String chatWithBot(String resumeStr);

    //
    String getResumeJsonFromLLM(String resumeStr);
}
