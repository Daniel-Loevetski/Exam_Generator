package Java_Project_For_DB;

import java.util.ArrayList;
import java.util.Objects;


public class Manager {
	private final ArrayList<Question> questions;
	private final ArrayList<Test> tests;

	public Manager() {
		questions = new ArrayList<>();
		tests = new ArrayList<>();
	}
	
	public void addTest(Test oldTest) {
		Test newTest = new Test(oldTest);
		tests.add(newTest);
	}
	
	public Test getTest(int index) {
		return tests.get(index);
	}
	
	public ArrayList<Question> getQuestionsArray() {
		return questions;
	}
	public ArrayList<Test> getTestsArray() {
		return tests;
	}
	
	public int getQuestionsSize() {
		return questions.size();
	}
	
	public int getTestsSize() {
		return tests.size();
	}
	public String showQuestionsAnswers() {
		new Sort().quickSort(questions, 0, questions.size() - 1);
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < questions.size(); index++) {
			sb.append(index + 1).append(")").append(questions.get(index).toString()).append("\n");
		}
		return sb.toString();
	}

	public boolean areQuestionBodiesEqual(Question otherQuestion) {
		for (Question question : questions) {
			if (question != null)
				if ((question.getQuestionBody().compareTo(otherQuestion.getQuestionBody())) == 0)
					if (question.getClass().getSimpleName().equals(otherQuestion.getClass().getSimpleName()))
						return true;
		}
		return false;
	}

	public void addAnswerToMultiChoiceQuestion(String ansBody, boolean isTrue) {
		if (questions.get(questions.size()-1) instanceof MultiChoiceQuestion) {
			((MultiChoiceQuestion)questions.get(questions.size()-1)).addAnswer(new MultiChoiceAnswer(ansBody, isTrue));
		}
	}

	public void addOpenQuestion(String questionBody, Answer answer) {
		OpenQuestion openQuestion = new OpenQuestion(questionBody, answer);
		if (areQuestionBodiesEqual(openQuestion)) {
			System.out.println("This question already exists in the question list and wasn't added! \n");
		} else {
			questions.add(openQuestion);
			
		}
	}

	public void addOpenQuestion(OpenQuestion openQuestion) {
		if (areQuestionBodiesEqual(openQuestion)) {
			System.out.println("This question already exists in the question list and wasn't added! \n");
		} else {
			questions.add(new OpenQuestion(openQuestion));
		}
	}

	public void addMultiChoiceQuestion(String questionBody) {
		MultiChoiceQuestion multiQuestion = new MultiChoiceQuestion(questionBody);
		if (areQuestionBodiesEqual(multiQuestion)) {
			System.out.println("This question already exists in the question list and wasn't added! \n");
		} else {
			questions.add(multiQuestion);
			
		}
	}

	public void addMultiChoiceQuestion(MultiChoiceQuestion multiQuestion) {
		if (areQuestionBodiesEqual(multiQuestion)) {
			System.out.println("This question already exists in the question list and wasn't added! \n");
		} else {
			questions.add(new MultiChoiceQuestion(multiQuestion));
			
			
		}
	}

	public String printOnlyQuestions() {
		StringBuilder sb = new StringBuilder();
		for (Question question : questions) {
			sb.append(question.getSerialId()).append(") ").append(question.getQuestionBody()).append("\n");
		}
		return sb.toString();
	}

	public boolean editQuestion(int theId, String editQuestion) {
		return questions.get(theId-1).setQuestionBody(editQuestion);
	}

	public void editQuestionAnswer(Question question, String newAnswerBody) {
		question.editAnswer(newAnswerBody);
	}

	public void editQuestionAnswer(Question question, String newAnswerBody, boolean isTrue, int id) {
		question.editAnswer(newAnswerBody, isTrue, id);
	}

	public Question getQuestionById(int id) {
		return questions.get(id);

	}

	public void deleteAnswer(Question q, int id) {
		if (id == -1) {
			q.deleteAnswer();
		} else {
			q.deleteAnswer(id - 1);
		}
	}
	
	public void addQuestionToTest(Test test, Question q) {
        test.addQuestion(q);
    }
	
	public boolean isQuestionInTest(Test test, Question q) {
		for (int i = 0; i < test.getQuestionsAmount(); i++) {
			if(q != null && Objects.equals(test.getQuestions().get(i).getQuestionBody(), q.getQuestionBody())) {
					return true;
			}
		}
		return false;
	}

}
