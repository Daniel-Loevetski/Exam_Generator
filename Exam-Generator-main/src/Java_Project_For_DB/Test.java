package Java_Project_For_DB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Test implements Serializable{
	private ArrayList<Question> questions;
	private boolean sort = false;
	
	public Test(int numOfQuestions ,boolean sort) {
		questions = new ArrayList<>();
		this.sort = sort;
	}
	public Test(Test test) {
		questions = new ArrayList<>(test.getQuestions());
		this.sort = test.sort;
	}

	public void addQuestion(Question q) {
			this.questions.add(q);
	}
	
	public int getQuestionsAmount() {
		return questions.size();
	}
	

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public String toString() {
		if(sort) new Sort().quickSort(questions, 0, questions.size() - 1);
		StringBuilder sb = new StringBuilder();
		int questionNumber = 0;
		for (Question question : questions) {
			sb.append(++questionNumber).append(")").append(question.toString()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(questions, sort);
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		return Objects.equals(questions, other.questions);
	}
	
	public void saveTestToFile(int numOfTests) {	
	new Sort().AnswerQuickSort(questions, 0, questions.size()-1);
	LocalDateTime now = LocalDateTime.now();  
	String dateString = String.format("%s_%s_%s", now.getYear(),now.getMonthValue()/10==0? "0"+now.getMonthValue():now.getMonthValue(),now.getDayOfMonth());
	 try {
		 String QfileName = "Test_Files\\exam"+numOfTests+"_"+dateString+".txt"; 
		 String SfileName = "Test_Files\\solution"+numOfTests+"_"+dateString+".txt";
		 File questionFile = new File(QfileName);
		 File answersFile = new File(SfileName);
      if (questionFile.createNewFile() && answersFile.createNewFile()) {
        System.out.println("File created with your exam: " + questionFile.getName());
        try {
        	 FileOutputStream questionFileOutput = new FileOutputStream(QfileName); 
        	 FileOutputStream answersFileOutput = new FileOutputStream(SfileName); 
            for (int i = 0; i < questions.size() ; i++) {
            	questionFileOutput.write(String.format("%s) %s \n", i + 1 ,questions.get(i).getQuestionBody()).getBytes());
			}
            questionFileOutput.close();  
            for (int i = 0; i < questions.size() ; i++) {
				if (questions.get(i) instanceof OpenQuestion) {
					answersFileOutput.write(String.format("%s) %s \n", i + 1,((OpenQuestion)questions.get(i)).getAnswer().getAnswerBody()).getBytes());
				} else {
					answersFileOutput.write(String.format("%s) \n", i + 1).getBytes());
					for (MultiChoiceAnswer answer : ((MultiChoiceQuestion)questions.get(i)).getMultiAnswers()) {
						if(answer!=null)
							answersFileOutput.write(String.format("   %s \n",answer.toString()).getBytes());
					}
					answersFileOutput.write("\n".getBytes());
				}
			}
        
            answersFileOutput.close();  
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
	
	public void saveCopyTestToFile(int choice,int numOfTests) {	
		new Sort().AnswerQuickSort(questions, 0, questions.size()-1);
		LocalDateTime now = LocalDateTime.now();  
		String dateString = String.format("%s_%s_%s", now.getYear(),now.getMonthValue()/10==0? "0"+now.getMonthValue():now.getMonthValue(),now.getDayOfMonth());
		 try {
			 String QfileName = "Test_Files\\exam"+numOfTests+"(copy_of_"+choice+")_"+dateString+".txt"; 
			 String SfileName = "Test_Files\\solution"+numOfTests+"(copy_of_"+choice+")_"+dateString+".txt";
			 File questionFile = new File(QfileName);
			 File answersFile = new File(SfileName);
	      if (questionFile.createNewFile() && answersFile.createNewFile()) {
	        System.out.println("File created with your exam: " + questionFile.getName());
	        try {
	        	 FileOutputStream questionFileOutput = new FileOutputStream(QfileName); 
	        	 FileOutputStream answersFileOutput = new FileOutputStream(SfileName); 
	            for (int i = 0; i < questions.size() ; i++) {
	            	questionFileOutput.write(String.format("%s) %s \n", i + 1 ,questions.get(i).getQuestionBody()).getBytes());
				}
	            questionFileOutput.close();  
	            for (int i = 0; i < questions.size() ; i++) {
					if (questions.get(i) instanceof OpenQuestion) {
						answersFileOutput.write(String.format("%s) %s \n", i + 1,((OpenQuestion)questions.get(i)).getAnswer().getAnswerBody()).getBytes());
					} else {
						answersFileOutput.write(String.format("%s) \n", i + 1).getBytes());
						for (MultiChoiceAnswer answer : ((MultiChoiceQuestion)questions.get(i)).getMultiAnswers()) {
							if(answer!=null)
								answersFileOutput.write(String.format("   %s \n",answer.toString()).getBytes());
						}
						answersFileOutput.write("\n".getBytes());
					}
				}
	        
	            answersFileOutput.close();  
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
}


