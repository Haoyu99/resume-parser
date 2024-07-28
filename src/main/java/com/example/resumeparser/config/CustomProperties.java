package com.example.resumeparser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @title CustomProperties
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/28 14:24
 **/
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private Pdf2txt pdf2txt = new Pdf2txt();
    private Coze coze = new Coze();
    private Feishu feishu = new Feishu();

    // Getters and Setters

    public static class Pdf2txt {
        private String api;
        private String login;
        private String username;
        private String password;

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Coze {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class Feishu {
        private String appId;
        private String appSecret;
        private String appToken;
        private String tableId;

        // Getters and Setters

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }
    }

    public Pdf2txt getPdf2txt() {
        return pdf2txt;
    }

    public void setPdf2txt(Pdf2txt pdf2txt) {
        this.pdf2txt = pdf2txt;
    }

    public Coze getCoze() {
        return coze;
    }

    public void setCoze(Coze coze) {
        this.coze = coze;
    }

    public Feishu getFeishu() {
        return feishu;
    }

    public void setFeishu(Feishu feishu) {
        this.feishu = feishu;
    }
}
