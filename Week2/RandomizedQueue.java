import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int count;
  private Item[] items;

  public RandomizedQueue(){                 // construct an empty randomized queue
    items = (Item[]) new Object[2];
    count = 0;
  }
  public boolean isEmpty(){                 // is the queue empty?
    return count == 0;
  }
  public int size(){                       // return the number of items on the queue
    return count;
  }

  private void resize(int s){
    Item[] newItems = (Item[]) new Object[s];
    for(int i = 0; i < count; i++)
      newItems[i] = items[i];
    items = newItems;
  }

  public void enqueue(Item item){           // add the item
    if(item == null)
      throw new NullPointerException();
    if(count == items.length)
      resize(items.length * 2);
    items[count++] = item;
  }

  public Item dequeue(){                    // remove and return a random item
    if(isEmpty())
      throw new NoSuchElementException();
    int random = StdRandom.uniform(count);
    Item item = items[random];
    if(random != count - 1)
      items[random] = items[count - 1];

    items[count - 1] = null;
    count--;
    if(count > 0 && count == items.length / 4)
      resize(items.length / 2);
    return item;
  }

  public Item sample(){                     // return (but do not remove) a random item
    if (isEmpty())
      throw new NoSuchElementException();
    int random = StdRandom.uniform(count);
    Item item = items[random];
    return item;
  }

  public Iterator<Item> iterator(){         // return an independent iterator over items in random order
    return new QueueIterator();
  }

  private class QueueIterator implements Iterator<Item> {
    private int index = 0;
    private int newLength = count;
    private Item[] newArray = (Item[]) new Object[count];

    private QueueIterator() {
      for (int i = 0; i < newLength; i++) {
          newArray[i] = items[i];
      }
    }

    public boolean hasNext() {
      return index <= newLength - 1;
    }

    public Item next() {
      if (newArray[index] == null)
        throw new NoSuchElementException();

      int random = StdRandom.uniform(newLength);
      Item item = newArray[random];
      if (random != newLength - 1)
        newArray[random] = newArray[newLength - 1];
      newLength--;
      newArray[newLength] = null;

      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
  public static void main(String[] args){   // unit testing (optional)
    //
  }
}
