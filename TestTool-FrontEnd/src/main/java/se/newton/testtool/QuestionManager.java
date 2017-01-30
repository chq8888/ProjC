package se.newton.testtool;

import java.util.List;
import se.newton.testtool.model.Question;

public class QuestionManager {

    private int index = 0;
    private List<Question> questions;

    public QuestionManager(List<Question> q) {
        questions = q;
    }

    public boolean isStart() {
        return index == 0 ? true : false;
    }

    public boolean isEnd() {
        return index == questions.size() - 1 ? true : false;
    }

    public Question getNextQuestion() {
        if (index < questions.size())
            index = index + 1;
        else 
            index = questions.size() - 1;
        
        return questions.get(index);
    }

    public Question getPrevQuestion() {
        if (index > -1)
            index = index - 1;
        else 
            index = 0;
        
        return questions.get(index);
    }

    public Question getCurrentQuestion() {
        return questions.get(index);
    }
    
    public int getTotalOfQuestion() {
        return questions.size();
    }
    
    public int getQuestionNr() {
        return index + 1;
    }
}
