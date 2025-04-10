package com.project.HistoryLine.config;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RDF4JRepositoryConfiguration {

    // L'URL dell'endpoint SPARQL di Wikidata
    @Value("${rdf4j.spring.repository.remote.manager-url}")
    private String sparqlEndpoint;

    // Il nome "wikidata" è riportato nelle properties, ma in questo caso non è strettamente necessario
    // perché SPARQLRepository si configura direttamente sull'URL dell'endpoint.
    @Value("${rdf4j.spring.repository.remote.name}")
    private String repositoryName;

    @Bean
    public Repository repository() {
        SPARQLRepository repository = new SPARQLRepository(sparqlEndpoint);
        return repository;
    }
}