package em.topquiz.org.model;

import java.util.Collections;
import java.util.List;

/*
 *   Created by ${user} on 10/11/2019.
 */public class QuestionBank {

     List<Question> mQuestionList;
     int mNextQuestionIndex;

     public QuestionBank(List<Question> questionList){
         this.mQuestionList = questionList;

         Collections.shuffle(mQuestionList);

         mNextQuestionIndex = 0;
     }

     public  Question getQuestion(){
         // ensure we loop over the question
         if(mNextQuestionIndex == mQuestionList.size()){
             mNextQuestionIndex=0;
         }

         return mQuestionList.get(mNextQuestionIndex++);
     }


}
