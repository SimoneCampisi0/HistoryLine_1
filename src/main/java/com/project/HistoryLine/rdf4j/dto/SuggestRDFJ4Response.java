package com.project.HistoryLine.rdf4j.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestRDFJ4Response {

    private String item;

    private String itemLabel;

    private String itemDescription;

    private String itemImageUrl;

}
