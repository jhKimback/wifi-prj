package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookMarkGroup {
    private int id;
    private String group_name;
    private int order_no;
    private String register_dttm;
    private String modify_dttm;
}