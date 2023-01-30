package dev.dashaun.client.ai.forefront.cli;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ForeFrontClient {
    @PostExchange("/summarize")
    String summarize(@RequestBody SummarizeRequest summarizeRequest);
}
