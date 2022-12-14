package bo.custom.Bo;



import TM.ItemTm;
import bo.custom.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    String getItemIds() throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String itemId) throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAll()throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    int items() throws SQLException, ClassNotFoundException;
}
