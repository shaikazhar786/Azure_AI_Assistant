package com.yourcompany.jmeter.azureai;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import org.apache.jmeter.util.JMeterUtils;

import java.util.ArrayList;
import java.util.List;

public class AzureOpenAIService {
    
    public static String getChatResponse(String prompt) {
        // Read from jmeter.properties or user.properties
        String endpoint = JMeterUtils.getProperty("azure.openai.endpoint");
        String apiKey = JMeterUtils.getProperty("azure.openai.key");
        String deploymentName = JMeterUtils.getProperty("azure.openai.deployment"); 

        if (apiKey == null || endpoint == null || deploymentName == null) {
            return "Configuration Error: Please add 'azure.openai.endpoint', 'azure.openai.key', and 'azure.openai.deployment' to your jmeter.properties file.";
        }

        try {
            OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(apiKey))
                .buildClient();

            List<ChatRequestMessage> chatMessages = new ArrayList<>();
            chatMessages.add(new ChatRequestSystemMessage("You are an expert JMeter performance testing assistant. Keep your answers technical, concise, and focused on JMeter best practices."));
            chatMessages.add(new ChatRequestUserMessage(prompt));

            ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, options);

            return chatCompletions.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return "Azure API Error: " + e.getMessage();
        }
    }
}