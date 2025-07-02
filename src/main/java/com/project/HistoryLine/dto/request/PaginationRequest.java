package com.project.HistoryLine.dto.request;

import lombok.Getter;

@Getter
public class PaginationRequest {

    /**
     * Numero di risultati mostrati per query
     */
    private Integer limit;

    /**
     * Numero di pagina corrente
     */
    private Integer offset;

}
