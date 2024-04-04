package com.mvc.quizquiz;

public class Questions {
    private String question;
    private boolean ans;
    public Questions(){}

    public Questions(String question, boolean ans) {
        this.question = question;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAns() {
        return ans;
    }

    public void setAns(boolean ans) {
        this.ans = ans;
    }
}
