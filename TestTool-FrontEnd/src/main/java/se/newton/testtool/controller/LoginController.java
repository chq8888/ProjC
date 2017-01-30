package se.newton.testtool.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import se.newton.testtool.*;

public class LoginController implements Initializable {
    
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassw;
    @FXML private Button btnLogin;
    @FXML private Label lblError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setText("");
        
        btnLogin.setOnAction(ev -> {

            Util.switchScene(ev, getClass().getResource("/se/newton/testtool/view/Action.fxml"));
        });
    }
}
