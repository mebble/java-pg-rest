import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

import spark.Spark;
import com.google.gson.Gson;

public class HelloSpark {
    public static Jdbi connectToDB() {
        Jdbi jdbi = Jdbi.create("jdbc:postgresql:java_pg_rest");
        jdbi.installPlugin(new SqlObjectPlugin());
        System.out.println("Connected to the PostgreSQL server successfully.");

        return jdbi;
    }

    public static void main(String[] args) {
        Jdbi jdbi = connectToDB();
        Gson gson = new Gson();

        Spark.port(3000);
        Spark.staticFiles.location("/public");

        Spark.get("/items", (req, res) -> {
            System.out.println("GET /items");
            List<Item> items = jdbi.withExtension(ItemDao.class, dao -> dao.getAllItems());
            String json = gson.toJson(items);
            return json;
        });
    }

    public interface ItemDao {
        @SqlQuery("SELECT * FROM item")
        @RegisterBeanMapper(Item.class)
        List<Item> getAllItems();
    }

    public static class Item implements java.io.Serializable {
        public int id;
        public String name;
        public int quantity;

        public Item() {
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}