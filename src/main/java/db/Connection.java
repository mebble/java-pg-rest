package java_pg_rest.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class Connection {
    public static Jdbi createConnection(String dbUrl) {
        Jdbi jdbi = Jdbi.create(dbUrl);
        jdbi.installPlugin(new SqlObjectPlugin());
        System.out.println("Successfully connected to the database");

        return jdbi;
    }
}
