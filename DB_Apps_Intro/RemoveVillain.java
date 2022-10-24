import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class RemoveVillain {

    static final String SELECT_VILLAIN = "SELECT name FROM villains WHERE id = ?";
    static final String SELECT_MINIONS = "SELECT COUNT(minion_id) AS m_count FROM minions_villains WHERE villain_id = ?";
    static final String DELETE_MINION_VILLAIN = "DELETE FROM minions_villains WHERE villain_id = ?";
    static final String DELETE_VILLAIN = "DELETE FROM villains WHERE id = ?";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        Connection connection = DriverManager.getConnection(Constants.JDBC_URL, properties);

        Scanner sc = new Scanner(System.in);
        int id = Integer.parseInt(sc.nextLine());

        PreparedStatement selectVillain = connection.prepareStatement(SELECT_VILLAIN);

        selectVillain.setInt(1, id);

        ResultSet villainSet = selectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }
        String villainName = villainSet.getString("name");

        connection.setAutoCommit(false);

        PreparedStatement selectMinIds = connection.prepareStatement(SELECT_MINIONS);
        selectMinIds.setInt(1, id);
        ResultSet minionsCountSet = selectMinIds.executeQuery();
        minionsCountSet.next();

        int delMinCount = minionsCountSet.getInt("m_count");

        try {
            PreparedStatement deleteMinVil = connection.prepareStatement(DELETE_MINION_VILLAIN);
            deleteMinVil.setInt(1, id);
            deleteMinVil.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement(DELETE_VILLAIN);
            deleteVillain.setInt(1, id);
            deleteVillain.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
        }
        System.out.println(villainName + " was deleted");
        System.out.println(delMinCount + " minions released");
        connection.close();

    }
}
