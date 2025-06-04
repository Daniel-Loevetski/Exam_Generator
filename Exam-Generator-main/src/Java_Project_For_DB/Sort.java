package Java_Project_For_DB;

import java.util.ArrayList;
import java.util.Collections;

public class Sort {
    private void swap(ArrayList<Question> arr, int i, int j)
    {
        Collections.swap(arr, i, j);
        
    }
    
    private int partition(ArrayList<Question> arr, int low, int high)
    {     
        Question pivot = arr.get(high);
        int i = (low - 1);    
        for(int j = low; j < high ; j++)
        {
        	
            if (pivot.getQuestionBody().compareTo(arr.get(j).getQuestionBody()) > 0)
            {
                i++;
                this.swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }
    
    private int AnswerPartition(ArrayList<Question> arr, int low, int high)
    {     
        Question pivot = arr.get(high);
        int i = (low - 1);    
        for(int j = low; j < high ; j++)
        {
        	
        	if(pivot instanceof OpenQuestion && arr.get(j) instanceof OpenQuestion)
        	{
        		
    		  if (((OpenQuestion)pivot).getAnswer().getAnswerBody().length() >
    				  ((OpenQuestion)arr.get(j)).getAnswer().getAnswerBody().length())
              {
                  i++;
                  this.swap(arr, i, j);
              }
        	}else if(pivot instanceof OpenQuestion && arr.get(j) instanceof MultiChoiceQuestion) {
        		
        			int answersLength = 0;
        			for (MultiChoiceAnswer ans : ((MultiChoiceQuestion)arr.get(j)).getMultiAnswers()) {
        				if(ans != null)
        					answersLength+=ans.getAnswerBody().length();
					}
        			
        		 if (((OpenQuestion)pivot).getAnswer().getAnswerBody().length() > answersLength)
                 {
	        		 i++;
	                 this.swap(arr, i, j);
                 }
        	}else if(pivot instanceof MultiChoiceQuestion && arr.get(j) instanceof MultiChoiceQuestion) {
        		
        		int answersLength = 0;
    			for (MultiChoiceAnswer ans : ((MultiChoiceQuestion)arr.get(j)).getMultiAnswers()) {
    				if(ans != null)
    				answersLength+=ans.getAnswerBody().length();
				}
    			int panswersLength = 0;
    			for (MultiChoiceAnswer ans : ((MultiChoiceQuestion)pivot).getMultiAnswers()) {
    				if(ans != null)
    				panswersLength+=ans.getAnswerBody().length();
				}
        		if (panswersLength > answersLength)
                 {
	        		 i++;
	                 this.swap(arr, i, j);
                 }
        	}else if(pivot instanceof MultiChoiceQuestion && arr.get(j) instanceof OpenQuestion) {
        		int panswersLength = 0;
    			for (MultiChoiceAnswer ans : ((MultiChoiceQuestion)pivot).getMultiAnswers()) {
    				if(ans != null)
    				panswersLength+=ans.getAnswerBody().length();
				}
    			
        		if (panswersLength > ((OpenQuestion)arr.get(j)).getAnswer().getAnswerBody().length())
                 {
	        		 i++;
	                 this.swap(arr, i, j);
                 }
        	}
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }
    
    public void quickSort(ArrayList<Question> arr, int low, int high)
    {
        if (low < high)
        {
            int pi = this.partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    public void AnswerQuickSort(ArrayList<Question> arr, int low, int high)
    {
        if (low < high)
        {
            int pi = this.AnswerPartition(arr, low, high);
            AnswerQuickSort(arr, low, pi - 1);
            AnswerQuickSort(arr, pi + 1, high);
        }
     
    }
}
