package com.example.resumeparser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/***
 * @title UniversityConfig
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/27 15:35
 **/
@Configuration
public class UniversityConfig {
    @Bean
    public HashMap<String, Set<String>> universityTagsMap() {
        HashMap<String, Set<String>> universityTags = new HashMap<>();
        addTags(universityTags, "清华大学", "985", "211");
        addTags(universityTags, "北京大学", "985", "211");
        addTags(universityTags, "中国科学技术大学", "985", "211");
        addTags(universityTags, "复旦大学", "985", "211");
        addTags(universityTags, "中国人民大学", "985", "211");
        addTags(universityTags, "上海交通大学", "985", "211");
        addTags(universityTags, "南京大学", "985", "211");
        addTags(universityTags, "同济大学", "985", "211");
        addTags(universityTags, "浙江大学", "985", "211");
        addTags(universityTags, "南开大学", "985", "211");
        addTags(universityTags, "北京航空航天大学", "985", "211");
        addTags(universityTags, "北京师范大学", "985", "211");
        addTags(universityTags, "武汉大学", "985", "211");
        addTags(universityTags, "西安交通大学", "985", "211");
        addTags(universityTags, "天津大学", "985", "211");
        addTags(universityTags, "华中科技大学", "985", "211");
        addTags(universityTags, "北京理工大学", "985", "211");
        addTags(universityTags, "东南大学", "985", "211");
        addTags(universityTags, "中山大学", "985", "211");
        addTags(universityTags, "华东师范大学", "985", "211");
        addTags(universityTags, "哈尔滨工业大学", "985", "211");
        addTags(universityTags, "厦门大学", "985", "211");
        addTags(universityTags, "西北工业大学", "985", "211");
        addTags(universityTags, "中南大学", "985", "211");
        addTags(universityTags, "大连理工大学", "985", "211");
        addTags(universityTags, "四川大学", "985", "211");
        addTags(universityTags, "电子科技大学", "985", "211");
        addTags(universityTags, "华南理工大学", "985", "211");
        addTags(universityTags, "吉林大学", "985", "211");
        addTags(universityTags, "湖南大学", "985", "211");
        addTags(universityTags, "重庆大学", "985", "211");
        addTags(universityTags, "山东大学", "985", "211");
        addTags(universityTags, "中国农业大学", "985", "211");
        addTags(universityTags, "中国海洋大学", "985", "211");
        addTags(universityTags, "中央民族大学", "985", "211");
        addTags(universityTags, "东北大学", "985", "211");
        addTags(universityTags, "兰州大学", "985", "211");
        addTags(universityTags, "西北农林科技大学", "985", "211");
        addTags(universityTags, "国防科技大学", "985", "211");
        addTags(universityTags, "上海财经大学", "211");
        addTags(universityTags, "中央财经大学", "211");
        addTags(universityTags, "北京外国语大学", "211");
        addTags(universityTags, "中国政法大学", "211");
        addTags(universityTags, "北京邮电大学", "211");
        addTags(universityTags, "上海外国语大学", "211");
        addTags(universityTags, "西南财经大学", "211");
        addTags(universityTags, "中国传媒大学", "211");
        addTags(universityTags, "中南财经政法大学", "211");
        addTags(universityTags, "南京航空航天大学", "211");
        addTags(universityTags, "北京科技大学", "211");
        addTags(universityTags, "北京交通大学", "211");
        addTags(universityTags, "华东理工大学", "211");
        addTags(universityTags, "西安电子科技大学", "211");
        addTags(universityTags, "天津医科大学", "211");
        addTags(universityTags, "南京理工大学", "211");
        addTags(universityTags, "华中师范大学", "211");
        addTags(universityTags, "哈尔滨工程大学", "211");
        addTags(universityTags, "中央民族大学", "211");
        addTags(universityTags, "华北电力大学", "211");
        addTags(universityTags, "北京中医药大学", "211");
        addTags(universityTags, "暨南大学", "211");
        addTags(universityTags, "苏州大学", "211");
        addTags(universityTags, "武汉理工大学", "211");
        addTags(universityTags, "中国药科大学", "211");
        addTags(universityTags, "东华大学", "211");
        addTags(universityTags, "河海大学", "211");
        addTags(universityTags, "北京林业大学", "211");
        addTags(universityTags, "河北工业大学", "211");
        addTags(universityTags, "北京工业大学", "211");
        addTags(universityTags, "江南大学", "211");
        addTags(universityTags, "北京化工大学", "211");
        addTags(universityTags, "西南交通大学", "211");
        addTags(universityTags, "上海大学", "211");
        addTags(universityTags, "南京师范大学", "211");
        addTags(universityTags, "中国地质大学", "211");
        addTags(universityTags, "西北大学", "211");
        addTags(universityTags, "东北师范大学", "211");
        addTags(universityTags, "长安大学", "211");
        addTags(universityTags, "华中农业大学", "211");
        addTags(universityTags, "合肥工业大学", "211");
        addTags(universityTags, "广西大学", "211");
        addTags(universityTags, "陕西师范大学", "211");
        addTags(universityTags, "南京农业大学", "211");
        addTags(universityTags, "湖南师范大学", "211");
        addTags(universityTags, "福州大学", "211");
        addTags(universityTags, "大连海事大学", "211");
        addTags(universityTags, "西北农林科技大学", "211");
        addTags(universityTags, "西南大学", "211");
        addTags(universityTags, "中国矿业大学", "211");
        addTags(universityTags, "云南大学", "211");
        addTags(universityTags, "太原理工大学", "211");
        addTags(universityTags, "华南师范大学", "211");
        addTags(universityTags, "北京体育大学", "211");
        addTags(universityTags, "中国石油大学", "211");
        addTags(universityTags, "安徽大学", "211");
        addTags(universityTags, "东北林业大学", "211");
        addTags(universityTags, "东北农业大学", "211");
        addTags(universityTags, "辽宁大学", "211");
        addTags(universityTags, "南昌大学", "211");
        addTags(universityTags, "延边大学", "211");
        addTags(universityTags, "内蒙古大学", "211");
        addTags(universityTags, "四川农业大学", "211");
        addTags(universityTags, "海南大学", "211");
        addTags(universityTags, "贵州大学", "211");
        addTags(universityTags, "郑州大学", "211");
        addTags(universityTags, "新疆大学", "211");
        addTags(universityTags, "宁夏大学", "211");
        addTags(universityTags, "青海大学", "211");
        addTags(universityTags, "中央音乐学院", "211");
        addTags(universityTags, "第二军医大学", "211");
        addTags(universityTags, "第四军医大学", "211");
        addTags(universityTags, "西藏大学", "211");
        addTags(universityTags, "海军军医大学", "211");
        addTags(universityTags, "空军军医大学", "211");
        return universityTags;
    }

    private void addTags(HashMap<String, Set<String>> map, String university, String... tags) {
        Set<String> tagSet = new HashSet<>(Arrays.asList(tags));
        map.put(university, tagSet);
    }
}
