package dev.dashaun.client.ai.forefront.cli;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("forefront")
public record ConfigProps(String teamId, String modelKey, String platformKey) { }