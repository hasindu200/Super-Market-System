package dao.impl;

import dao.CrudDAO;
import dto.ItemDTO;
import entitiy.Customer;
import entitiy.Item;


import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {

    boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public int items() throws SQLException, ClassNotFoundException;
    public String getItemIds() throws SQLException,ClassNotFoundException;
    public List getAllItemIds() throws SQLException,ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

}


