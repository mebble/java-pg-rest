package java_pg_rest.models;

import java.util.List;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;

public interface ItemDao {
    @SqlQuery("SELECT * FROM item WHERE state != 'discontinued'")
    @RegisterBeanMapper(Item.class)
    List<Item> getAllItems();

    @SqlQuery("SELECT * FROM item WHERE id = ? AND state != 'discontinued'")
    @RegisterBeanMapper(Item.class)
    Item getItem(int id);

    @SqlUpdate("INSERT INTO item (name, quantity) VALUES (:name, :quantity)")
    @GetGeneratedKeys({"id", "name", "quantity"})
    @RegisterBeanMapper(Item.class)
    Item insertItem(@BindBean Item item);

    @SqlUpdate("INSERT INTO item (id, name, quantity) VALUES (:id, :name, :quantity) " +
            "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, quantity = EXCLUDED.quantity, state = 'present'")
    @GetGeneratedKeys({"id", "name", "quantity"})
    @RegisterBeanMapper(Item.class)
    Item putItem(@BindBean Item item);

    @SqlUpdate("UPDATE item SET state = 'discontinued' WHERE id = ? AND state != 'discontinued'")
    boolean deleteItem(int id);
}
