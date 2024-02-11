package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookMark {
    private int id;
    private int group_id;
    private String group_name;
    private String register_dttm;
    private String x_swifi_mgr_no;
    private String x_swifi_main_nm;
}