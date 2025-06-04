package Java_Project_For_DB;

import java.util.Objects;

public class OpenQuestion extends Question {
	private Answer openAnswer;

	public OpenQuestion(String questionBody, Answer openAnswer) {
		super(questionBody);
		this.openAnswer = openAnswer;
	}
	public OpenQuestion(OpenQuestion openQuestion) {
		
		super(openQuestion.getQuestionBody());
		this.openAnswer = openQuestion.openAnswer;
	}

	public Answer getAnswer() {
		return openAnswer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(openAnswer);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		OpenQuestion other = (OpenQuestion) obj;
		return Objects.equals(openAnswer, other.openAnswer);
	}

	@Override
	public String toString() {
		if (openAnswer != null)
			return super.toString() + "\n Answer: " + openAnswer.toString() + "\n";
		return super.toString();
	}

	@Override
	public void editAnswer(String newBody) {
		this.openAnswer.edit(newBody);
	}

	@Override
	public void deleteAnswer() {
		this.openAnswer = null;
	}
}
