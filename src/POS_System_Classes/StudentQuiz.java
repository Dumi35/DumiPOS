package POS_System_Classes;


public class StudentQuiz {
    private String quizName;
    private String quizDuration;
    //private String quizDescription;
    
    public StudentQuiz(String quizName, String quizDuration){
        this.quizName = quizName;
        this.quizDuration = quizDuration;
    }
        
    public String getQuizName(){
        return quizName;
    }
    public String getQuizDuration(){
        return quizDuration;
    }
    

}
