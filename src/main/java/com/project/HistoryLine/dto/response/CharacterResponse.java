package com.project.HistoryLine.dto.response;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterResponse {

    private String characterName;

    private CharacterDescriptionResp characterDescription;

    private List<CharacterEventsDTO> characterEventDTOS;

}
