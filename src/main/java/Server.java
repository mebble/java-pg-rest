package java_pg_rest;

import java.util.List;

import spark.Spark;
import com.google.gson.Gson;
import org.jdbi.v3.core.Jdbi;   // !! leaking the Connection implementation

import java_pg_rest.db.Connection;
import java_pg_rest.models.*;

public class Server {
    public static void main(String[] args) {
        Jdbi conn = Connection.createConnection("jdbc:postgresql:java_pg_rest");
        Gson gson = new Gson();

        Spark.port(3000);
        Spark.staticFiles.location("/public");

        Spark.get("/items", (req, res) -> {
            System.out.println("GET /items");
            List<Item> items = conn.withExtension(ItemDao.class, dao -> dao.getAllItems());
            String json = gson.toJson(items);
            return json;
        });

        Spark.post("/items", (req, res) -> {
            System.out.println("POST /items");
            Item postedItem = gson.fromJson(req.body(), Item.class);
            Item insertedItem = conn.withExtension(ItemDao.class, dao -> dao.insertItem(postedItem));
            String json = gson.toJson(insertedItem);
            return json;
        });
    }
}