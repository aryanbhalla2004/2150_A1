class Node<T> {
  T data;
  Node<T> next;

  public Node(T data) {
      this.data = data;
      this.next = null;
  }
}

// Generic Singly Linked List
public class LinkedList<T> {
  private Node<T> head; // Head of the list
  private int size;


  public LinkedList() {
    this.size = 0;
  }

  public Node<T> getHead() {
    return head;
  }
  // Add a node at the end
  public void add(T data) {
      Node<T> newNode = new Node<>(data);
      if (head == null) {
          head = newNode;
          return;
      }
      Node<T> current = head;
      while (current.next != null) {
          current = current.next;
      }
      current.next = newNode;
      size++;
  }

  public int size() {
    return this.size;
  }

  // // Add a node at the beginning
  // public void addFirst(T data) {
  //     Node<T> newNode = new Node<>(data);
  //     newNode.next = head;
  //     head = newNode;
  // }

  // Find a node by value
  public boolean find(T value) {
    Node<T> current = head;
    while (current != null) {
        if (current.data.equals(value)) { // Use equals() for generic types
          return true;
        }
        current = current.next;
    }
    return false;
  }

  public void display() {
    Node<T> current = head;
    while (current != null) {
        System.out.println(current.data);
        current = current.next;
    }
  }
  // public Node<T> get(T value) {
  //   Node<T> current = head;
  //   while (current != null) {
  //       if (current.data.equals(value)) { // Use equals() for generic types
  //           return true;
  //       }
  //       current = current.next;
  //   }
  //   return false;
  // }

  
}