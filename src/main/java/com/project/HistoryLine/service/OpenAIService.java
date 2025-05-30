package com.project.HistoryLine.service;

import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.model.LanguageCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAIService {
    private static final String QUERY_SYSTEM_MESSAGE = """
            Ti verrà inviato un wikitesto contenente le informazioni di un personaggio storico, in un unico prompt / messaggio.
            Da questo wikitesto, devi creare un array di JSON che segua RIGOROSAMENTE lo schema di questa classe:
            {
                  "eventName": "exampleEventName",
                  "eventDescription": "exampleEventDescription",
                  "eventDate": "0001-01-01",
                  "isBeforeChrist": false
            }
            LA DATA DEV'ESSERE SEMPRE NEL FORMATO "yyyy-mm-dd"!
            Aggiungi, nel caso di anni di lunghezza inferiori a 4 cifre, gli zeri 0 come placeholder.
            Inoltre, specifica sempre i mesi e i giorni. Se non riesci a specificarli, indicali sempre con il primo giorno del mese di gennaio.
            Esempio: Caduta di Roma: 0476-01-01
            Il campo "isBeforeChrist" è un campo booleano che dev'essere a TRUE se la data è AVANTI CRISTO, altrimenti FALSE se è DOPO CRISTO. POPOLALO SEMPRE.
            Prima di popolarlo, tuttavia, verifica SEMPRE se il valore che gli assegni è coerente con il personaggio ricercato, in base alle tue informazioni.
            Devi indicare se la data è b.C o a.C soltanto tramite il campo "isBeforeChrist". NON indicarlo in "eventDate".
            Tutte le infomazioni che utilizzerai per compilare l'array di JSON dovranno essere ottenute ESCLUSIVAMENTE dal wikitesto
            Devi indicare quindi gli eventi principali accaduti nella vita del personaggio storico.
            L'array deve contenere quindi tutti gli avvenimenti più importanti e indicativi di tale personaggio storico!
            Devi inviarmi SOLTANTO L'ARRAY!
            Sii il più completo e dettagliato possibile sugli eventi della vita del personaggio storico.
            """;
    private static final String QUERY_SYSTEM_MESSAGE_EN = """
            You will be sent a wikitext containing information about a historical figure, in a single prompt/message.
            From this wikitext, you must create a JSON array that RIGOROUSLY follows the pattern of this class:
            {
             "eventName": "exampleEventName",
             "eventDescription": "exampleEventDescription",
             "eventDate": "0001-01-01",
             "isBeforeChrist": false
            }
            THE DATE MUST ALWAYS BE IN THE FORMAT "yyyy-mm-dd"!
            Add, in the case of years less than 4 digits in length, zeros 0 as a placeholder.
            Also, always specify months and days. If you can't specify them, always indicate them with the first day of the January month.
            Example: Fall of Rome: 0476-01-01
            The "isBeforeChrist" is a boolean field that must be at TRUE if the date is BEFORE CHRIST, otherwise FALSE if it is AFTER CHRIST. ALWAYS POPULATE IT.
            Before you populate it, ALWAYS verify to see if the value you assign to it is consistent with the character you are looking for, based on your information.
            You must indicate whether the date is b.C or a.C only via the “isBeforeChrist” field. Do NOT indicate it in “eventDate”.
            All the information you will use to compile the JSON array must be obtained EXCLUSIVELY from the wikitext
            You must then indicate the main events that happened in the life of the historical figure.
            The array must then contain all the most important and indicative events of that historical figure!
            You must send me ONLY the ARRAY!
            Be as complete and detailed as possible about the events in the historical character's life.
            """;
    private static final String QUERY_SYSTEM_DESC_IT = """
            Riceverai la lista degli eventi più importanti della vita di un personaggio storico.
            Realizza un riepilogo testuale esplicativo della sua vita. Devi ritornare soltanto una stringa testuale, senza nessun carattere speciale o di markup (come \\n)
            """;
    private static final String QUERY_SYSTEM_DESC_EN = """
            Receive a list of the most important events in the life of a historical figure.
            Make an explanatory textual summary of his life. You need to return only a textual string, without any special or markup characters (such as \\n)
            """;

    private final ChatClient chatClient;

    public OpenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateOutput(LanguageCache language, String message) {
        return chatClient.prompt()
                .system(language.getName().equals("italian") ? QUERY_SYSTEM_MESSAGE : QUERY_SYSTEM_MESSAGE_EN)
                .user(message)
                .call().content();
    }

    public String generateCharacterDescription(String eventList, LanguageCache language) {
        return chatClient.prompt()
                .system(language.getName().equals("italian") ? QUERY_SYSTEM_DESC_IT : QUERY_SYSTEM_DESC_EN )
                .user(eventList)
                .call().content();
    }

}
