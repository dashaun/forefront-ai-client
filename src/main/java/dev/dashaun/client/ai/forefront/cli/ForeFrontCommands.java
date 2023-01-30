package dev.dashaun.client.ai.forefront.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.ComponentFlow.ComponentFlowResult;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ForeFrontCommands extends AbstractShellComponent {

    private final ComponentFlow.Builder componentFlowBuilder;
    
    private final ForeFrontClient foreFrontClient;

    public ForeFrontCommands(ComponentFlow.Builder componentFlowBuilder, ForeFrontClient foreFrontClient) {
        this.componentFlowBuilder = componentFlowBuilder;
        this.foreFrontClient = foreFrontClient;
    }
    

    @ShellMethod(value = "summarize text")
    public String summarizeText() throws IOException {
        Map<String, String> compressionLevelSelect = new HashMap<>();
        compressionLevelSelect.put("1", "1");
        compressionLevelSelect.put("2", "2");
        compressionLevelSelect.put("3", "3");
        compressionLevelSelect.put("4", "4");
        compressionLevelSelect.put("5", "5");


        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withPathInput("textFileToSummarize")
                .name("Text file to summarize")
                .and()
                .withSingleItemSelector("compression")
                .name("Compression Level")
                .selectItems(compressionLevelSelect)
                .and()
                .build();

        ComponentFlowResult result = flow.run();

        return summarizeThisFilePath(result.getContext().get("textFileToSummarize").toString(), Integer.parseInt(result.getContext().get("compression").toString()));
        
    }
    
    public String summarizeThisFilePath(String path, int compression) throws IOException {
        String text = readFileToString(path);
        SummarizeRequest summarizeRequest = new SummarizeRequest(text, compression);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("target/summarizeRequest.json"), summarizeRequest);
        String summary  = foreFrontClient.summarize(summarizeRequest);
        return "Summary: " + System.getProperty("line.separator") + summary;
    }

    public static String readFileToString(String inputPath) throws IOException {
        Path path = Paths.get(inputPath);
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }

}
