package se.newton.testtool.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.List;

import javax.ws.rs.core.GenericType;
import se.newton.testtool.model.User;
import se.newton.testtool.*;

public class TestWebService implements Initializable {

    @FXML
    private Button btn;
    @FXML
    private TextArea txtArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn.setOnAction((ActionEvent e) -> {

            GenericType<List<User>> type = new GenericType<List<User>>(){};
            List<User> users = Util.GetRequest("users", type);

            txtArea.setText(users.toString());
            System.out.println(users);

//            if (users != null) {
//                System.out.println(users.getName());
//            }
        });
    }
}
