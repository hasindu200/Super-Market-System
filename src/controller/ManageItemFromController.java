package controller;


import TM.ItemTm;
import bo.custom.Bo.ItemBO;
import bo.custom.BoFactory;
import dto.ItemDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageItemFromController {



    public AnchorPane root;


    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public TextField txtId;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TableView<ItemTm> itemTable;
    public TableColumn tblItemCode;
    public TableColumn tblDescription;
    public TableColumn tblUnitPrice;
    public TableColumn tblQty;
    public Button btnSave;
    public Button btnDelete;

    Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");


    public void initialize() {

        itemTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        itemTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


        loadAllItems();

        itemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnSave.setText(newValue != null ? "Update" : "Save");

            if (newValue != null) {

                txtId.setText(newValue.getItemCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));

            }
        });

    }



    private void loadAllItems() {
        itemTable.getItems().clear();

        try {
            ArrayList<ItemDTO> allItems = itemBO.getAll();
            for (ItemDTO item : allItems) {
                itemTable.getItems().add(new ItemTm(item.getItemCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifCustomerExist(id);
    }


    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {



        String code = txtId.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        if (!code.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Item code").show();
            txtId.requestFocus();
            return;
        } else if (!description.matches("^[A-z ]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Description should be at least 3 characters long No numbers...").show();
            txtDescription.requestFocus();
            return;
        }
         else if (!txtUnitPrice.getText().matches("^[1-9][0-9]*([.][0-9]{2})?$")) {
            new Alert(Alert.AlertType.ERROR, "Numbers Only").show();
            txtUnitPrice.requestFocus();
            return;
        }

        else if (!txtQtyOnHand.getText().matches("^[1-9][0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Numbers Only").show();
            txtQtyOnHand.requestFocus();
            return;
        }


        if (btnSave.getText().equalsIgnoreCase("save")) {

            ItemDTO itemDTO = new ItemDTO(code, description, unitPrice, qtyOnHand);

            if (itemBO.addItem(itemDTO)) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                loadAllItems();
                clearText();


            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            }

        }
        if (btnSave.getText().equalsIgnoreCase("Update")) {

            ItemDTO itemDTO = new ItemDTO(code, description, unitPrice, qtyOnHand);

            if (itemBO.update(itemDTO)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadAllItems();
                clearText();


            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                clearText();

            }


        }

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (itemBO.deleteItem(txtId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            loadAllItems();
            clearText();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            clearText();
        }


    }


    public void goToHome(MouseEvent mouseEvent) throws IOException {


        URL resource = this.getClass().getResource("../view/MainPage.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());


    }

    private void clearText() {
        txtDescription.setText("");
        txtId.setText("");
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
    }
}
