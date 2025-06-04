package Java_Project_For_DB;

import java.io.Serializable;
import java.util.Objects;

public class Answer implements Serializable {
	private String answerBody;
	
	public Answer(String answerBody) {
		this.answerBody = answerBody;
	}

	public String getAnswerBody() {
		return answerBody;
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerBody);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		return Objects.equals(answerBody, other.answerBody);
	}
	
	
	@Override
	public String toString() {
		return answerBody ;
	}
	
	public void edit(String value) {
		this.answerBody = value;

	}
}
