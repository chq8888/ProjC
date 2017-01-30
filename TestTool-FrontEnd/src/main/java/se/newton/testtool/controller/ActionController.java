package se.newton.testtool.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import javafx.scene.control.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class ActionController implements Initializable {

    @FXML private ComboBox<Test> cbTests;
    @FXML private Button btnOk;
    @FXML private Button btnCreate;
    @FXML private Button btnSample;

    QuestionManager qm;
    List<Question> questions;
    List<Test> tests;
    ObservableList<Test> lstTests;

    public ActionController() {
        tests = Util.GetRequest("/tests", new GenericType<List<Test>>() { });      

        if (tests != null)
            lstTests = FXCollections.observableArrayList(tests);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbTests.setItems(lstTests);
        //cbTests.getSelectionModel().selectFirst();

        cbTests.setCellFactory(new Callback<ListView<Test>, ListCell<Test>>() {

            @Override
            public ListCell<Test> call(ListView<Test> p) {

                final ListCell<Test> cell = new ListCell<Test>() {

                    @Override
                    protected void updateItem(Test t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) 
                            setText(t.getTitle());
                        else 
                            setText(null);
                    }
                };
                return cell;
            }
        });

//        btnOk.setOnAction(ev -> {
//
//            Test test = cbTests.getSelectionModel().getSelectedItem();
//            
//            // make a GET request to webservice to get a list of questions
//            questions = Util.GetRequest("/questions/tests/" + test.gettId(), new GenericType<List<Question>>() { });

//            GenericType<List<Test>> collectionType = new GenericType<List<Test>>() { };
//                List<Test> t = Util.GetRequest("/tests", collectionType);
//                String x="s";
                
//
//////            GenericType<List<User>> collectionType = new GenericType<List<User>>() { };
//////            List<User> us = Util.GetRequest("/users", collectionType);
////            
////            //User us = Util.GetRequest("/users/1", User.class);  
////            
////            // assign to QuestionManager to handle the question
////            TestToolApp.qManager = new QuestionManager(questions);
////            qm = TestToolApp.qManager;
////
////            // switch to a specifik scene base om the first question
////            Util.switchScene(ev, getClass().getResource(Util.getQuestionScene(qm.getCurrentQuestion().getCategory())));
 //       });

        btnOk.setOnAction(ev -> {

            if (cbTests.getSelectionModel().getSelectedIndex() != -1) {
                Test test = cbTests.getSelectionModel().getSelectedItem();

                // make a GET request to webservice to get a list of questions
                questions = Util.GetRequest("/questions/tests/" + test.gettId(), new GenericType<List<Question>>() { });

                // assign to QuestionManager to handle the question
                TestToolApp.qManager = new QuestionManager(questions);
                qm = TestToolApp.qManager;

                // switch to a specifik scene base om the first question
                Util.switchScene(ev, getClass().getResource(Util.getQuestionScene(qm.getCurrentQuestion().getCategory())));
            }
        });
        
        btnCreate.setOnAction(ev -> {
            Util.switchScene(ev, getClass().getResource("/se/newton/testtool/view/CreateTest.fxml"));
        });
        
        btnSample.setOnAction(ev -> {
            Util.PostRequest("/createsampletest", Test.class, Entity.entity(new Test("Sample"), MediaType.APPLICATION_JSON));
            
            tests = Util.GetRequest("/tests", new GenericType<List<Test>>() { });
            
            if (tests != null) {
                lstTests = FXCollections.observableArrayList(tests);
                cbTests.setItems(lstTests);        
            }
        });
    }
}
