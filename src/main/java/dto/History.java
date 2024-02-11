package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class History {
    private int id;
    private String lat;
    private String lnt;
    private String search_dttm;
}

