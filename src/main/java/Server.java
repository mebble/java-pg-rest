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
            res.type("application/json");

            List<Item> items = conn.withExtension(ItemDao.class, dao -> dao.getAllItems());
            String json = gson.toJson(items);
            return json;
        });

        Spark.get("/items/:id", (req, res) -> {
            System.out.println("GET /items/:id");
            res.type("application/json");

            int id;
            try {
                id = Integer.parseInt(req.params(":id"));
            } catch (Exception e) {
                res.status(400);
                return "no";
            }

            Item item = conn.withExtension(ItemDao.class, dao -> dao.getItem(id));
            if (item == null) {
                res.status(404);
                return "no";
            }

            String json = gson.toJson(item);
            return json;
        });

        Spark.post("/items", (req, res) -> {
            System.out.println("POST /items");
            res.type("application/json");

            Item postedItem;
            try {
                postedItem = gson.fromJson(req.body(), Item.class);
            } catch (Exception e) {
                res.status(400);
                return "no";  // !! return error body
            }

            Item insertedItem = conn.withExtension(ItemDao.class, dao -> dao.insertItem(postedItem));
            String json = gson.toJson(insertedItem);
            return json;
        });

        Spark.put("/items/:id", (req, res) -> {
            System.out.println("PUT /items/:id");
            res.type("application/json");

            Item itemToPut;
            try {
                itemToPut = gson.fromJson(req.body(), Item.class);
                int id = Integer.parseInt(req.params(":id"));
                itemToPut.setId(id);
            } catch (Exception e) {
                res.status(400);
                return "no";
            }

            Item itemPut = conn.withExtension(ItemDao.class, dao -> dao.putItem(itemToPut));
            String json = gson.toJson(itemPut);
            return json;
        });
    }
}
