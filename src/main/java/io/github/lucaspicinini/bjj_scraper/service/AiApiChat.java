package io.github.lucaspicinini.bjj_scraper.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;
import io.github.lucaspicinini.bjj_scraper.utils.AiApiConfigs;

public class AiApiChat {
    public static String getResponse(String text) {
        ChatLanguageModel model = MistralAiChatModel.builder()
                .modelName(MistralAiChatModelName.MISTRAL_SMALL_LATEST)
                .apiKey(AiApiConfigs.MISTRAL_AI_KEY)
                .build();

        String response = model.generate("Traduza para pt-br e me retorne só a tradução: " + text);
        return response;
    }
}