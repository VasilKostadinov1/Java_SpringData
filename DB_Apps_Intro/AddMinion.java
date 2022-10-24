import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddMinion {

    public static final String SELECT_TOWN = "SELECT id FROM towns WHERE name = ?";
    public static final String SELECT_VILLAIN = "SELECT id FROM villains WHERE name = ?";
    public static final String INSERT_TOWN = "INSERT INTO towns(name) VALUES(?)";
    public static final String INSERT_VILLAIN = "INSERT INTO villains(name, evilness_factor) VALUES(?, ?)";
    public static final String INSERT_MINION = "INSERT INTO minions(name, age, town_id) VALUES(?, ?, ?)";
    public static final String GET_LAST_MINION = "SELECT id FROM minions ORDER BY id DESC LIMIT 1";
    public static final String INSERT_MINIONS_TO_VILLAIN = "INSERT INTO minions_villains VALUES (?, ?)";
    public static final String INSERT_VILLAIN_CONFIRM = "Villain %s was added to the database.\n";
    public static final String INSERT_TOWN_CONFIRM = "Town %s was added to the database.\n";
    public static final String FINAL_ADDITION = "Successfully added %s to be minion of %s.\n";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        Connection connection = DriverManager.getConnection(Constants.JDBC_URL, properties);

        Scanner sc = new Scanner(System.in);

        String[] input = sc.nextLine().split("\\s+");
        String villainName = sc.nextLine().split("\\s+")[1];

        String minionName = input[1];
        int minionAge = Integer.parseInt(input[2]);
        String minionCity = input[3];

        int townId = getTownId(connection, minionCity);

        int villainId = getVillainId(connection, villainName);

        insertMinion(connection, minionName, minionAge, townId);

        int lastMinionId = getLastMinionId(connection);

        insertMinionsVillain(connection, villainId, lastMinionId);

        System.out.printf(FINAL_ADDITION, minionName, villainName);
    }

    private static int getTownId(Connection connection, String minionCity) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement(SELECT_TOWN);
        selectTown.setString(1, minionCity);

        ResultSet townSet = selectTown.executeQuery();

        int townId = 0;
        if (!townSet.next()) {
            PreparedStatement insert = connection.prepareStatement(INSERT_TOWN);
            insert.setString(1, minionCity);
            insert.executeUpdate();

            ResultSet newSet = selectTown.executeQuery();
            newSet.next();
            townId = newSet.getInt("id");
            System.out.printf(INSERT_TOWN_CONFIRM, minionCity);
        } else {
            townId = townSet.getInt("id");
        }
        return townId;
    }

    private static int getVillainId(Connection connection, String villainName) throws SQLException {
        PreparedStatement selectVillain = connection.prepareStatement(SELECT_VILLAIN);
        selectVillain.setString(1, villainName);
        ResultSet villainSet = selectVillain.executeQuery();

        int villainId = 0;
        if (!villainSet.next()) {
            PreparedStatement insert = connection.prepareStatement(INSERT_VILLAIN);

            insert.setString(1, villainName);
            insert.setString(2, "evil");

            insert.executeUpdate();

            ResultSet newSet = selectVillain.executeQuery();
            newSet.next();
            villainId = newSet.getInt("id");
            System.out.printf(INSERT_VILLAIN_CONFIRM, villainName);
        } else {
            villainId = villainSet.getInt("id");
        }
        return villainId;
    }

    private static void insertMinion(Connection connection, String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement insertMinion = connection.prepareStatement(INSERT_MINION);

        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);

        insertMinion.executeUpdate();
    }

    private static int getLastMinionId(Connection connection) throws SQLException {
        PreparedStatement lastMinion = connection.prepareStatement(GET_LAST_MINION);
        ResultSet lastMinionSet = lastMinion.executeQuery();
        lastMinionSet.next();
        return lastMinionSet.getInt("id");
    }

    private static void insertMinionsVillain(Connection connection, int villainId, int lastMinionId) throws SQLException {
        PreparedStatement insertMinionsVillains = connection.prepareStatement(INSERT_MINIONS_TO_VILLAIN);
        insertMinionsVillains.setInt(1, lastMinionId);
        insertMinionsVillains.setInt(2, villainId);
        insertMinionsVillains.executeUpdate();
    }

    }

