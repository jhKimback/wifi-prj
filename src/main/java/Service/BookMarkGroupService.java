package Service;

import dto.BookMarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkGroupService {
    public static void main (String[] args){
        BookMarkGroupService bs = new BookMarkGroupService();
        dto.BookMarkGroup bookmarkGroup = new dto.BookMarkGroup();
//        bookmarkGroup.setId(6);
//        bookmarkGroup.setModify_dttm("2023-01-01");
//        bookmarkGroup.setGroup_name("알고");
//        bookmarkGroup.setOrder_no(3);
//        bs.editBookMarkGroup(bookmarkGroup);
    }
    public int addBookMarkGroup(BookMarkGroup bookMarkGroup) {
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

            String sql = " insert into bookmark_group (group_name, order_no, register_dttm) "+
                    " values (?, ?, ?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  bookMarkGroup.getGroup_name());
            preparedStatement.setInt(2, bookMarkGroup.getOrder_no());
            preparedStatement.setString(3, bookMarkGroup.getRegister_dttm());
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
    public List<BookMarkGroup> showBookMarkGroup() {
        List<BookMarkGroup> bookmarkGroupList = new ArrayList<>();
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

            String sql = " select id, group_name, order_no, register_dttm, modify_dttm from bookmark_group order by order_no";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Integer id = rs.getInt("id");
                String groupName = rs.getString("group_name");
                Integer orderNo = rs.getInt("order_no");
                String registerdttm = rs.getString("register_dttm");
                String modifydttm = rs.getString("modify_dttm");
                BookMarkGroup bookmarkGroup = new BookMarkGroup();

                bookmarkGroup.setId(id);
                bookmarkGroup.setGroup_name(groupName);
                bookmarkGroup.setOrder_no(orderNo);
                bookmarkGroup.setRegister_dttm(registerdttm);
                bookmarkGroup.setModify_dttm(modifydttm);

                bookmarkGroupList.add(bookmarkGroup);
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
        return bookmarkGroupList;
    }
    public BookMarkGroup showOneBookMarkGroup(Integer id) {
        BookMarkGroup bookmarkGroup = new BookMarkGroup();
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

            String sql = " select group_name, order_no " +
                    " from bookmark_group " +
                    " where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();

            if (rs.next()){
                String groupName = rs.getString("group_name");
                Integer orderNo = rs.getInt("order_no");
                bookmarkGroup.setGroup_name(groupName);
                bookmarkGroup.setOrder_no(orderNo);
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
        return bookmarkGroup;
    }

    public int deleteBookMarkGroup(BookMarkGroup bookMarkGroup) {
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

            String sql = " delete from bookmark_group where group_name = ? and order_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  bookMarkGroup.getGroup_name());
            preparedStatement.setInt(2,  bookMarkGroup.getOrder_no());
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
    public int editBookMarkGroup(BookMarkGroup bookMarkGroup) {
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

            String sql = " update bookmark_group set group_name = ?, order_no = ?, modify_dttm = ? where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookMarkGroup.getGroup_name());
            preparedStatement.setInt(2,  bookMarkGroup.getOrder_no());
            preparedStatement.setString(3,  bookMarkGroup.getModify_dttm());
            preparedStatement.setInt(4,  bookMarkGroup.getId());
            affected = preparedStatement.executeUpdate();

            if (affected > 0){
                System.out.println("북마크 그룹 데이터 수정 완료");
            } else {
                System.out.println("북마크 그룹 데이터 수정 실패");
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
