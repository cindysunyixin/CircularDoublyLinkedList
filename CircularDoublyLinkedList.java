import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Your implementation of a CircularDoublyLinkedList
 *
 * @author Yixin Sun
 * @version 1.0
 */
public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {
        head = null;
        size = 0;

    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws java.lang.IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {
        ArrayList<T> list = new ArrayList<>(Arrays.asList(data));
        if (list.contains(null)) {
            throw new IllegalArgumentException("List contains something null");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (list.size() == 1) {
            head = new LinkedListNode<T>(list.get(0), null, null);
            head.setNext(head);
            head.setPrevious(head);
        } else {
            head = new LinkedListNode<T>(list.get(0), null, null);
            LinkedListNode<T> tmp = head;
            LinkedListNode<T> curr = null;
            int length = list.size();
            for (int i = 1; i < length; i++) {
                curr = tmp;
                tmp = new LinkedListNode<T>(list.get(i), curr, null);
                curr.setNext(tmp);
            }
            tmp.setNext(head);
            head.setPrevious(tmp);
        }
        size = list.size();
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is less than zero or"
                + "larger than size of linked list");
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data, null, null);
            head.setNext(head);
            head.setPrevious(head);
            size++;
        } else if (index == 0) {
            LinkedListNode<T> temp = head.getPrevious();
            head = new LinkedListNode<T>(data, temp, head);
            LinkedListNode<T> a = head.getNext();
            a.setPrevious(head);
            temp.setNext(head);
            size++;
        } else {
            LinkedListNode<T> tmp = head;
            LinkedListNode<T> previous = null;
            for (int i = 1; i < index + 1; i++) {
                previous = tmp;
                tmp = tmp.getNext();
            }
            LinkedListNode<T> a = new LinkedListNode<T>(data, previous, tmp);
            previous.setNext(a);
            tmp.setPrevious(a);
            size++;
        }

    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than "
                + "zero or larger than size of linked list");
        }
        if (index == 0) {
            T data = (head.getData());
            return data;
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 1; i < index + 1; i++) {
                curr = curr.getNext();
            }
            T data = curr.getData();
            return data;
        }

    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is less "
            + "than zero or larger than size of linked list");
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            size--;
            return data;
        }
        if (index == 0) {
            LinkedListNode<T> previous = head.getPrevious();
            T data = head.getData();
            head = head.getNext();
            previous.setNext(head);
            head.setPrevious(previous);
            size--;
            return data;
        } else {
            LinkedListNode<T> tmp = head;
            LinkedListNode<T> curr = null;
            for (int i = 1; i < index + 1; i++) {
                curr = tmp;
                tmp = tmp.getNext();
            }
            T data = tmp.getData();
            LinkedListNode<T> tmp2 = tmp.getNext();
            curr.setNext(tmp2);
            tmp2.setPrevious(curr);
            size--;
            return data;
        }

    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data, null, null);
            head.setNext(head);
            head.setPrevious(head);
            size++;
        } else {
            LinkedListNode<T> temp = head.getPrevious();
            head = new LinkedListNode<T>(data, temp, head);
            LinkedListNode<T> aHead = head.getNext();
            aHead.setPrevious(head);
            temp.setNext(head);
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data);
            head.setNext(head);
            head.setPrevious(head);
            size++;
        } else {
            LinkedListNode<T> current = new LinkedListNode<T>(data,
                    head.getPrevious(), head);
            head.getPrevious().setNext(current);
            head.setPrevious(current);
            size++;
        }
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            size = 0;
            return data;
        } else {
            T data = head.getData();
            head.getPrevious().setNext(head.getNext());
            head.getNext().setPrevious(head.getPrevious());
            head = head.getNext();
            size--;
            return data;
        }

    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            size = 0;
            return data;
        } else {
            T data = head.getPrevious().getData();
            head.setPrevious(head.getPrevious().getPrevious());
            head.getPrevious().setNext(head);
            size--;
            return data;
        }

    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return 0;
        } else {
            LinkedListNode<T> tmp = head;
            LinkedListNode<T> previous = null;
            for (int i = 1; i < size; i++) {
                previous = tmp;
                tmp = tmp.getNext();
                if (tmp.getData().equals(data)) {
                    LinkedListNode<T> next = tmp.getNext();
                    previous.setNext(next);
                    next.setPrevious(previous);
                    size--;
                    return i;
                }
            }
            throw new NoSuchElementException("There is no such element");
        }
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        LinkedListNode<T> current = head;
        boolean flag = false;
        int oldSize = size;
        for (int i = 0; i < oldSize; i++) {
            if (current.getData().equals(data)) {
                if (head == current) {
                    head = head.getNext();
                }

                current.getNext().setPrevious(current.getPrevious());
                current.getPrevious().setNext(current.getNext());
                flag = true;
                size--;
            }

            current = current.getNext();
        }
        return flag;


    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        LinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /* DO NOT MODIFY THIS METHOD */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }

}
