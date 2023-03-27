import java.sql.*;

public class ConnectionManager {

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mariadb://localhost:3306/tenniss",
                        "root", "salutsalut1");
            }catch (ClassNotFoundException e){
                throw new RuntimeException(e);
            }

            }}

