package se.newton.testtool.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class CreateTestController implements Initializable {

    @FXML private TableView<Test> tblTest;
    @FXML private TableView<Question> tblQuestion;
    @FXML private TableView<Answer> tblAnswer;

    @FXML private TableColumn<Test, Integer> tId;
    @FXML private TableColumn<Test, String> tTitle;
    @FXML private TableColumn<Test, String> tOpenDate;
    @FXML private TableColumn<Test, String> tCloseDate;
    @FXML private TableColumn<Test, String> tTimer;
    @FXML private TableColumn<Test, Integer> tGrade;
    @FXML private TableColumn<Test, Integer> tTotalScore;
    @FXML private TableColumn<Test, Boolean> tShowAnswer;
    @FXML private TableColumn<Test, Boolean> tDone;

    @FXML private TableColumn<Question, Integer> qId;
    @FXML private TableColumn<Question, Integer> qScore;
    @FXML private TableColumn<Question, String> qTitle;
    @FXML private TableColumn<Question, String> qCategory;

    @FXML private TableColumn<Answer, Integer> aId;
    @FXML private TableColumn<Answer, String> aTitle;
    @FXML private TableColumn<Answer, String> aAnsStudent;
    @FXML private TableColumn<Answer, String> aAnsTeacher;

    @FXML private ComboBox<String> cbqCategory;    
    @FXML private TextField txttTitle;
    @FXML private TextField txttCloseDate;
    @FXML private TextField txttOpenDate;
    @FXML private TextField txtqTitle;
    @FXML private TextField txtaTitle;
    @FXML private TextField txtaAnsTeacher;
    @FXML private CheckBox chkShowAnswer;

    @FXML private Button btntSave;
    @FXML private Button btnqSave;
    @FXML private Button btnaSave;
    @FXML private Button btnMenu;

    private ObservableList<Test> lstTest;
    private ObservableList<Question> lstQuestion;
    private ObservableList<Answer> lstAnswer;

    private Test test;
    private Question question;
    private Answer answer;
    
    /**+
     * Constructor
     */
    public CreateTestController() {
        // make a GET request to webservice to get a list of test
       lstTest = FXCollections.observableArrayList( Util.GetRequest("/tests", new GenericType<List<Test>>() { }) );
    }
    
    /**+
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbqCategory.setItems(FXCollections.observableArrayList("SELECTION", "CHOICE", "REORDER", "ANSWER"));
        
        tId.setCellValueFactory(new PropertyValueFactory<>("tId"));
        tTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tOpenDate.setCellValueFactory(new PropertyValueFactory<>("openDate"));
        tCloseDate.setCellValueFactory(new PropertyValueFactory<>("closeDate"));
        tTimer.setCellValueFactory(new PropertyValueFactory<>("timer"));
        tTotalScore.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        tGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tShowAnswer.setCellValueFactory(new PropertyValueFactory<>("showAnswer"));
        tDone.setCellValueFactory(new PropertyValueFactory<>("done"));

        qId.setCellValueFactory(new PropertyValueFactory<>("qId"));
        qTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        qCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        qScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        aId.setCellValueFactory(new PropertyValueFactory<>("aId"));
        aTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        aAnsStudent.setCellValueFactory(new PropertyValueFactory<>("ansStudent"));
        aAnsTeacher.setCellValueFactory(new PropertyValueFactory<>("ansTeacher"));
        


        tblTest.setEditable(false);
        tblQuestion.setEditable(false);
        tblAnswer.setEditable(false);

        tblTest.setItems(lstTest);
        //tblQuestion.setItems(lstQuestion);
        //tblAnswer.setItems(lstAnswer);

        
        
        // test select row event handler
        tblTest.getSelectionModel().selectedItemProperty().addListener(
            (obsList, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    test = (Test) newSelection;
                    tblAnswer.setItems(null);

                    List<Question> questions = Util.GetRequest("/questions/tests/" + test.gettId(), new GenericType<List<Question>>() { });                   

                    lstQuestion = questions == null ? null : FXCollections.observableArrayList(questions);
                    tblQuestion.setItems(lstQuestion);
                    
                    question = null;
                    answer = null;
                } 
        });
        
        // question select row event handler
        tblQuestion.getSelectionModel().selectedItemProperty().addListener(
            (obsList, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    question = (Question) newSelection;
                    
                    List<Answer> answers = Util.GetRequest("/answers/questions/" + question.getqId(), new GenericType<List<Answer>>() { });

                    lstAnswer = ( answers == null ? null : FXCollections.observableArrayList(answers) );
                    tblAnswer.setItems(lstAnswer);
                    
                    answer = null;
                }
        });

        // answer select row event handler
        tblAnswer.getSelectionModel().selectedItemProperty().addListener(
            (obsList, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    answer = (Answer) newSelection;                    
                }
        });
        
        
        
        btntSave.setOnAction(ev -> {
            test = new Test(txttTitle.getText(), txttOpenDate.getText(), txttCloseDate.getText(), null, null, chkShowAnswer.isSelected(), false, 0, 0);
            test = Util.PostRequest("/tests/create", Test.class, Entity.entity(test, MediaType.APPLICATION_JSON));

            // check if test is create
            if (test.gettId() != 0) {
                if (lstTest == null) {
                    lstTest = FXCollections.observableArrayList();
                    tblTest.setItems(lstTest);
                }
                lstTest.add(test);
            }
            
            // reset question and answer to null value
            question = null;
            answer = null;
            test = null;
        });

        btnqSave.setOnAction(ev -> {
            if (test == null) {
                Util.showMessage("Test missing", "Please create a new test or select an existing test first");
                return;
            }
            
            question = new Question(txtqTitle.getText(), cbqCategory.getSelectionModel().getSelectedItem(), 0);
            question = Util.PostRequest("/questions/create/" + test.gettId(), Question.class, Entity.entity(question, MediaType.APPLICATION_JSON));
                
            // check if question is create
            if (question.getqId() != 0) {
                if (lstQuestion == null) {
                    lstQuestion = FXCollections.observableArrayList();
                    tblQuestion.setItems(lstQuestion);
                }
                lstQuestion.add(question);                
            }
            
            // reset question and answer to null value
            question = null;
            answer = null;
        });

        btnaSave.setOnAction(ev -> {
            if (question == null) {
                Util.showMessage("Question missing", "Please create a new question or select an existing question first");
                return;
            }
            
            answer = new Answer(txtaTitle.getText(), null, txtaAnsTeacher.getText());
            answer = Util.PostRequest("/answers/create/" + question.getqId(), Answer.class, Entity.entity(answer, MediaType.APPLICATION_JSON));

            if (answer.getaId() != 0) {
                if (lstAnswer == null) {
                    lstAnswer = FXCollections.observableArrayList();
                    tblAnswer.setItems(lstAnswer);
                }
                lstAnswer.add(answer);
            }
            
            // reset answer to null value
            answer = null;                
        });
        
        btnMenu.setOnAction(ev -> {
            Util.switchScene(ev, getClass().getResource("/se/newton/testtool/view/Action.fxml"));
        });        
    }
}
