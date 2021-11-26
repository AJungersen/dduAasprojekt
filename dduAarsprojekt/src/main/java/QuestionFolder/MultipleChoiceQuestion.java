/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionFolder;

import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class MultipleChoiceQuestion extends Question{
    ArrayList<MultipleChoiceAnswer> answerOptions;

    public MultipleChoiceQuestion(String q,MultipleChoiceAnswer a,MultipleChoiceAnswer b,MultipleChoiceAnswer c,MultipleChoiceAnswer d){
        question = q;
        type = "multiChoice";
        answerOptions = new ArrayList<>();
        answerOptions.add(a);
        
    }
    
}
