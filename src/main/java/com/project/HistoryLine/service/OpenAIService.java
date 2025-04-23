package com.project.HistoryLine.service;

import com.project.HistoryLine.dto.CharacterEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OpenAIService {

    private static final String QUERY_SYSTEM_MESSAGE = """
                Ti verrà inviato un wikitesto contenente le informazioni di un personaggio storico, in un unico prompt / messaggio.s
                Da questo wikitesto, devi creare un array di JSON che segua lo schema di questa classe:s
                public class CharacterEvents {
                    private String eventName;
                    private String eventDescription;
                    // Data nel formato "yyyy-MM-dd"
                    private Date eventDate;
                }
                Tutte le infomazioni che utilizzerai per compilare l'array di JSON dovranno essere ottenute ESCLUSIVAMENTE dal wikitesto
                Devi indicare quindi gli eventi principali accaduti nella vita del personaggio storico.
                L'array deve contenere quindi tutti gli avvenimenti più importanti e indicativi di tale personaggio storico!
                Devi inviarmi SOLTANTO L'ARRAY!
            """;

    private final ChatClient chatClient;

    public OpenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<CharacterEvents> generateOutput(String message) {
        return chatClient.prompt()
                .system(QUERY_SYSTEM_MESSAGE)
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CharacterEvents>>(){});
    }

}
