package controller;

import bo.custom.Bo.OrderBO;
import bo.custom.BoFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class PlaceOderFromController {
    public AnchorPane root;
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public TableView tblOrderDetails;
    public JFXButton btnSave;
    private final OrderBO purchaseOrderBO = (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);


    public void backToHome(MouseEvent event) throws IOException {

        URL resource = this.getClass().getResource("../view/MainPage.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());

    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
    }

    private void loadCustomersIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = purchaseOrderBO.getAllCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);
    }


}
