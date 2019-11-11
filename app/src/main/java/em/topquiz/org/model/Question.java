package em.topquiz.org.model;

/*
 *   Created by ${user} on ${date}.
 */

import java.util.List;

public class Question {
    // une question posée a une liste de reponse possible et un index de la bonne reponse
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex){
        this.mQuestion = question;
        this.mChoiceList = choiceList;
        this.mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
