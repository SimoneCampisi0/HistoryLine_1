package com.project.HistoryLine.rdf4j;

import com.project.HistoryLine.rdf4j.dto.SuggestRDFJ4Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.spring.dao.RDF4JDao;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CharacterDao extends RDF4JDao {

    static abstract class QUERY_KEYS {
        public static final String FIND_CHARACTERS_SUGGEST = "find-characters-suggest";
    }

    public CharacterDao(RDF4JTemplate rdf4JTemplate) {
        super(rdf4JTemplate);
    }

    private SuggestRDFJ4Response mapBindingSetToResponse(BindingSet binding) {
        return SuggestRDFJ4Response.builder()
                .item(binding.getValue("article").stringValue())
                .itemLabel(binding.getValue("itemLabel").stringValue())
                .build();
    }

    @Override
    protected NamedSparqlSupplierPreparer prepareNamedSparqlSuppliers(NamedSparqlSupplierPreparer preparer) {
        String query = """
                SELECT ?item ?itemLabel ?article
                WHERE {
                  SERVICE wikibase:mwapi {
                    bd:serviceParam wikibase:api "EntitySearch".
                    bd:serviceParam wikibase:endpoint "www.wikidata.org".
                    bd:serviceParam mwapi:search ?searchItem.
                    bd:serviceParam mwapi:language "it".
                    ?item wikibase:apiOutputItem mwapi:item.
                  }
                  ?item wdt:P31 wd:Q5.
                  OPTIONAL {
                    ?article schema:about ?item;
                             schema:inLanguage "it";
                             schema:isPartOf <https://it.wikipedia.org/>.
                  }
                  SERVICE wikibase:label { bd:serviceParam wikibase:language "it". }
                }
                LIMIT 10
                """;
        return preparer.forKey(QUERY_KEYS.FIND_CHARACTERS_SUGGEST)
                .supplySparql(query);
    }

    public List<SuggestRDFJ4Response> executeFindCharacterQuery(String searchItem) {
        List<SuggestRDFJ4Response> suggestList = getNamedTupleQuery(QUERY_KEYS.FIND_CHARACTERS_SUGGEST)
                .withBinding("searchItem", searchItem)
                .evaluateAndConvert()
                .toStream()
                .map(this::mapBindingSetToResponse)
                .toList();
        log.info("suggestList {}", suggestList);
        return suggestList;
    }

}
