package com.clerk.register.intelligence;

import com.clerk.register.data.responses.ValidationResponse;
import com.clerk.register.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ClerkIntelligence {

    private final boolean enabled;

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    private final Advisor knowledge;

    public ClerkIntelligence(ClerkIntelligenceProperties properties,
                             Optional<ChatClient> chatClient,
                             Optional<VectorStore> vectorStore) {
        this.enabled = properties.enabled();
        this.chatClient = enabled ? chatClient.orElseThrow() : null;
        this.vectorStore = enabled ? vectorStore.orElseThrow() : null;
        this.knowledge = enabled ? productKnowledge(this.vectorStore) : null;
    }

    public ValidationResponse proofread(String description) {
        if (!enabled) {
            return new ValidationResponse(true, description);
        }

        return chatClient.prompt()
                .user(user -> user.text(ClerkPrompts.VERIFY_DESCRIPTION).param("description", String.valueOf(description)))
                .call()
                .entity(ValidationResponse.class);
    }

    public boolean verifyDescription(Product product) {
        if (!enabled) {
            return product.isChecked();
        }

        return chatClient.prompt()
                .advisors(knowledge)
                .user(user -> user.text("{listing}").param("listing", listing(product)))
                .call()
                .entity(ValidationResponse.class)
                .valid();
    }

    private static Advisor productKnowledge(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .promptTemplate(new PromptTemplate(ClerkPrompts.VERIFY_DESCRIPTION))
                        .emptyContextPromptTemplate(new PromptTemplate(ClerkPrompts.VERIFY_DESCRIPTION))
                        .build()
                )
                .build();
    }

    private static String listing(Product product) {
        return product.getName() + "\n" + product.getDescription();
    }

}
