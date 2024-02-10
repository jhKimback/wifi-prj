package Database;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dto.Wifi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class APIClient {

    public static void main(String[] args) {
        String apiUrl = "https://open.jejudatahub.net/api/proxy/Dtb18ta1btbD1Da1a81aaDttab6tDabb/o_oc9c9t8to7c882brrr8r5o9p28ot25";
        String baseDate = "20230201";
        String jsonResponse = "";

        try {
            jsonResponse = sendGetRequest(apiUrl, baseDate);
            saveJSONToMariaDB(jsonResponse);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String url, String baseDate) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Construct the URL with query parameters
        String fullUrl = url + "?baseDate=" + baseDate;

        Request request = new Request.Builder()
                .url(fullUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    private static void saveJSONToMariaDB(String jsonResponse) throws SQLException {
        Gson gson = new Gson();

        // Parse JSON response
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        // Convert JSON array to array of Wifi objects
        Wifi[] wifiData = gson.fromJson(dataArray, Wifi[].class);

        // Your existing database insertion code remains unchanged
        String url = "jdbc:mariadb://localhost:3306/testdb1?user=testuser&password=1111&allowPublicKeyRetrieval=true";

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO wifi_table (baseDate, macAddress, apGroupName, installLocationDetail, category, categoryDetail, addressDong, addressDetail, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (Wifi wifi : wifiData) {
                    statement.setString(1, wifi.getBaseDate());
                    statement.setString(2, wifi.getMacAddress());
                    statement.setString(3, wifi.getApGroupName());
                    statement.setString(4, wifi.getInstallLocationDetail());
                    statement.setString(5, wifi.getCategory());
                    statement.setString(6, wifi.getCategoryDetail());
                    statement.setString(7, wifi.getAddressDong());
                    statement.setString(8, wifi.getAddressDetail());

                    // Check if latitude and longitude are not null before parsing
                    if (wifi.getLatitude() != null) {
                        statement.setDouble(9, Double.parseDouble(wifi.getLatitude()));
                    } else {
                        statement.setNull(9, java.sql.Types.DOUBLE);
                    }

                    if (wifi.getLongitude() != null) {
                        statement.setDouble(10, Double.parseDouble(wifi.getLongitude()));
                    } else {
                        statement.setNull(10, java.sql.Types.DOUBLE);
                    }

                    statement.addBatch();
                }
                statement.executeBatch();
            }
        }
    }
}
