package engine.entity;

public class Answer {
    boolean success;
    String feedback;

    public Answer(boolean success) {
        this.success = success;
        feedback = setFeedback();
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getFeedback() {
        return feedback;
    }
    public String setFeedback() {
        return success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }
}