package se.newton.testtool.controller;

import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class SelectionController implements Initializable {

    @FXML private Label lblQuestion;
    @FXML private Label lblPageNr;
    @FXML private RadioButton rbAnswer1;
    @FXML private RadioButton rbAnswer2;
    @FXML private RadioButton rbAnswer3;
    @FXML private RadioButton rbAnswer4;
    @FXML private Button btnNext;
    @FXML private Button btnPrev;

    private final QuestionManager qm;
    private final Question question;
    private List<Answer> answers;
    private final GenericType<List<Answer>> collectionType;
    private boolean modified;
    
    public SelectionController() {
        qm = TestToolApp.qManager;
        question = qm.getCurrentQuestion();
        collectionType = new GenericType<List<Answer>>() { };
        modified = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rbAnswer1.setOnAction(ev -> {

            modified = true;
            rbAnswer2.setSelected(false);
            rbAnswer3.setSelected(false);
            rbAnswer4.setSelected(false);

            answers.get(0).setAnsStudent(Util.getString(true));
            answers.get(1).setAnsStudent(Util.getString(false));
            answers.get(2).setAnsStudent(Util.getString(false));
            answers.get(3).setAnsStudent(Util.getString(false));

        });

        rbAnswer2.setOnAction(ev -> {

            modified = true;
            rbAnswer1.setSelected(false);
            rbAnswer3.setSelected(false);
            rbAnswer4.setSelected(false);

            answers.get(0).setAnsStudent(Util.getString(false));
            answers.get(1).setAnsStudent(Util.getString(true));
            answers.get(2).setAnsStudent(Util.getString(false));
            answers.get(3).setAnsStudent(Util.getString(false));
        });

        rbAnswer3.setOnAction(ev -> {

            modified = true;
            rbAnswer1.setSelected(false);
            rbAnswer2.setSelected(false);
            rbAnswer4.setSelected(false);

            answers.get(0).setAnsStudent(Util.getString(false));
            answers.get(1).setAnsStudent(Util.getString(false));
            answers.get(2).setAnsStudent(Util.getString(true));
            answers.get(3).setAnsStudent(Util.getString(false));
        });

        rbAnswer4.setOnAction(ev -> {

            modified = true;
            rbAnswer1.setSelected(false);
            rbAnswer2.setSelected(false);
            rbAnswer3.setSelected(false);

            answers.get(0).setAnsStudent(Util.getString(false));
            answers.get(1).setAnsStudent(Util.getString(false));
            answers.get(2).setAnsStudent(Util.getString(false));
            answers.get(3).setAnsStudent(Util.getString(true));
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
            rbAnswer1.setText(answers.get(0).getTitle());
            rbAnswer2.setText(answers.get(1).getTitle());
            rbAnswer3.setText(answers.get(2).getTitle());
            rbAnswer4.setText(answers.get(3).getTitle());
            
            rbAnswer1.setSelected(Util.getBoolean(answers.get(0).getAnsStudent()));
            rbAnswer2.setSelected(Util.getBoolean(answers.get(1).getAnsStudent()));
            rbAnswer3.setSelected(Util.getBoolean(answers.get(2).getAnsStudent()));
            rbAnswer4.setSelected(Util.getBoolean(answers.get(3).getAnsStudent()));
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
