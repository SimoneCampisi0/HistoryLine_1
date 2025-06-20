package com.project.HistoryLine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterDescriptionResp {

    private String characterName;

    private String description;

    private String bornYear;

    private String deathYear;

}
