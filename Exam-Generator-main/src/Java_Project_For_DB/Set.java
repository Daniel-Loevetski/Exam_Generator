package Java_Project_For_DB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Set<T extends MultiChoiceAnswer> implements Iterable<T>, Serializable {
	private ArrayList<T> items;

	public Set() {
		items = new ArrayList<T>();
	}

	public Set(Set<T> oldSet) {
		items = new ArrayList<T>(oldSet.getItems());
	}

	public T getByIndex(int num) {
		return items.get(num);
	}

	public void Push(T newItem) {
		try {
			if (items.indexOf(newItem) == -1) {
				items.add(newItem);
				return;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid Value, wrong object was entered!");
		}
		System.out.println("Failed to add set item!");
	}

	public int size() {
		return items.size();
	}

	public void delete(int id) {
		items.remove(id);
	}

	public ArrayList<T> getItems() {
		return this.items;
	}

	@Override
	public Iterator<T> iterator() {
		return this.items.iterator();
	}

	@Override
	public Spliterator<T> spliterator() {
		return items.spliterator();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		Objects.requireNonNull(action);
		for (T t : items) {
			action.accept(t);
		}
	}
}
