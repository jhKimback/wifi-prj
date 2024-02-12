package Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.Wifi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiService {

    private static final String API_KEY = "65514f686f726c613637544b494554";
    private static String API_URL = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo/";
    private static OkHttpClient okHttpClient = new OkHttpClient();
    public static int WifiTotalCount() throws IOException{
        int cnt = 0;

        URL url = new URL(API_URL + "1/1");

        Request.Builder builder = new Request.Builder().url(url).get();
        Request request = builder.build();
        Response response = okHttpClient.newCall(request).execute();

        try {
            if (response.isSuccessful()) {
                ResponseBody body = response.body();

                if (body != null) {
                    JsonElement jsonElement = JsonParser.parseString(body.string());

                    cnt = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
                            .getAsJsonObject().get("list_total_count")
                            .getAsInt();
                }

            } else {
                System.out.println(response.code());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnt;
    }
    public static int getWifiJson() throws IOException {
        int totalCnt = WifiTotalCount();
        int start = 1, end = 1;
        int count = 0;

        try {
            for (int i = 0; i <= totalCnt / 1000; i++) {
                start = 1 + (1000 * i);
                end = 1000 + (1000 * i);

                URL url = new URL(API_URL + start + "/" + end);

                Request.Builder builder = new Request.Builder().url(url).get();
                Request request = builder.build();
                Response response = okHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();

                    if (responseBody != null) {
                        JsonElement jsonElement = JsonParser.parseString(responseBody.string());

                        JsonArray jsonArray = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
                                .getAsJsonObject().get("row")
                                .getAsJsonArray();

                        count += insertTotalWifi(jsonArray);

                    } else {
                        System.out.println(response.code());
                    }
                } else {
                    System.out.println(response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    public static int insertTotalWifi(JsonArray jsonArray) {
        List<Wifi> wifiList = new ArrayList<>();
        String url = "jdbc:mariadb://localhost:3306/wifi?allowPublicKeyRetrieval=true&useSSL=false";
        String dbUserId = "testuser";
        String dbPassword = "1111";
        int count = 0;

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
            connection.setAutoCommit(false);

            String sql = " insert into public_wifi "
                    + " ( x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, "
                    + " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
                    + " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject data = (JsonObject) jsonArray.get(i).getAsJsonObject();

                preparedStatement.setString(1, data.get("X_SWIFI_MGR_NO").getAsString());
                preparedStatement.setString(2, data.get("X_SWIFI_WRDOFC").getAsString());
                preparedStatement.setString(3, data.get("X_SWIFI_MAIN_NM").getAsString());
                preparedStatement.setString(4, data.get("X_SWIFI_ADRES1").getAsString());
                preparedStatement.setString(5, data.get("X_SWIFI_ADRES2").getAsString());
                preparedStatement.setString(6, data.get("X_SWIFI_INSTL_FLOOR").getAsString());
                preparedStatement.setString(7, data.get("X_SWIFI_INSTL_TY").getAsString());
                preparedStatement.setString(8, data.get("X_SWIFI_INSTL_MBY").getAsString());
                preparedStatement.setString(9, data.get("X_SWIFI_SVC_SE").getAsString());
                preparedStatement.setString(10, data.get("X_SWIFI_CMCWR").getAsString());
                preparedStatement.setString(11, data.get("X_SWIFI_CNSTC_YEAR").getAsString());
                preparedStatement.setString(12, data.get("X_SWIFI_INOUT_DOOR").getAsString());
                preparedStatement.setString(13, data.get("X_SWIFI_REMARS3").getAsString());
                preparedStatement.setString(14, data.get("LAT").getAsString());
                preparedStatement.setString(15, data.get("LNT").getAsString());
                preparedStatement.setString(16, data.get("WORK_DTTM").getAsString());

                preparedStatement.addBatch();
                preparedStatement.clearParameters();

                if ((i + 1) % 1000 == 0) {
                    int[] result = preparedStatement.executeBatch();
                    count += result.length;
                    connection.commit();
                }
            }

            int[] result = preparedStatement.executeBatch();
            count += result.length;
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
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
        return count;
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
                    " LIMIT 20;";

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

    public Wifi showInfoWifi(Wifi wifi) {
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

        int affected = 0;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "select *, round(6371 * acos(cos(radians(sub.lat)) * COS(radians(sub.lnt)) * COS(RADIANS(sub.lat)) * COS(radians(sub.lnt)) + sin(radians(sub.lat))  * sin(radians(sub.lat))), 4)" +
            " AS distance " +
            " FROM public_wifi, " +
            " (SELECT lat, lnt FROM public_wifi WHERE x_swifi_mgr_no = ?) AS sub " +
            " WHERE x_swifi_mgr_no = ?; ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wifi.getX_swifi_mgr_no());
            preparedStatement.setString(2, wifi.getX_swifi_mgr_no());

            rs = preparedStatement.executeQuery();

            if (rs.next()){
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
                String lat = rs.getString("lat");
                String lnt = rs.getString("lnt");
                String work_dttm = rs.getString("work_dttm");

                wifi = Wifi.builder()
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
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return wifi;
    }

}
