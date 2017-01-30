package se.newton.testtool.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.ws.rs.client.Entity;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class ChoiceController implements Initializable {

    @FXML private Label lblQuestion;
    @FXML private Label lblPageNr;
    @FXML private CheckBox chkAnswer1;
    @FXML private CheckBox chkAnswer2;
    @FXML private CheckBox chkAnswer3;
    @FXML private CheckBox chkAnswer4;
    @FXML private Button btnPrev;
    @FXML private Button btnNext;

    private final QuestionManager qm;
    private final Question question;
    private List<Answer> answers;
    private final GenericType<List<Answer>> collectionType;
    private boolean choice;
    private boolean modified;

    public ChoiceController() {
        qm = TestToolApp.qManager;
        question = qm.getCurrentQuestion();
        collectionType = new GenericType<List<Answer>>(){};
        modified = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chkAnswer1.setOnAction(ev -> {
            modified = true;
            choice = ((CheckBox) ev.getSource()).isSelected();
            answers.get(0).setAnsStudent(Util.getString(choice));
        });

        chkAnswer2.setOnAction(ev -> {
            modified = true;
            choice = ((CheckBox) ev.getSource()).isSelected();
            answers.get(1).setAnsStudent(Util.getString(choice));
        });

        chkAnswer3.setOnAction(ev -> {
            modified = true;
            choice = ((CheckBox) ev.getSource()).isSelected();
            answers.get(2).setAnsStudent(Util.getString(choice));
        });

        chkAnswer4.setOnAction(ev -> {
            modified = true;
            choice = ((CheckBox) ev.getSource()).isSelected();
            answers.get(3).setAnsStudent(Util.getString(choice));
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
        answers = Util.GetRequest("answers/questions/" + question.getqId(), collectionType);

        // show the question
        lblQuestion.setText(question.getTitle());

        // show difference answer
        if (answers != null) {
            chkAnswer1.setText(answers.get(0).getTitle());
            chkAnswer2.setText(answers.get(1).getTitle());
            chkAnswer3.setText(answers.get(2).getTitle());
            chkAnswer4.setText(answers.get(3).getTitle());
            
            chkAnswer1.setSelected(Util.getBoolean(answers.get(0).getAnsStudent()));
            chkAnswer2.setSelected(Util.getBoolean(answers.get(1).getAnsStudent()));
            chkAnswer3.setSelected(Util.getBoolean(answers.get(2).getAnsStudent()));
            chkAnswer4.setSelected(Util.getBoolean(answers.get(3).getAnsStudent()));
        }

        // set page nr count
        lblPageNr.setText(qm.getQuestionNr() + " / " + qm.getTotalOfQuestion());
    }

    private void registerAnswer() {
        
        if (modified) {
            Util.PutRequest("/answers/update/list", collectionType, Entity.entity(answers, MediaType.APPLICATION_JSON));
        }
    }
}
