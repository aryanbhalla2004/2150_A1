
public class FoodLibrary {
  private LinkedList<Food> foods = new LinkedList<Food>();


  public boolean addFood(Food food) {
    boolean found = foods.find(food);
    if(found) {
      return false;
    }

    foods.add(food);
    return true;     
  }

  

  public Food getFood(String description) {
    Node<Food> current = foods.getHead();
    while(current != null) {
      if(current.data.getDescription().equals(description)) {
        return current.data;
      }
      current = current.next;
    }
    
    return null;
  }
}