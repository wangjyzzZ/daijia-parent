package com.atguigu.daijia.driver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tencent.cloud")
public class TencentCloudProperties {
    private String secretId;
    private String secretKey;
    private String region;
    private String bucketPrivate;
    private String persionGroupId;
}
