package java_pg_rest.models;

import java.util.List;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

public interface ItemDao {
    @SqlQuery("SELECT * FROM item")
    @RegisterBeanMapper(Item.class)
    List<Item> getAllItems();

//    @SqlUpdate("INSERT INTO item (name, quantity) VALUES (?, ?)")
//    Item insertItem(String name, int quantity);
}
xs