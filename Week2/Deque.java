import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   // construct an empty deque
   // List<Item> deque;
  private int count;
  private Node front, end;

  private class Node {
    private Item item;
    private Node next;
    private Node previous;
  }

  public Deque(){
    count = 0;
    front = end = null;
  }
  // is the deque empty?
  public boolean isEmpty(){
    return count == 0;
  }
  // return the number of items on the deque
  public int size(){
    return count;
  }
  // add the item to the front
  public void addFirst(Item item){
    if(item == null)
      throw new NullPointerException();
    Node newNode = new Node();
    newNode.item = item;
    newNode.previous = null;
    newNode.next = front;

    if(isEmpty())
      end = newNode;
    else
      front.previous = newNode;

    front = newNode;
    count++;
  }
  // add the item to the end
  public void addLast(Item item){
    if(item == null)
      throw new NullPointerException();
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = null;
    newNode.previous = end;

    if(isEmpty())
      front = newNode;
    else
      end.next = newNode;

    end = newNode;
    count++;
  }
  // remove and return the item from the front
  public Item removeFirst(){
    if(isEmpty())
      throw new NoSuchElementException();
    Item item;
    if(count == 1){
      item = front.item;
      front = null;
      end = null;
    }else{
      item = front.item;
      front = front.next;
      front.previous = null;
    }
    count--;
    return item;
  }
  // remove and return the item from the end
  public Item removeLast(){
    if(isEmpty())
      throw new NoSuchElementException();
    Item item;
    if(count == 1){
      item = front.item;
      front = null;
      end = null;
    }else{
      item = end.item;
      end = end.previous;
      end.next = null;
    }
    count--;
    return item;
  }
  // return an iterator over items in order from front to end
  public Iterator<Item> iterator(){
   return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> { ///
    private Node current = front;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if(current == null)
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
  }
  // unit testing (optional)
  public static void main(String[] args){
    //
    Deque<String> deque = new Deque<String>();
    deque.addFirst("study");
    deque.addFirst("I");
    deque.addFirst("and");
    deque.addFirst("You");

    deque.removeFirst();
    deque.removeFirst();
    deque.removeFirst();

    System.out.println("output:");
    for (String x : deque) {
        System.out.print(x + ' ');
    }
  }
}
