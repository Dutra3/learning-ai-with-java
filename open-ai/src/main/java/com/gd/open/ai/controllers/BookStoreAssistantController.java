package com.gd.open.ai.controllers;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookStoreAssistantController {

    private OpenAiChatModel openAiChatModel;

    public BookStoreAssistantController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/information")
    public String bookStoreChat(@RequestParam(value = "message", defaultValue = "Quais sao os livros best sellers de algoritimos de dados?") String message) {
        return openAiChatModel.call(message);
    }

    @GetMapping("/information/prompt")
    public ChatResponse bookStoreChatWithPrompt(@RequestParam(value = "message", defaultValue = "Quais sao os livros best sellers de algoritimos de dados?") String message) {
        return openAiChatModel.call(new Prompt(message));
    }

    @GetMapping("/reviews")
    public String bookStoreChatReview(@RequestParam(value = "book", defaultValue = "Dom Quixote") String book) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Por favor, me forneca um breve resumo do livro {book}
                e tambem a biografia do seu autor.
                """);
        promptTemplate.add("book", book);

        return openAiChatModel.call(promptTemplate.create()).getResult().getOutput().getText();
    }

    @GetMapping("/stream/information")
    public Flux<String> bookStoreChatStream(@RequestParam(value = "message", defaultValue = "Quais sao os livros best sellers de algoritimos de dados?") String message) {
        return openAiChatModel.stream(message);
    }

    @GetMapping("/stream/information")
    public Flux<ChatResponse> bookStoreChatStreamWithPrompt(@RequestParam(value = "message", defaultValue = "Quais sao os livros best sellers de algoritimos de dados?") String message) {
        return openAiChatModel.stream(new Prompt(message));
    }
}
