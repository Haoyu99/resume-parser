package com.example.resumeparser.service.impl;

import com.example.resumeparser.config.CustomProperties;
import com.example.resumeparser.service.FileService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @title FileServiceImpl
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/13 0:07
 **/
@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final CustomProperties customProperties;

    private final String feiShuUploadFileUrl = "https://open.feishu.cn/open-apis/drive/v1/medias/upload_all";

    private final String feishuTokenUrl = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";

    // pdf2txt 相关配置
    @Value("${pdf2txt.login}")
    private String pdf2txtLoginUrl;
    @Value("${pdf2txt.api}")
    private String pdf2txtApiUrl;
    @Value("${pdf2txt.username}")
    private String pdf2txtUserName;
    @Value("${pdf2txt.password}")
    private String pdf2txtPassword;

    // coze相关配置
    @Value("${coze.token}")
    private String cozeToken;
    @Value("${coze.bot-id}")
    private String botId;
    private static final String cozeUploadFileUrl = "https://api.coze.cn/v1/files/upload";
    private final String cozeChatUrl = "https://api.coze.cn/v3/chat";

    // 飞书相关配置
    @Value("${feishu.app-id}")
    private String feishuAppId;
    @Value("${feishu.app-secret}")
    private String feishuAppSecret;
    @Value("${feishu.app-token}")
    private String feishuAppToken;
    @Value("${feishu.table-id}")
    private String feishuTableId;
    private final String chatWithBotRequest =
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
    private final WebClient webClient;
    private final HashMap<String, Set<String>> universityTags;
    @Autowired
    private RestTemplate restTemplate;


    public FileServiceImpl(CustomProperties customProperties, HashMap<String, Set<String>> universityTags) {
        this.customProperties = customProperties;
        this.universityTags = universityTags;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.coze.cn")
                .build();
    }


    private String loginAndGetCookie() {
        // 设置登录请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 设置登录请求体
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", pdf2txtUserName);
        body.add("password", pdf2txtPassword);

        // 构建 HttpEntity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        // 发送登录请求
        ResponseEntity<String> response = restTemplate.postForEntity(pdf2txtLoginUrl, requestEntity, String.class);

        // 获取 Cookie
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0);
        }
        return null;
    }

    @Override
    public String getResumeStr(File file) {
        long stratTime = System.currentTimeMillis();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", loginAndGetCookie()); // 添加 Cookie 头
        // 设置请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("fileInput", new FileSystemResource(file));
        body.add("outputFormat", "txt");
        // 构建 HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送请求
        ResponseEntity<byte[]> response = restTemplate.postForEntity(pdf2txtApiUrl, requestEntity, byte[].class);

        // 强制设置字符编码为 UTF-8
        byte[] responseBody = response.getBody();
        if (responseBody != null) {
            long endTime = System.currentTimeMillis();
            log.info("解析成功, 文件名{}, 用时{}ms", file.getName(), endTime - stratTime);
            return new String(responseBody, StandardCharsets.UTF_8);
        }
        return null;
    }

    @Override
    public String uploadCoze(File file) {
        return null;
//        try {
//            if (!file.exists()) {
//                log.error("File is empty");
//                return "File is empty";
//            }
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            headers.set("Authorization", "Bearer " + cozeBotToken);
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("file", new FileSystemResource(file));
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(cozeUploadFileUrl, requestEntity, String.class);
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                log.info("File uploaded successfully: {}", response.getBody());
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode rootNode = mapper.readTree(response.getBody());
//                String id = rootNode.path("data").path("id").asText();
//                log.info("File uploaded successfully: ID = {}", id);
//                return id;
//            } else {
//                log.error("Failed to upload file: {}", response.getStatusCode());
//                return "Failed to upload file: " + response.getStatusCode();
//            }
//        } catch (Exception e) {
//            log.error("Error occurred while uploading file", e);
//            return "Error occurred: " + e.getMessage();
//        }
    }

    @Override
    public String getResumeJson(String cozeFileId, String feishuFileToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + cozeToken);

        String requestBody = String.format(chatWithBotRequest, escapeJson(cozeFileId));


        try {
            String json = restTemplate.execute(cozeChatUrl, HttpMethod.POST, request -> {
                request.getHeaders().addAll(headers);
                OutputStream outputStream = request.getBody();
                if (outputStream != null) {
                    outputStream.write(requestBody.getBytes());
                    outputStream.flush();
                }
            }, response -> {
                try (InputStream inputStream = response.getBody();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder responseString = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseString.append(line);
                    }
                    log.info("Streamed response: {}", responseString);

//                    String cozeJson = getContentFormCozeJson(String.valueOf(responseString), feishuFileToken);
//                    log.info("json:{}", cozeJson);
//                    return cozeJson;
                    return null;
                } catch (IOException e) {
                    log.error("Error reading streamed response", e);
                    return null;
                }
            });
            return json;
        } catch (RestClientException e) {
            log.error("Error during REST call", e);
            return null;
        }
    }

    // 转义 JSON 字符串中的特殊字符
    private String escapeJson(String content) {
        StringBuilder escapedContent = new StringBuilder();
        for (char c : content.toCharArray()) {
            switch (c) {
                case '\\':
                    escapedContent.append("\\\\");
                    break;
                case '"':
                    escapedContent.append("\\\"");
                    break;
                case '\b':
                    escapedContent.append("\\b");
                    break;
                case '\f':
                    escapedContent.append("\\f");
                    break;
                case '\n':
                    escapedContent.append("\\n");
                    break;
                case '\r':
                    escapedContent.append("\\r");
                    break;
                case '\t':
                    escapedContent.append("\\t");
                    break;
                default:
                    if (Character.isISOControl(c)) {
                        // 处理控制字符
                        escapedContent.append(String.format("\\u%04x", (int) c));
                    } else {
                        // 处理其他字符
                        escapedContent.append(c);
                    }
                    break;
            }
        }
        return escapedContent.toString();
    }


    // 获取飞书api对应的token
    public String getToken() {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 构造请求体
            String requestBody = String.format("{\"app_id\": \"%s\", \"app_secret\": \"%s\"}",
                    feishuAppId, feishuAppSecret);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(feishuTokenUrl, requestEntity, String.class);

            // 处理响应
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.getBody());
                return rootNode.path("tenant_access_token").asText();
            } else {
                throw new RuntimeException("Failed to get token: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addRecord(String rowJson, String fileName, String fileToken) throws Exception {

        try {
            // 修改json内容
            String resumeJson = modifyResumeJson(rowJson, fileName, fileToken);
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());

            // 创建请求实体
            HttpEntity<String> requestEntity = new HttpEntity<>(resumeJson, headers);
            String feishuAddRecordUrl = "https://open.feishu.cn/open-apis/bitable/v1/apps/" +
                    feishuAppToken +
                    "/tables/" +
                    feishuTableId +
                    "/records";
            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(feishuAddRecordUrl, requestEntity, String.class);

            // 检查响应状态
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseJson = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseJson);
                int code = rootNode.path("code").asInt();
                if (code == 0) {
                    log.info("飞书增加记录成功，{}", fileName);
                } else {
                    log.info(responseJson);
                    throw new RuntimeException("飞书增加记录失败");
                }
                return responseJson;
            } else {
                throw new RuntimeException("Failed to add record: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public String chatWithBot(String resumeStr) {
        long startTime = System.currentTimeMillis();
        String requestBody = String.format(chatWithBotRequest, botId, escapeJson(resumeStr));
        log.info("解析简历文字字数{}", requestBody.length());
        try {
            // 发起 HTTP 请求并处理流式响应
            String responseFlux = webClient
                    .post()
                    .uri("/v3/chat")
                    .headers(headers -> {
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.set("Authorization", "Bearer " + cozeToken);
                    })
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
                    })
                    .filter(event -> "conversation.message.completed".equals(event.event()))
                    .mapNotNull(ServerSentEvent::data)
                    .next()
                    .block(); // Take the first occurrence and convert to Mono

            Map<String, Object> map = new ObjectMapper().readValue(responseFlux, Map.class);
            String rowJson = (String) map.get("content");
            long endTime = System.currentTimeMillis();
            log.info("与bot对话结果为:{}, 耗时{}ms", rowJson, endTime - startTime);
            return rowJson;

        } catch (Exception e) {
            log.error("Coze bot 对话失败，无返回结果");
            throw new RuntimeException(e);
        }
    }


    @Override
    public String uploadFeiShuAndAddRecord(File file, String rowJson) {
        if (!file.exists()) {
            log.error("文件为空");
            return "文件为空";
        }
        long startTime = System.currentTimeMillis();
        String fileName = file.getName();
        long fileSize = file.length();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", "Bearer " + getToken());

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file_name", fileName);
            body.add("parent_type", "bitable_file");
            body.add("parent_node", feishuAppToken);
            body.add("size", String.valueOf(fileSize));
            body.add("file", new FileSystemResource(file));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> updateLoadResponse = restTemplate.postForEntity(feiShuUploadFileUrl, requestEntity, String.class);

            if (updateLoadResponse.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode updateLoadRootNode = mapper.readTree(updateLoadResponse.getBody());
                String fileToken = updateLoadRootNode.path("data").path("file_token").asText();
                long endTime = System.currentTimeMillis();
                log.info("文件{}上传飞书成功，file_token为{}，用时{}ms", file.getName(), fileToken, endTime - startTime);
                // 增加一条飞书记录
                return addRecord(rowJson, fileName, fileToken);
            } else {
                log.error("文件上传飞书失败: {}", updateLoadResponse.getStatusCode());
                return "上传失败: " + updateLoadResponse.getStatusCode();
            }
        } catch (Exception e) {
            log.error("文件上传飞书或增加记录时发生错误", e);
            throw new RuntimeException("文件上传飞书或增加记录时发生错误");
        }
    }

    public String modifyResumeJson(String rowJson, String fileName, String fileToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 解析 JSON 字符串
            JsonNode rootNode = objectMapper.readTree(rowJson);
            // 获取嵌套的 fields 字段
            JsonNode fieldsNode = rootNode.get("fields");
            if (fieldsNode != null) {
                // 重置投递时间
                long timeStamp = System.currentTimeMillis();
                ((ObjectNode) fieldsNode).put("简历投递时间", timeStamp);
                // 重置岗位
                String position = getPositionFromFileName(fileName);
                ((ObjectNode) fieldsNode).put("应聘职位", position);
                // 重置岗位性质
                ((ObjectNode) fieldsNode).put("岗位性质",
                        position.contains("实习生") ? "实习" : "全职");
                // 重置应聘渠道
                ((ObjectNode) fieldsNode).put("应聘渠道", "boss直聘");
                // 重置教育北京
                String universityName = fieldsNode.path("第一学历").asText();
                Set<String> universityTag = universityTags.get(universityName);
                ArrayNode tagsArrayNode = objectMapper.createArrayNode();
                if (null == universityTag) {
                    tagsArrayNode.add("双非");
                } else {
                    for (String tag : universityTag) {
                        tagsArrayNode.add(tag);
                    }
                }
                ((ObjectNode) fieldsNode).set("教育背景", tagsArrayNode);
                // 上传简历
                ArrayNode resumeArray = objectMapper.createArrayNode();
                ObjectNode resumeNode = objectMapper.createObjectNode();
                resumeNode.put("file_token", fileToken);
                resumeArray.add(resumeNode);
                ((ObjectNode) fieldsNode).set("简历", resumeArray);
            }
            return objectMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("coze 返回结果不合规");
        }
    }

    public String getPositionFromFileName(String fileName) {
        // 定义正则表达式，匹配岗位部分
        String regex = "【(.*?)_.*?】";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);

        // 查找并提取岗位部分
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // 如果找不到匹配，返回null或其他默认值
            return null;
        }
    }
}
