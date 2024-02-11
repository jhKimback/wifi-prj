package Service;

import dto.BookMark;
import dto.BookMarkGroup;
import dto.Wifi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkService {
    public int addBookMark(BookMark bookmark, BookMarkGroup bookmarkGroup) {
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

            String sql = " insert into bookmark (register_dttm, group_id, x_swifi_mgr_no) "+
                    " values (?, (select id from bookmark_group where group_name = ?) , ?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookmark.getRegister_dttm());
            preparedStatement.setString(2, bookmarkGroup.getGroup_name());
            preparedStatement.setString(3, bookmark.getX_swifi_mgr_no());
            affected = preparedStatement.executeUpdate();

            if (affected > 0){
                System.out.println("북마크 데이터 추가 완료");
            } else {
                System.out.println("북마크 데이터 추가 실패");
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
    public List<BookMark> showBookMark() {
        List<BookMark> bookmarkList = new ArrayList<>();
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

            String sql = " select bm.id, bmg.group_name, bm.x_swifi_mgr_no, w.x_swifi_main_nm, bm.register_dttm from bookmark as bm" +
                         " left join bookmark_group as bmg" +
                         " on bm.group_id = bmg.id" +
                         " left join public_wifi as w" +
                         " on w.x_swifi_mgr_no = bm.x_swifi_mgr_no";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Integer id = rs.getInt("bm.id");
                String mgrNo = rs.getString("bm.x_swifi_mgr_no");
                String groupName = rs.getString("bmg.group_name");
                String mainNm = rs.getString( "w.x_swifi_main_nm");
                String registerDttm = rs.getString("bm.register_dttm");

                BookMark bookmark = new BookMark();

                bookmark.setId(id);
                bookmark.setX_swifi_mgr_no(mgrNo);
                bookmark.setGroup_name(groupName);
                bookmark.setX_swifi_main_nm(mainNm);
                bookmark.setRegister_dttm(registerDttm);

                bookmarkList.add(bookmark);
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
        return bookmarkList;
    }

    public BookMark showOneBookMark(Integer id) {
        BookMark bookmark = new BookMark();
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

            String sql = " select bmg.group_name, w.x_swifi_main_nm, bm.register_dttm from bookmark as bm" +
                    " left join bookmark_group as bmg" +
                    " on bm.group_id = bmg.id" +
                    " left join public_wifi as w" +
                    " on w.x_swifi_mgr_no = bm.x_swifi_mgr_no" +
                    " where bm.id = ?; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()){
                String groupName = rs.getString("bmg.group_name");
                String mainNm = rs.getString( "w.x_swifi_main_nm");
                String registerDttm = rs.getString("bm.register_dttm");

                bookmark.setId(id);
                bookmark.setGroup_name(groupName);
                bookmark.setX_swifi_main_nm(mainNm);
                bookmark.setRegister_dttm(registerDttm);
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
        return bookmark;
    }
    public int deleteBookMark(BookMark bookMark) {
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

            String sql = " delete from bookmark where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,  bookMark.getId());
            affected = preparedStatement.executeUpdate();

            if (affected > 0){
                System.out.println("북마크 데이터 삭제 완료");
            } else {
                System.out.println("북마크 데이터 삭제 실패");
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
