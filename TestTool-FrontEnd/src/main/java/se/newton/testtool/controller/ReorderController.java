package se.newton.testtool.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class ReorderController implements Initializable {

    @FXML private Label lblQuestion;
    @FXML private Label lblPageNr;
    @FXML private ListView<Answer> lvUnOrder;
    @FXML private ListView<Answer> lvOrder;
    @FXML private Button btnToRight;
    @FXML private Button btnToLeft;
    @FXML private Button btnNext;
    @FXML private Button btnPrev;

    private final QuestionManager qm;
    private final Question question;
    private List<Answer> answers;
    private ObservableList<Answer> oblUnOrder, oblOrder;
    private final GenericType<List<Answer>> collectionType;
    private boolean modified;

    public ReorderController() {
        qm = TestToolApp.qManager;
        question = qm.getCurrentQuestion();
        collectionType = new GenericType<List<Answer>>() { };
        modified = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lvUnOrder.setCellFactory(new Callback<ListView<Answer>, ListCell<Answer>>() {

            @Override
            public ListCell<Answer> call(ListView<Answer> param) {

                ListCell<Answer> cell = new ListCell<Answer>() {

                    @Override
                    protected void updateItem(Answer a, boolean empty) {
                        super.updateItem(a, empty);
                        if (a != null) {
                            setText(a.getTitle());
                        } else {
                            setText("");	// require the clear if empty
                        }
                    }
                };
                return cell;
            }
        });

        lvOrder.setCellFactory(new Callback<ListView<Answer>, ListCell<Answer>>() {

            @Override
            public ListCell<Answer> call(ListView<Answer> param) {
                ListCell<Answer> cell = new ListCell<Answer>() {
                    @Override
                    protected void updateItem(Answer a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getTitle());
                        } else {
                            setText("");	// require the clear if empty
                        }
                    }
                };
                return cell;
            }
        });

        btnNext.setOnAction(ev -> {
            // send back answer to webservice
            registerAnswer();
            if (!qm.isEnd()) {
                // switch to next scene base on category of the next question
                Util.switchScene(ev, getClass().getResource(Util.getQuestionScene(qm.getNextQuestion().getCategory())));
            } else
                // switch to home scene
                Util.switchScene(ev, getClass().getResource("/se/newton/testtool/view/Action.fxml"));
        });

        btnPrev.setOnAction(ev -> {
            // send back answer to webservice
            registerAnswer();
            if (!qm.isStart()) {
                // switch to prev scene base on category of the prev question
                Util.switchScene(ev, getClass().getResource(Util.getQuestionScene(qm.getPrevQuestion().getCategory())));
            } else
                // switch to home scene
                Util.switchScene(ev, getClass().getResource("/se/newton/testtool/view/Action.fxml"));
        });

        btnToRight.setOnAction(ev -> {
            Answer selItem = (Answer) lvUnOrder.getSelectionModel().getSelectedItem();

            if (selItem != null) {
                oblUnOrder.remove(selItem);
                oblOrder.add(selItem);
                modified = true;
            }
        });

        btnToLeft.setOnAction(ev -> {
            Answer selItem = (Answer) lvOrder.getSelectionModel().getSelectedItem();

            if (selItem != null) {
                oblOrder.remove(selItem);
                oblUnOrder.add(selItem);
                modified = true;
            }
        });

        if (qm.isStart()) {
            // change next button to Hem if first question
            btnPrev.setText("Hem");        
        }
        if (qm.isEnd()) {
            // change next button to Klar if last question
            btnNext.setText("Klar");
        }

        initiateScene();
    }

    private void initiateScene() {

        // make a GET request to webservice with a questionId
        GenericType<List<Answer>> collectionType = new GenericType<List<Answer>>(){};
        answers = Util.GetRequest("answers/questions/" + question.getqId(), collectionType);

        // show the question
        lblQuestion.setText(question.getTitle());

        List<Answer> choiceAnswers = getSelectedAnswers(answers, true);
        answers = getSelectedAnswers(answers, false);

        if (choiceAnswers != null) {
            choiceAnswers.sort(new Comparator<Answer>() {
                @Override
                public int compare(Answer a1, Answer a2) {
                    return Integer.parseInt(a1.getAnsStudent()) > Integer.parseInt(a2.getAnsStudent()) ? 1 : -1;
                }
            });
        }

        oblUnOrder = ( answers == null ? FXCollections.observableArrayList() : FXCollections.observableArrayList(answers) );
        oblOrder = ( choiceAnswers == null ? FXCollections.observableArrayList() : FXCollections.observableArrayList(choiceAnswers) );

        // binding ListView to ObservableList
        lvUnOrder.setItems(oblUnOrder);
        lvOrder.setItems(oblOrder);
        
        // set page nr count
        lblPageNr.setText(qm.getQuestionNr() + " / " + qm.getTotalOfQuestion());
    }

    private void registerAnswer() {
        if (modified) {
            answers = getUpdateAnswers();
            Util.PutRequest("/answers/update/list", collectionType, Entity.entity(answers, MediaType.APPLICATION_JSON));
        }
    }
    
    private List<Answer> getSelectedAnswers(List<Answer> ans, boolean choice) {
        List<Answer> selAnswers = new ArrayList<>();
        
        for (Answer a : ans) {
            if (choice && a.getAnsStudent() != null) 
                selAnswers.add(a);
            else if (!choice && a.getAnsStudent() == null) 
                selAnswers.add(a);
        }
        
        return selAnswers.isEmpty() ? null : selAnswers;
    }
       
    private List<Answer> getUpdateAnswers() {
        Answer ans;
        answers = new ArrayList<>();
        
        for (int i=0; i < oblOrder.size(); i++) {
            ans = oblOrder.get(i);
            ans.setAnsStudent(Integer.toString(i+1));
            answers.add(ans);
        }
        
        for (int i=0; i < oblUnOrder.size(); i++) {
            ans = oblUnOrder.get(i);
            ans.setAnsStudent(null);
            answers.add(ans);
        }
        
        return answers;
    }
}
