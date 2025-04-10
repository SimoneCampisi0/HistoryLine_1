package com.project.HistoryLine.config;

import org.eclipse.rdf4j.spring.RDF4JConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ RDF4JConfig.class, RDF4JRepositoryConfiguration.class })
public class AppConfigRDF4J {

}