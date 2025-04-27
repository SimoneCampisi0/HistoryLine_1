package com.project.HistoryLine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAIService {

    private static final String QUERY_SYSTEM_MESSAGE = """
            Ti verrà inviato un wikitesto contenente le informazioni di un personaggio storico, in un unico prompt / messaggio.s
            Da questo wikitesto, devi creare un array di JSON che segua lo schema di questa classe:
            public class CharacterEvents {
                private String eventName;
                private String eventDescription;
                // Data nel formato "yyyy-MM-dd"
                private Date eventDate;
            }
            LA DATA DEV'ESSERE SEMPRE NEL FORMATO "yyyy-mm-dd"! Aggiungi, nel caso di anni di lunghezza inferiori a 4 cifre, gli zeri come placeholder.
            Inoltre, specifica sempre i mesi e i giorni. Se non riesci a specificarli, indicali sempre con il primo giorno del mese di gennario.
            Esempio: Caduta di Roma: 0476-01-01
            Tutte le infomazioni che utilizzerai per compilare l'array di JSON dovranno essere ottenute ESCLUSIVAMENTE dal wikitesto
            Devi indicare quindi gli eventi principali accaduti nella vita del personaggio storico.
            L'array deve contenere quindi tutti gli avvenimenti più importanti e indicativi di tale personaggio storico!
            Devi inviarmi SOLTANTO L'ARRAY!
            Sii il più completo e dettagliato possibile sugli eventi della vita del personaggio storico.
            """;

    private final ChatClient chatClient;

    public OpenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateOutput(String message) {
        return chatClient.prompt()
                .system(QUERY_SYSTEM_MESSAGE)
                .user(message)
                .call().content();
    }

}
