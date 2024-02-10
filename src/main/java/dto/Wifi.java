package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Wifi {
    private String baseDate;
    private String macAddress;
    private String apGroupName;
    private String installLocationDetail;
    private String category;
    private String categoryDetail;
    private String addressDong;
    private String addressDetail;
    private String latitude;
    private String longitude;

}