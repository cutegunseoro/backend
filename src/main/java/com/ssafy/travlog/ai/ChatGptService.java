package com.ssafy.travlog.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatGptService {

	private final ChatClient chatClient;

	public ChatGptService(OpenAiChatModel openAiChatModel) {
		this.chatClient = ChatClient.builder(openAiChatModel).build();
	}

	public String getChatResponse(String prompt) {
		return chatClient.prompt()
			.user(prompt)
			.call()
			.content();
	}
}