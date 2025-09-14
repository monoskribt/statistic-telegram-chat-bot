package com.statistictgchatbot.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.credentials")
public record AWSProps(String accessKey, String secretKey, String region) {
}
