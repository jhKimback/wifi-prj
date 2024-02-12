package Service;

import dto.BookMarkGroup;
import dto.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    public List<History> showHistory() {
        List<History> historyList = new ArrayList<>();
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

            String sql = " select * " +
                    " from history_wifi " +
                    " order by id desc; ";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                History history = new History();
                Integer id = rs.getInt("id");
                String lat = rs.getString("lat");
                String lnt = rs.getString("lnt");
                String searchDttm = rs.getString("search_dttm");

                history.setId(id);
                history.setLat(lat);
                history.setLnt(lnt);
                history.setSearch_dttm(searchDttm);

                historyList.add(history);
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
        return historyList;
    }
    public int addHistory(History history) {
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
        int affected = 0;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "INSERT INTO history_wifi (lat, lnt, search_dttm) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, history.getLat());
            preparedStatement.setString(2, history.getLnt());
            preparedStatement.setString(3, history.getSearch_dttm());
            affected = preparedStatement.executeUpdate();

            if (affected > 0){
                System.out.println("히스토리 추가 완료");
            } else {
                System.out.println("히스토리 추가 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return affected;
    }

    public int deleteHistory(History history) {
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

            String sql = " delete from history_wifi where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,  history.getId());
            affected = preparedStatement.executeUpdate();

            if (affected > 0){
                System.out.println("북마크 그룹 데이터 추가 완료");
            } else {
                System.out.println("북마크 그룹 데이터 추가 실패");
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
        return affected;
    }

}
