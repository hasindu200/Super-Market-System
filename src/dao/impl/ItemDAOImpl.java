package dao.impl;

import dao.CrudUtil;
import dto.ItemDTO;
import entitiy.Customer;
import entitiy.Item;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?)", item.getItemCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()
        );
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT id FROM Item WHERE id=?", id).next();
    }



    @Override
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?", itemCode);
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?,unitPrice=?, qtyOnHand=? WHERE itemCode=?", item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getItemCode()
        );
    }

    @Override
    public Item search(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", itemCode);
        if (rst.next()) {
            return new Item(itemCode,
                    rst.getString("description"),
                    rst.getDouble("unitPrice"),
                    rst.getInt("qtyOnHand")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> item = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            item.add(new Item(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getDouble("unitPrice"),
                    rst.getInt("qtyOnHand")
            ));
        }
        return item;
    }


    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, unitPrice=?,qtyOnHand=? WHERE itemCode=?", dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(),dto.getItemCode());
    }

    @Override
    public int items() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(*) FROM Item");
        if (rst.next()) {
            return rst.getInt(1);
        }else {
            return 0;
        }
    }

    @Override
    public String getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString("itemCode").split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "I-00" + tempId;
            } else if (tempId <= 99) {
                return "I-0" + tempId;
            } else {
                return "I-" + tempId;
            }
        } else {
            return "I-001";
        }
    }


    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            ids.add(
                    rst.getString("itemCode")
            );
        }
        return ids;
    }
}

