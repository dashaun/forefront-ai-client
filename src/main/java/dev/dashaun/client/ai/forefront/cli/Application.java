package dev.dashaun.client.ai.forefront.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(ConfigProps.class)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@Component
class CustomPromptProvider implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("forefront.ai:>",
				AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
	}

}