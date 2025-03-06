package com.project.HistoryLine.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchItem {

    private String result;

    private String extraOption;

    private String link;

}
