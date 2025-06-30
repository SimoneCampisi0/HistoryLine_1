package com.project.HistoryLine.rdf4j;

import com.project.HistoryLine.rdf4j.dto.SuggestRDFJ4Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.spring.dao.RDF4JDao;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class CharacterDao extends RDF4JDao {

    static abstract class QUERY_KEYS {
        public static final String FIND_CHARACTERS_SUGGEST = "find-characters-suggest";
        public static final String FIND_CHARACTERS_SUGGEST_IT = "find-characters-suggest-it";
    }

    private static final Map<String, String> queryMap = Map.of("italian","""
                 SELECT ?item ?itemLabel ?itemDescription (SAMPLE(?article_) AS ?article) (SAMPLE(?imageUrl_) AS ?imageUrl)
                     WHERE {
                       SERVICE wikibase:mwapi {
                         bd:serviceParam wikibase:api "EntitySearch" ;
                                         wikibase:endpoint "www.wikidata.org" ;
                                         mwapi:search ?searchItem ;
                                         mwapi:language "it" .
                         ?item wikibase:apiOutputItem mwapi:item .
                       }
                    
                       ?item wdt:P31 wd:Q5 .
                       ?item wdt:P570 ?dateOfDeath .
                    
                       OPTIONAL {
                         ?item wdt:P18 ?image .
                         BIND(
                           CONCAT("https://commons.wikimedia.org/wiki/Special:FilePath/",\s
                                  REPLACE(STR(?image), ".*Special:FilePath/", "")
                           ) AS ?imageUrl_
                         )
                       }
                    
                       ?article_ schema:about ?item ;
                                 schema:inLanguage "it" ;
                                 schema:isPartOf <https://it.wikipedia.org/> .
                    
                       ?item rdfs:label ?itemLabel .
                       FILTER (LANG(?itemLabel) = "it")
                    
                       ?item schema:description ?itemDescription .
                       FILTER (LANG(?itemDescription) = "it")
                    
                       FILTER NOT EXISTS { ?item wdt:P31 wd:Q4167410 }
                     }
                     GROUP BY ?item ?itemLabel ?itemDescription
                     LIMIT 10
                     OFFSET 0
                    
                """,
            "english",
            """
                 SELECT ?item ?itemLabel ?itemDescription (SAMPLE(?article_) AS ?article) (SAMPLE(?imageUrl_) AS ?imageUrl)
                  WHERE {
                    SERVICE wikibase:mwapi {
                      bd:serviceParam wikibase:api "EntitySearch" ;
                                      wikibase:endpoint "www.wikidata.org" ;
                                      mwapi:search ?searchItem ;
                                      mwapi:language "en" .
                      ?item wikibase:apiOutputItem mwapi:item .
                    }
        
                    ?item wdt:P31 wd:Q5 .
                    ?item wdt:P570 ?dateOfDeath .
        
                    OPTIONAL {
                      ?item wdt:P18 ?image .
                      BIND(
                        CONCAT("https://commons.wikimedia.org/wiki/Special:FilePath/",\s
                               REPLACE(STR(?image), ".*Special:FilePath/", "")
                        ) AS ?imageUrl_
                      )
                    }
        
                    ?article_ schema:about ?item ;
                              schema:inLanguage "en" ;
                              schema:isPartOf <https://en.wikipedia.org/> .
        
                    ?item rdfs:label ?itemLabel .
                    FILTER (LANG(?itemLabel) = "en")
        
                    ?item schema:description ?itemDescription .
                    FILTER (LANG(?itemDescription) = "en")
        
                    FILTER NOT EXISTS { ?item wdt:P31 wd:Q4167410 }
                  }
                  GROUP BY ?item ?itemLabel ?itemDescription
                  LIMIT 10
                  OFFSET 0
                """
            );

    public CharacterDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    private SuggestRDFJ4Response mapBindingSetToResponse(BindingSet binding) {
        log.info("binding {}", binding);
        if(binding.getValue("article") == null) {
            return null;
        }
        SuggestRDFJ4Response response = SuggestRDFJ4Response.builder()
                .item(binding.getValue("article").stringValue())
                .itemLabel(binding.getValue("itemLabel").stringValue())
                .itemDescription(binding.getValue("itemDescription").stringValue())
                .itemImageUrl(binding.getValue("imageUrl") != null ? binding.getValue("imageUrl").stringValue() : "")
                .build();
        log.debug("Suggest response: {}", response);
        return response;
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer preparer) {
        return preparer.forKey(QUERY_KEYS.FIND_CHARACTERS_SUGGEST)
                .supplySparql(queryMap.get("english"))
                .forKey(QUERY_KEYS.FIND_CHARACTERS_SUGGEST_IT)
                .supplySparql(queryMap.get("italian"));
    }

    public List<SuggestRDFJ4Response> executeFindCharacterQuery(String searchItem, String languageName) {
        searchItem = searchItem.toLowerCase();
        log.info("searchItem: {}", searchItem);
        List<SuggestRDFJ4Response> suggestList = getNamedTupleQuery(languageName.equals("english") ? QUERY_KEYS.FIND_CHARACTERS_SUGGEST : QUERY_KEYS.FIND_CHARACTERS_SUGGEST_IT)
                .withBinding("searchItem", searchItem)
                .evaluateAndConvert()
                .toStream()
                .map(this::mapBindingSetToResponse)
                .filter(Objects::nonNull)
                .toList();
        log.info("suggestList {}", suggestList);
        return suggestList;
    }

}
