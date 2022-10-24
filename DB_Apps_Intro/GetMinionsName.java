import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionsName {

    private static final String GET_MINIONS_NAME_AND_AGE =
            "select m.name, m.age " +
            " from  minions as  m" +
            " join minions_villains as mv on m.id = mv.minion_id" +
            " where mv.villain_id = ?";
    private static final String GET_VILLAIN_NAME =
            "select v.name from villains v " +
            "where v.id = ?";

    private final  static String COLUMN_LABEL_AGE="age";
    private static final String NO_VILLAIN = "No villain with ID %d exists in the database.";
    private final static String VILLAIN_FORMAT = "Villain: %s%n";
    private final static String MINION_FORMAT = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainID = new Scanner(System.in).nextInt();

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME);

        villainStatement.setInt(1, villainID);

        final ResultSet resultSet = villainStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf(NO_VILLAIN, villainID);
            return;
        }
        final  String villainName = resultSet.getString("name");
        System.out.printf(VILLAIN_FORMAT, villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE);

        minionsStatement.setInt(1, villainID);
        final ResultSet minionsSet = minionsStatement.executeQuery();

        for (int i = 1;  minionsSet.next(); i++) {
            final String minionName = minionsSet.getString("name");
            final int minionAge = minionsSet.getInt(COLUMN_LABEL_AGE);

            System.out.printf(MINION_FORMAT, i, minionName, minionAge);
        }
    }
}
