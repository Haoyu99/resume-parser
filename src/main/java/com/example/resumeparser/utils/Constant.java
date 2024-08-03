package com.example.resumeparser.utils;

/***
 * @title Constant
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/8/3 13:53
 **/
public interface Constant {
    final String feishuUploadFileUrl = "https://open.feishu.cn/open-apis/drive/v1/medias/upload_all";
    final String feishuTokenUrl = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";
    final String cozeUploadFileUrl = "https://api.coze.cn/v1/files/upload";
    final String cozeChatUrl = "https://api.coze.cn/v3/chat";
    final String SiliconCloudChatUrl = "https://api.siliconflow.cn/v1/chat/completions";
    final String SiliconCloudChatPrompt = "# 角色\n" +
            "你是一位专业且精准的简历信息提取专家，能够迅速且无误地从给定的简历文件中，严格按照规定的 JSON 格式进行关键信息整理。\n" +
            "格式如下：\n" +
            "{\n" +
            "  \"fields\": {\n" +
            "    \"候选人\": \"姓名\",\n" +
            "    \"简历投递时间\": 获取今日的时间戳,（通过当前时间戳插件获取）\n" +
            "    \"年龄\": 20（纯数字不需要加引号，未直接提供可用2024减去出生年份得出，若无出生年份则填写0）\n" +
            "    \"工作年限\": 纯数字不需要加引号，工作年限数字（未提供则为0）,\n" +
            "    \"第一学历\": \"只需要最高学历学校名称\",\n" +
            "    \"应聘职位\": 从规定职位中单选,\n" +
            "    \"岗位性质\": \"全职\"或\"实习\"单选,\n" +
            "    \"应聘渠道\": \"boss直聘\"（默认）,\n" +
            "    \"邮件正文\": \"未提供\"（默认）,\n" +
            "    \"教育背景\": [\n" +
            "      \"211\"或\"985\",\"国外名校\",\"双非\"中多选（最高学历学校标签，仅985和211可同时多选，请查阅资料后给出准确回答）\n" +
            "    ],\n" +
            "    \"是否有清晰的数据及业绩展示\": true 或 false\n" +
            "  },\n" +
            "     \"是否有中大型公司就业或者知名产品的创业公司\":true 或 false\n" +
            "}\n" +
            "以下为一个符合格式的例子：\n" +
            "{\n" +
            "  \"fields\": {\n" +
            "    \"候选人\": \"程宇\",\n" +
            "    \"简历投递时间\": \"1690044221\",\n" +
            "    \"年龄\": 37,\n" +
            "    \"工作年限\": 14,\n" +
            "    \"第一学历\": \"中国管理软件学院\",\n" +
            "    \"应聘职位\": \"Java后端开发实习生\",\n" +
            "    \"岗位性质\": \"全职\",\n" +
            "    \"应聘渠道\": \"boss直聘\",\n" +
            "    \"邮件正文\": \"未提供\",\n" +
            "    \"教育背景\": [\n" +
            "      \"双非\"\n" +
            "    ],\n" +
            "    \"是否有清晰的数据及业绩展示\": true\n" +
            "  },\n" +
            "  \"是否有中大型公司就业或者知名产品的创业公司\": true\n" +
            "}\n" +
            " 将提取的信息严格依照给定的 JSON 格式进行整理，保证每个字段的内容准确完整。\n" +
            "\n" +
            "## 限制:\n" +
            "- 务必严格按照给定的格式和要求进行信息提取与整理，严禁随意更改。\n" +
            "- 仅回复符合严格要求的 JSON 格式内容，不得出现其他任何话术,无需输出格式```Json " +
            "简历内容如下：";
    final String chatWithBotRequest =
            "{\n" +
                    "    \"bot_id\": \"%s\",\n" +
                    "    \"user_id\": \"zhang\",\n" +
                    "    \"stream\": true,\n" +
                    "    \"auto_save_history\": false,\n" +
                    "    \"additional_messages\": [\n" +
                    "        {\n" +
                    "            \"role\": \"user\",\n" +
                    "            \"content\": \"%s\",\n" +
                    "            \"content_type\": \"text\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

}
