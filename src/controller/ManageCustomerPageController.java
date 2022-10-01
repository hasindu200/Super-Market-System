package controller;


import TM.CustomerTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import bo.custom.Bo.CustomerBO;
import bo.custom.impl.CustomerBoImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerPageController {

    CustomerBO customerBO = new CustomerBoImpl();
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<CustomerTm>tblCustomers;
    public AnchorPane root;


    public void initialize() {

      tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
      tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
      tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


        loadAllCustomers();


        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);
            if (newValue != null) {
                txtCustomerId.setText(newValue.getId());
                txtCustomerName.setText(newValue.getName());
                txtCustomerAddress.setText(newValue.getAddress());
                txtCustomerId.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);

            }
        });

        txtCustomerAddress.setOnAction(event -> btnSave.fire());
        loadAllCustomers();

    }

    /*Get all customers*/

    private void loadAllCustomers() {
        tblCustomers.getItems().clear();

        try {
            ArrayList<CustomerDto> allCustomers = customerBO.getAllCustomer();
            for (CustomerDto customer : allCustomers) {
                tblCustomers.getItems().add(new CustomerTm(customer.getId(), customer.getName(), customer.getAddress()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }









    public void btnDelete_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (customerBO.deleteCustomer(txtCustomerId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            tblCustomers.refresh();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        tblCustomers.refresh();

    }


    public void backToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/MainPage.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());

    }

    public void btnSave_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustomerAddress.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {

            try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }


            CustomerDto customerDTO = new CustomerDto(id, name, address);
            customerBO.addCustomer(customerDTO);
            tblCustomers.getItems().add(new CustomerTm(id, name, address));
                tblCustomers.refresh();


            } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    } else {
        /*Update customer*/
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            CustomerDto customerDTO = new CustomerDto(id, name, address);
            customerBO.updateCustomer(customerDTO);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            CustomerTm selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            tblCustomers.refresh();
         }

    }
}