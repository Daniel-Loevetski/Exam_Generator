package Java_Project_For_DB;

import java.util.Objects;

public class MultiChoiceQuestion extends Question {
	private final Java_Project_For_DB.Set<MultiChoiceAnswer> multiAnswers;

	public MultiChoiceQuestion(String questionBody) {
		super(questionBody);
		multiAnswers = new Set<MultiChoiceAnswer>();
	}
	
	public MultiChoiceQuestion(String questionBody,Set<MultiChoiceAnswer> ans)
	{
		super(questionBody);
		multiAnswers = new Set<MultiChoiceAnswer>(ans);
	}
	
	public MultiChoiceQuestion(MultiChoiceQuestion multiQuestion)
	{
		super(multiQuestion.getQuestionBody());
		multiAnswers = new Set<MultiChoiceAnswer>(multiQuestion.getMultiAnswers());
	}

	public MultiChoiceQuestion(MultiChoiceQuestion multiChoiceQuestion, int[] answers) {
		super(multiChoiceQuestion.getQuestionBody());
		multiAnswers = new Set<MultiChoiceAnswer>();
		for (int answer : answers)
			if (answer != -1)
				this.addAnswer(multiChoiceQuestion.multiAnswers.getByIndex(answer));
					
		this.addAnswer("All the answers are wrong!", checkForNoRights());
		
		this.addAnswer("More than one answer is correct!", checkForMoreThanOne());
	
	}

	public int getSize() {
		return multiAnswers.size();
	}
	public Set<MultiChoiceAnswer> getMultiAnswers() {
		return multiAnswers;
	}

	public void addAnswer(String answerBody, boolean isTrue) {
		multiAnswers.Push(new MultiChoiceAnswer(answerBody, isTrue));
	}

	public void addAnswer(MultiChoiceAnswer answer) {
		multiAnswers.Push(answer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(multiAnswers);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MultiChoiceQuestion other = (MultiChoiceQuestion) obj;
		return Objects.equals(multiAnswers, other.multiAnswers);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n   Answers:\n ");
		for (int index = 0; index < multiAnswers.size(); index++)
			if (multiAnswers.getByIndex(index) != null)
				sb.append(" \t").append(index + 1).append(") ").append(multiAnswers.getByIndex(index).toString()).append("\n ");
		return sb.toString();
	}

	@Override
	public void editAnswer(String newBody, boolean isTrue, int id) {
		multiAnswers.getByIndex(id-1).edit(newBody, isTrue);
	}

	@Override
	public void deleteAnswer(int id) {
		multiAnswers.delete(id);
	}

	private boolean checkForMoreThanOne() {
		int count = 0;
		for (int i = 0; i < multiAnswers.size(); i++) {
			if (multiAnswers.getByIndex(i) != null && multiAnswers.getByIndex(i).isTrue()){
					count++;
				}
		}
		if (count > 1) {
			for (int i = 0; i < multiAnswers.size(); i++) {
				if(multiAnswers.getByIndex(i) != null)
					multiAnswers.getByIndex(i).setIsTrue(false);
			}
			return true;
		}
		return false;
	}

	private boolean checkForNoRights() {
		for (MultiChoiceAnswer multiChoiceAnswer : multiAnswers) {
			if (multiChoiceAnswer != null && multiChoiceAnswer.isTrue()){
					return false;
				}
		}
		return true;
	}
}
