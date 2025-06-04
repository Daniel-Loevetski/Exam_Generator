package Java_Project_For_DB;

import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {
	private String questionBody;
	private static int auto = 1;
	private int serialId;

	public Question(String questionBody) {
		this.questionBody = questionBody;
		serialId = auto++;
	}

	public boolean setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
		return Objects.equals(this.questionBody, questionBody);
	}

	public String getQuestionBody() {
		return questionBody;
	}

	public int getSerialId() {
		return serialId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionBody, serialId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(questionBody, other.questionBody) && serialId == other.serialId;
	}

	@Override
	public String toString() {
		return " Question: " + questionBody;

	}

	public void editAnswer(String newBody) {
		//method empty because it is not needed in this class
	}

	public void editAnswer(String newBody, boolean isTrue,int id) {
		//method empty because it is not needed in this class
	}

	public void deleteAnswer() {
		//method empty because it is not needed in this class
	}

	public void deleteAnswer(int id) {
		//method empty because it is not needed in this class
	}
}
