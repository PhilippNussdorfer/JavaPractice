package towerOfHanoi;

import java.util.ArrayList;
import java.util.List;

public class Stack <T> {
    private List<T> stack = new ArrayList<>();

    public Stack() {}

    public void push(T stackElement) {
        List<T> tmpStack = new ArrayList<>();

        tmpStack.add(stackElement);
        tmpStack.addAll(stack);

        stack = tmpStack;
    }

    public void pop() {
        stack.remove(0);
    }

    public T getTop() {
        return this.stack.get(0);
    }

    public List<String> getStackInList() {
        List<String> res = new ArrayList<>();

        for (T element : stack) {
            res.add(element.toString());
        }

        return res;
    }
}
