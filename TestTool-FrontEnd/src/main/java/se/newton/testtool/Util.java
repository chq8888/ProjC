package se.newton.testtool;

import java.util.List;
import java.net.URL;
import java.io.IOException;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {

    public static String HOST = "localhost";
    public static String PORT = "8080";
    public static String APPNAME = "TestTool-BackEnd-Maven-Jersey-Webapp";
    public static String BASE_URI = String.format("http://%s:%s/%s/api", HOST, PORT, APPNAME);
    public static String CONTENT_TYPE = MediaType.APPLICATION_JSON;
    public static boolean LOG_REQUEST = true;
    public static final Client CLIENT = ClientBuilder.newClient();
    
    public static <T> T GetRequest(String resourcePath, Class<T> tyoeofClass) {
        
        WebTarget service = CLIENT.target(BASE_URI);

        T object = service.path(resourcePath).request(CONTENT_TYPE).get(tyoeofClass);
        if (LOG_REQUEST)    System.out.println(object);
        return object;
    }

    public static <T> List<T> GetRequest(String resourcePath, GenericType<List<T>> typeofCollection) {
        
        WebTarget service = CLIENT.target(BASE_URI);

        List<T> list = service.path(resourcePath).request(CONTENT_TYPE).get(typeofCollection);
        if (LOG_REQUEST)    System.out.println(list);
        return list;
    }
    
    public static <T> T PostRequest(String resourcePath, Class<T> tyoeofClass, Entity<?> entity) {

        WebTarget service = CLIENT.target(BASE_URI);
        T object = service.path(resourcePath).request(CONTENT_TYPE).post(entity, tyoeofClass);
        if (LOG_REQUEST)    System.out.println(object);
        return object;
    }
    
    public static <T> void PostRequest(String resourcePath, GenericType<List<T>> typeofCollection, Entity<?> entity) {

        WebTarget service = CLIENT.target(BASE_URI);
        service.path(resourcePath).request(CONTENT_TYPE).post(entity, typeofCollection);
    }

    public static Response PutRequest(String resourcePath, Entity<?> entity) {

        WebTarget service = CLIENT.target(BASE_URI);
        Response response = service.path(resourcePath).request(CONTENT_TYPE).put(entity);
        if (LOG_REQUEST)    System.out.println(response);
        return response;
    }

    public static <T> void PutRequest(String resourcePath, GenericType<List<T>> typeofCollection, Entity<?> entity) {

        WebTarget service = CLIENT.target(BASE_URI);
        service.path(resourcePath).request(CONTENT_TYPE).put(entity, typeofCollection);
    }
    
    public static Response DeleteRequest(String resourcePath) {
        
        WebTarget service = CLIENT.target(BASE_URI);

        Response response = service.path(resourcePath).request(CONTENT_TYPE).delete();
        if (LOG_REQUEST)    System.out.println(response);
        return response;
    }

    public static boolean getBoolean(String data) {
        if (data != null) {
            return data.equals("CHECKED");
        }
        return false;
    }

    public static String getString(boolean state) {
        return state ? "CHECKED" : null;
    }
    
    public static Integer StringToInt(String data) {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return null;            
        }
    }

    public static String IntToString(int value) {
        return Integer.toString(value);
    }
    
    public static String getQuestionScene(String scene) {

        switch (scene) {
            case "CHOICE" : scene = "Choice.fxml"; break;
            case "SELECTION" : scene = "Selection.fxml"; break;
            case "REORDER" : scene = "Reorder.fxml"; break;
            case "ANSWER" : scene = "Answer.fxml"; break;
        }

        return String.format("/se/newton/testtool/view/%s", scene);
    }
    
    public static void switchScene(ActionEvent ev, URL url) {
        
        try {
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("[selectScene}: File not found");
            System.exit(-1);
        }
    }
    
    public static void showMessage(String title, String msg) {
        Alert dialog = new Alert(AlertType.INFORMATION);

        dialog.setHeaderText(null);
        dialog.setTitle(title);
        dialog.setContentText(msg);
        dialog.showAndWait();
    }
}
