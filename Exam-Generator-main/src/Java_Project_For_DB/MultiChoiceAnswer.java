package Java_Project_For_DB;

import java.util.Objects;

public class MultiChoiceAnswer extends Answer{
	private boolean isTrue;
	
	public MultiChoiceAnswer(String answerBody, boolean isTrue) {
		super(answerBody);
		this.isTrue = isTrue;
	}
	
	public boolean isTrue() {
		return isTrue;
	}

	public void setIsTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), isTrue);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MultiChoiceAnswer other = (MultiChoiceAnswer) obj;
		return isTrue == other.isTrue;
	}
	
	@Override
	public String toString() {
		return super.toString() + " , (" + isTrue + ")";
	}
	
	public void edit(String value, boolean value2) {
		super.edit(value);
		this.isTrue = value2;
	}
	
	
}
