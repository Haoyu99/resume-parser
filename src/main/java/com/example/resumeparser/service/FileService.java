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
    // 上传文档到Coze获取到文件Id
    String uploadCoze(File file);

    // 获取Coze bot 解析的Json
    String getResumeJson(String cozeFileId, String feishuFileToken);

    // 增加一条飞书记录
    String uploadFeiShuAndAddRecord(File file, String rowJson);

    // 获取pdf转txt结果
    String getResumeStr(File file);

    String chatWithBot(String resumeStr);
}
