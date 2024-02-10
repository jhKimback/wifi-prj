package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class History {
    private String id;
    private String lat;
    private String lnt;
    private String search_dttm;
}

