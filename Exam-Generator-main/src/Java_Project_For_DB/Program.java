package Java_Project_For_DB;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.AlreadyConnectedException;

public class Program {

	public static void showAll(Manager manager) {
		System.out.println("The list of questions and their answers: \n");
		System.out.println(manager.showQuestionsAnswers());
	}

	public static void addQuestion(Scanner input, Manager manager) {
		int answerCounter = 0;
		int caseTwoChoice1 = 0;
		char caseTwoChoice2;
		String caseTwoAnswerBody;
		String caseTwoQuestionBody;
		String caseTwoMultiQuestionBody;

		System.out.println("Please choose which question and answers you would like to add:");
		System.out.println("1)Multi Choice Question, add up to 10 answers.");
		System.out.println("2)Open Question, add a single answer.");
		caseTwoChoice1 = input.nextInt();
		while (caseTwoChoice1 < QuestionTypes.MULTI_QUESTION || caseTwoChoice1 > QuestionTypes.OPEN_QUESTION) {
			System.out.println("You entered a wrong number, please choose options 1 or 2");
			caseTwoChoice1 = input.nextInt();
		}
		if (caseTwoChoice1 == QuestionTypes.MULTI_QUESTION) {
			System.out.println("Please enter the question's body:");
			input.nextLine();
			caseTwoMultiQuestionBody = input.nextLine();
			manager.addMultiChoiceQuestion(caseTwoMultiQuestionBody);
			System.out.println("Add at least 4 answers to the added question.");
			for (int Index = 0; Index < 4; Index++) {
				addMultiChoiceQuestion(input, manager);
				answerCounter++;
			}
			System.out.println("You have the Minimum amount of answers needed.");
			do {
				System.out.println(
						"Would you like to add another answer to the question? Type Y/y for 'yes' or N/n for 'no':");
				caseTwoChoice2 = input.next().charAt(0);
				while (caseTwoChoice2 != 'y' && caseTwoChoice2 != 'Y' && caseTwoChoice2 != 'n'
						&& caseTwoChoice2 != 'N') {
					System.out.println("Invalid Value />> Please Type Y/y for 'yes' or N/n for 'no':");
					caseTwoChoice2 = input.next().charAt(0);
				}
				if (caseTwoChoice2 == 'y' || caseTwoChoice2 == 'Y') {
					input.nextLine();
					answerCounter++;
					addMultiChoiceQuestion(input, manager);
					if (answerCounter >= 10)
						System.out.println("You have reached the maximum amount of answers to this question!");
				} else {
					System.out.println("You chose 'no' and finish adding answers to the question.");
					System.out.println("Multi choice question added! \n");
				}
			} while (caseTwoChoice2 != 'n' && caseTwoChoice2 != 'N' && answerCounter < 10);
		} else if (caseTwoChoice1 == QuestionTypes.OPEN_QUESTION) {
			input.nextLine();
			System.out.println("Please enter the question's body:");
			caseTwoQuestionBody = input.nextLine();
			System.out.println("Please enter the answer's body:");
			caseTwoAnswerBody = input.nextLine();
			manager.addOpenQuestion(caseTwoQuestionBody, new Answer(caseTwoAnswerBody));
			System.out.println("Open question added! \n");
		}
	}

	public static void editQuestion(Scanner input, Manager manager) {
		int caseThreeId;
		boolean caseThreeFlag;
		String caseThreeStr;
		boolean flagos = true;
		input.nextLine();
		do {
			System.out.println("\t List Of All Your Questions:");
			System.out.println("__");
			if (manager.getQuestionsSize() == 0) {
				System.out.println(
						"There Are No Questions In The Database /> Insert Some Questions And Try Again! \n \t");
				System.out.println("______________________");
				return;
			}
			try {
				System.out.println(manager.printOnlyQuestions());
				System.out.println("Please Choose The Number Of Question You Want To Change /> (From The List Above)");
				caseThreeId = Integer.parseInt(input.nextLine());
				if (caseThreeId < 1 || caseThreeId > manager.getQuestionsSize())
					throw new IndexOutOfBoundsException();
				System.out.print("Type Your New Question Here: ");
				caseThreeStr = input.nextLine();
				caseThreeFlag = manager.editQuestion(caseThreeId, caseThreeStr);
				if (caseThreeFlag)
					System.out.println("The Editing Process Was Completed Successfully \n");// for check
				else
					System.out.println("ERROR \n");
				flagos = false;
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				System.out.println("Invalid Value //> Enter An Integer Number Between [1-" + manager.getQuestionsSize()
						+ "]" + "\n");
			}
		} while (flagos);
	}

	public static void editAnswer(Scanner input, Manager manager) {
		int idq = 0;
		int id = 0;
		String isTrueString = " ";
		boolean flagos = true;
		do {
			showAll(manager);
			try {
				System.out.println("Choose the question number (that own the answer that you want to change):");
				idq = Integer.parseInt(input.nextLine());
				if (idq < 1 || idq > manager.getQuestionsSize())
					throw new IndexOutOfBoundsException();
				flagos = false;
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				System.out.println("Invalid Value //> Enter An Integer Number Between [1-" + manager.getQuestionsSize()
						+ "]" + "\n");
			}
		} while (flagos);
		flagos = true;
		Question q = manager.getQuestionById(idq - 1);
		if (q instanceof OpenQuestion) {
			System.out.println("Please write the new answer body for the open question:");
			String body = input.nextLine();
			manager.editQuestionAnswer(q, body);
		} else if (q instanceof MultiChoiceQuestion) {
			do {
				try {
					System.out.println("Please choose the number of the answer you would like to edit:");
					id = Integer.parseInt(input.nextLine());
					if (id < 1 || id > ((MultiChoiceQuestion) q).getMultiAnswers().size())
						throw new IndexOutOfBoundsException();
					flagos = false;
				} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
					System.out.println("Invalid Value //> Enter An Integer Number Between [1-"
							+ ((MultiChoiceQuestion) q).getMultiAnswers().size() + "]" + "\n");
				}
			} while (flagos);
			flagos = true;
			System.out.println("Please write the new answer body for the Multi choice question:");
			String body = input.nextLine();
			do {
				try {
					System.out.println("Is the new answer 'true' or 'false'?");
					isTrueString = input.nextLine();
					if (!isTrueString.equals("true") && !isTrueString.equals("True") && !isTrueString.equals("False")
							&& !isTrueString.equals("false"))
						throw new InputMismatchException();
					flagos = false;
				} catch (InputMismatchException e) {
					System.out.println("Invalid Syntax //>> Type ONLY 'true' Or 'false'?  ");
				}
			} while (flagos);
			boolean isTrue = isTrueString.equals("true") || isTrueString.equals("True");
			manager.editQuestionAnswer(q, body, isTrue, id);
		}
	}

	public static void deleteAnswer(Scanner input, Manager manager) {
		int id = 0;
		boolean flagos = true;
		int ansNumber = -1;
		input.nextLine();
		do {
			showAll(manager);
			try {
				System.out.println(
						"Choose the question number of the answer that you want to delete(from the list above)");
				id = Integer.parseInt(input.nextLine());
				if (id < 1 || id > manager.getQuestionsSize())
					throw new IndexOutOfBoundsException();
				flagos = false;
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				System.out.println("Invalid Value //> Enter An Integer Number Between [1-" + manager.getQuestionsSize()
						+ "]" + "\n");
			}
		} while (flagos);
		flagos = true;
		Question q = manager.getQuestionById(id - 1);
		if (q instanceof MultiChoiceQuestion) {
			do {
				try {
					System.out.println(
							"Please enter the number of answer you would like to delete(from the list above):");
					ansNumber = Integer.parseInt(input.nextLine());
					if (ansNumber < 1 || ansNumber > ((MultiChoiceQuestion) q).getMultiAnswers().size())
						throw new IndexOutOfBoundsException();
					flagos = false;
				} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
					System.out.println("Invalid Value //> Enter An Integer Number Between [1-"
							+ ((MultiChoiceQuestion) q).getMultiAnswers().size() + "]" + "\n");
				}
			} while (flagos);
			manager.deleteAnswer(q, ansNumber);
		}
		if (q instanceof OpenQuestion) {
			manager.editQuestionAnswer(q, "<Empty Answer Body>");
		}
	}

	public static void createManual(Scanner input, Manager manager) {
		boolean flagos = true;
		int answerId = 0;
		input.nextLine();
		int id = 0;
		Question question;
		int amount = 0;
		do {
			try {
				System.out.println("The total amount of questions available: " + (manager.getQuestionsSize()));
				System.out.println("How many questions would you like to choose?");
				amount = Integer.parseInt(input.nextLine());
				if (amount < 1 || amount > manager.getQuestionsSize())
					throw new IndexOutOfBoundsException();
				flagos = false;
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				System.out.println(
						"Invalid Value //> Enter An Integer Number between [1- " + manager.getQuestionsSize() + "]");
			}
		} while (flagos);
		flagos = true;
		char charIndex = 'a';
		Test test = new Test(amount, false);
		for (int i = 0; i < amount; i++) {
			showAll(manager);
			do {
				try {
					System.out.println("Which question would you like to pick for the test? ");
					id = Integer.parseInt(input.nextLine());
					question = manager.getQuestionById(id - 1);
					if (id < 1 || id > manager.getQuestionsSize())
						throw new IndexOutOfBoundsException();
					if (manager.isQuestionInTest(test, question))
						throw new AlreadyConnectedException();
					flagos = false;
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid Value //> Enter An Integer Number between [1- "
							+ manager.getQuestionsSize() + "]");
				} catch (AlreadyConnectedException e) {
					System.out.println("This Question Is Already In The Test //>> choose another value between [1- "
							+ manager.getQuestionsSize() + "]");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid Value //> Enter  An Integer Number between [1- "
							+ manager.getQuestionsSize() + "]");
				}
			} while (flagos);
			flagos = true;
			question = manager.getQuestionById(id - 1);
			if (question instanceof OpenQuestion) {
				System.out.println("Adding Open");
				manager.addQuestionToTest(test, new OpenQuestion((OpenQuestion) question));
			} else if (question instanceof MultiChoiceQuestion) {
				System.out.println("Adding Multi");
				int[] answers = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
				System.out.println("Please add at least 2 answers to the test:");
				int ansAmount = 0;
				boolean flag = false;
				while (!flag) {
					if (ansAmount < ((MultiChoiceQuestion) question).getSize()) {
						if (ansAmount >= 2) {
							System.out.println(
									"Would you like to add another answer to the question? Type Y/y for 'yes' or N/n for 'no':");
							charIndex = input.next().charAt(0);
							input.nextLine();
							while (charIndex != 'y' && charIndex != 'Y' && charIndex != 'n' && charIndex != 'N') {
								System.out.println("Invalid Syntax! //>>> Type Y/y for 'yes' or N/n for 'no' !");
								charIndex = input.next().charAt(0);
								input.nextLine();
							}
						}
						if (charIndex == 'y' || charIndex == 'Y' || ansAmount < 2) {
							do {
								try {
									System.out.println("Which Answer would you like to pick for the test? ");
									answerId = Integer.parseInt(input.nextLine()) - 1;
									if (answerId < 0
											|| answerId >= ((MultiChoiceQuestion) question).getMultiAnswers().size())
										throw new IndexOutOfBoundsException();
									if (isNumberExist(answers, answerId))
										throw new AlreadyConnectedException();
									flagos = false;
								} catch (IllegalArgumentException e) {
									System.out.println("Invalid Value //> Enter An Integer Number Between [1-"
											+ ((MultiChoiceQuestion) question).getMultiAnswers().size() + "]" + "\n");
									flagos = true;
								} catch (IndexOutOfBoundsException e) {
									System.out.println("Invalid Value //> Enter An Integer Number Between [1-"
											+ ((MultiChoiceQuestion) question).getMultiAnswers().size() + "]" + "\n");
								} catch (AlreadyConnectedException e) {
									System.out.println(
											"The Chosen Answer Already Exists! //>>Enter Another Integer Number Between [1-"
													+ ((MultiChoiceQuestion) question).getMultiAnswers().size() + "]"
													+ "\n");
								}
							} while (flagos);
							answers[ansAmount++] = answerId;
						} else if (charIndex == 'n' || charIndex == 'N'
								|| ansAmount < ((MultiChoiceQuestion) question).getSize()) {
							System.out.println("You chose 'No' and got stopped adding answers!");
							flag = !flag;
						}
					} else {
						System.out.println("You chose all the possible answers of the question!");
						flag = !flag;
					}
				}
				System.out.println("Adding to Test");
				manager.addQuestionToTest(test, new MultiChoiceQuestion((MultiChoiceQuestion) question, answers));
			}
		}
		System.out.println("Your new test: \n");
		System.out.println(test.toString());
		manager.addTest(test);
		test.saveTestToFile(manager.getTestsSize());
	}

	public static void createAuto(Scanner input, Manager manager) {
		Random rand = new Random();
		int randomNumber = 0;
		boolean flagos = true;
		int questionAmount = manager.getQuestionsSize();
		int amount = 0;
		System.out.println("The total amount of questions available: " + (manager.getQuestionsSize()));
		input.nextLine();
		do {
			try {
				System.out.println("How many questions would you like to choose?");
				amount = Integer.parseInt(input.nextLine());
				if (amount > manager.getQuestionsSize() || amount <= 0)
					throw new IndexOutOfBoundsException();
				flagos = false;
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				System.out.println("Invalid Value //> please enter An Integer number between [1- "
						+ manager.getQuestionsSize() + "]");
			}
		} while (flagos);
		Test test = new Test(amount, true);
		for (int i = 0; i < amount; i++) {
			randomNumber = rand.nextInt(questionAmount);
			Question question = manager.getQuestionById(randomNumber);
			while (manager.isQuestionInTest(test, question)) {
				randomNumber = rand.nextInt(questionAmount);
				question = manager.getQuestionById(randomNumber);
			}
			if (question instanceof OpenQuestion) {
				manager.addQuestionToTest(test, new OpenQuestion((OpenQuestion) question));
			} else if (question instanceof MultiChoiceQuestion) {
				int[] answers = new int[] { -1, -1, -1, -1 };
				for (int j = 0; j < 4; j++) {
					int randomAnswer = (rand.nextInt(((MultiChoiceQuestion) question).getSize()));

					while (isNumberExist(answers, randomAnswer)) {

						randomAnswer = (rand.nextInt(((MultiChoiceQuestion) question).getSize()));
					}
					answers[j] = randomAnswer;
				}
				manager.addQuestionToTest(test, new MultiChoiceQuestion((MultiChoiceQuestion) question, answers));
			}
		}
		System.out.println("Your new test: \n");
		System.out.println(test.toString());
		manager.addTest(test);
		test.saveTestToFile(manager.getTestsSize());
	}

	public static void copyTest(Scanner input, Manager manager) {
		boolean flagos = true;
		int choice = 0;
		input.nextLine();
		boolean flagCheck = manager.getTestsSize() != 0;
		if (flagCheck) {
			System.out.println("Choose test number between [1-" + manager.getTestsSize() + "] :");
			do {
				try {
					choice = Integer.parseInt(input.nextLine());
					if (choice < 1 || choice > manager.getTestsSize())
						throw new NullPointerException();
					flagos = false;
				} catch (IllegalArgumentException | NullPointerException e) {
					System.out.println(
							"Invalid Value //> type An Integer Number between [1-" + manager.getTestsSize() + "]");
				}
			} while (flagos);
			Test test = new Test(manager.getTest(choice - 1));
			manager.addTest(test);
			test.saveCopyTestToFile(choice, manager.getTestsSize());
		} else {
			System.out.println("You Don't Have Any Exams Yet , Creat At Least One And Then Try Again!");
		}
	}

	public static boolean isNumberExist(int[] arr, int number) {
		for (int j : arr) {
			if (j == number)
				return true;
		}
		return false;
	}

	public static void addMultiChoiceQuestion(Scanner input, Manager manager) {
		String caseTwoAnswerBody;
		String caseTwoCheckForBoolean = " ";
		boolean caseTwoIsAnswerTrue;
		boolean flagos = true;
		System.out.println("Please enter the answer's body:");
		caseTwoAnswerBody = input.nextLine();
		System.out.println("Please enter if the answer is correct, please type 'true' or 'false':");
		do {
			try {
				caseTwoCheckForBoolean = input.nextLine();
				if (!caseTwoCheckForBoolean.equals("true") && !caseTwoCheckForBoolean.equals("false")
						&& !caseTwoCheckForBoolean.equals("True") && !caseTwoCheckForBoolean.equals("False"))
					throw new InputMismatchException();
				flagos = false;
			} catch (InputMismatchException e) {
				System.out.println(
						"Invalid Value />> Please enter if the answer is correct, please type 'True' or 'False' ");
			}
		} while (flagos);
		if (caseTwoCheckForBoolean.equals("true") || caseTwoCheckForBoolean.equals("True"))
			caseTwoIsAnswerTrue = true;
		else
			caseTwoIsAnswerTrue = false;
		manager.addAnswerToMultiChoiceQuestion(caseTwoAnswerBody, caseTwoIsAnswerTrue);
	}
	
	

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Manager manager = new Manager();
		
//		manager.addMultiChoiceQuestion("Is this test going to work or not?");
//		manager.addAnswerToMultiChoiceQuestion("answer 1", false);
//		manager.addAnswerToMultiChoiceQuestion("answer 2", false);
//		manager.addAnswerToMultiChoiceQuestion("answer 3", true);
//		manager.addAnswerToMultiChoiceQuestion("answer 4", false);
//
//		manager.addMultiChoiceQuestion("Is this a multi choice question?");
//		manager.addAnswerToMultiChoiceQuestion("I hope so", false);
//		manager.addAnswerToMultiChoiceQuestion("It is!", false);
//		manager.addAnswerToMultiChoiceQuestion("No its not!", false);
//		manager.addAnswerToMultiChoiceQuestion("Absolutely!", false);
//
//		manager.addMultiChoiceQuestion("What do you think about our project?");
//		manager.addAnswerToMultiChoiceQuestion("Its amazing!", true);
//		manager.addAnswerToMultiChoiceQuestion("It could use some changes", true);
//		manager.addAnswerToMultiChoiceQuestion("Its the worst project I have ever seen!", false);
//		manager.addAnswerToMultiChoiceQuestion("I think its ok I guess...", false);
//		manager.addAnswerToMultiChoiceQuestion("Its the best project I have ever seen!", false);
//
//		manager.addOpenQuestion("HELLO?", new Answer("HELLO!"));
//		manager.addOpenQuestion("Do I love coding?", new Answer("Yes, it is the best!"));
//		manager.addOpenQuestion("Is Covid a good thing?", new Answer("No it is definitely not!"));
//		manager.addOpenQuestion("When was The Declaration of Independence of the United States of America signed?",
//				new Answer("1776, July 4th - August 2nd."));

		String dbfileName = "Test_Files\\db_File";

		FileInputStream dbinput;
		try {
			dbinput = new FileInputStream(dbfileName);
			ObjectInputStream dboinput = new ObjectInputStream(dbinput);
			for (Question q : (ArrayList<Question>) dboinput.readObject()) {
				if (q != null) {
					if (q instanceof OpenQuestion) {
						manager.addOpenQuestion((OpenQuestion) q);
					} else {
						manager.addMultiChoiceQuestion((MultiChoiceQuestion) q);
					}
				}
			}
			for (Test test : (ArrayList<Test>) dboinput.readObject()) {
				if (test != null)
					manager.addTest(test);
			}
			dboinput.close();
			File myObj = new File(dbfileName);
			if (myObj.delete()) {
				System.out.println("Deleted the file: " + myObj.getName());
			} else {
				System.out.println("Failed to delete the file.");
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}


		int generalChoice;

		do {
			System.out.println("Hello and welcome!");
			System.out.println("~~~~~~~~~~~~~~~~ List of Options: ~~~~~~~~~~~~~~~~");
			System.out.println(" 1. Show all the questions and their answers.");
			System.out.println(" 2. Add a question and its answers.");
			System.out.println(" 3. Update a question's body.");
			System.out.println(" 4. Update a question's answer.");
			System.out.println(" 5. Delete a question's answer.");
			System.out.println(" 6. Create an Exam manually.");
			System.out.println(" 7. Create an Exam automatically.");
			System.out.println(" 8. Create a test copy.");
			System.out.println(" Exit the Menu /> Type 0 ");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Please choose a program by entering the program's number ->\t");
			generalChoice = input.nextInt();

			switch (generalChoice) {

			case MenuCases.SHOW_QUESTIONS_AND_ANSWERS:
				showAll(manager);
				break;

			case MenuCases.ADD_QUESTIONS:
				addQuestion(input, manager);
				break;

			case MenuCases.EDIT_QUESTIONS_BODY:
				editQuestion(input, manager);
				break;

			case MenuCases.EDIT_ANSWERS_BODY:
				editAnswer(input, manager);
				break;

			case MenuCases.DELETE_ANSWER_OF_QUESTION:
				deleteAnswer(input, manager);
				break;

			case MenuCases.CREATE_MANUAL:
				createManual(input, manager);
				break;

			case MenuCases.CREATE_AUTO:
				createAuto(input, manager);
				break;

			case MenuCases.COPY_TEST:
				copyTest(input, manager);
				break;

			case MenuCases.EXIT:
				System.out.println("You entered 0 and exit the menu.");
				break;

			default:
				System.out.println("You entered a wrong number!");
				System.out.println("Please try again: \n");
				break;
			}
		} while (generalChoice != 0);
		System.out.println("Goodbye!");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					File dbFile = new File(dbfileName);
					if (dbFile.createNewFile()) {
						System.out.println("saving to db file: " + dbFile.getName());
						try {
							FileOutputStream dbFileOutput = new FileOutputStream(dbfileName);
							ObjectOutputStream dbObjectOutput = new ObjectOutputStream(dbFileOutput);
							dbObjectOutput.writeObject(manager.getQuestionsArray());
							dbObjectOutput.writeObject(manager.getTestsArray());
							dbObjectOutput.close();
							System.out.println("success...\n");
						} catch (IOException e) {
							System.out.println("An error occurred.");
							e.printStackTrace();
						}
					} else {
						System.out.println("File already exists.");
					}
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}

			}
		});
	}

}
