package com.project.HistoryLine.dto.response;

import com.project.HistoryLine.rdf4j.dto.SuggestRDFJ4Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WikimediaResponse {

    private String searchedKeyword;

    private List<SuggestRDFJ4Response> items;

    private Integer totalElements;

}
