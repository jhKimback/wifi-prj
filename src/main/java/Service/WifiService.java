package Service;

import dto.Wifi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiService {
    public static void main(String[] args) {
        WifiService wf = new WifiService();
    }

    public List<Wifi> showNearWifi(String lat, String lnt) {
        List<Wifi> wifiList = new ArrayList<>();
        String url = "jdbc:mariadb://localhost:3306/wifi?allowPublicKeyRetrieval=true&useSSL=false";
        String dbUserId = "testuser";
        String dbPassword = "1111";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT *, " +
                    " round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) " +
                    " -radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    " AS distance " +
                    " FROM public_wifi " +
                    " ORDER BY distance " +
                    " LIMIT 2;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, Double.parseDouble(lat));
            preparedStatement.setDouble(2, Double.parseDouble(lnt));
            preparedStatement.setDouble(3, Double.parseDouble(lat));

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Double distance = rs.getDouble("distance");
                String x_swifi_mgr_no = rs.getString("x_swifi_mgr_no");
                String x_swifi_wrdofc = rs.getString("x_swifi_wrdofc");
                String x_swifi_main_nm = rs.getString("x_swifi_main_nm");
                String x_swifi_adres1 = rs.getString("x_swifi_adres1");
                String x_swifi_adres2 = rs.getString("x_swifi_adres2");
                String x_swifi_instl_floor = rs.getString("x_swifi_instl_floor");
                String x_swifi_instl_ty = rs.getString("x_swifi_instl_ty");
                String x_swifi_instl_mby = rs.getString("x_swifi_instl_mby");
                String x_swifi_svc_se = rs.getString("x_swifi_svc_se");
                String x_swifi_cmcwr = rs.getString("x_swifi_cmcwr");
                String x_swifi_cnstc_year = rs.getString("x_swifi_cnstc_year");
                String x_swifi_inout_door = rs.getString("x_swifi_inout_door");
                String x_swifi_remars3 = rs.getString("x_swifi_remars3");
                lat = rs.getString("lat");
                lnt = rs.getString("lnt");
                String work_dttm = rs.getString("work_dttm");

                Wifi wifi = Wifi.builder()
                        .distance(distance)
                        .x_swifi_mgr_no(x_swifi_mgr_no)
                        .x_swifi_wrdofc(x_swifi_wrdofc)
                        .x_swifi_main_nm(x_swifi_main_nm)
                        .x_swifi_adres1(x_swifi_adres1)
                        .x_swifi_adres2(x_swifi_adres2)
                        .x_swifi_instl_floor(x_swifi_instl_floor)
                        .x_swifi_instl_ty(x_swifi_instl_ty)
                        .x_swifi_instl_mby(x_swifi_instl_mby)
                        .x_swifi_svc_se(x_swifi_svc_se)
                        .x_swifi_cmcwr(x_swifi_cmcwr)
                        .x_swifi_cnstc_year(x_swifi_cnstc_year)
                        .x_swifi_inout_door(x_swifi_inout_door)
                        .x_swifi_remars3(x_swifi_remars3)
                        .lat(lat)
                        .lnt(lnt)
                        .work_dttm(work_dttm)
                        .build();

                wifiList.add(wifi);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return wifiList;
    }
}
