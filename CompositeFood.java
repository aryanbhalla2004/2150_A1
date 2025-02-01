
public class CompositeFood extends Food {
  private LinkedList<Food> components;

  public CompositeFood(String description) {
    super(description, 0);
    this.components = new LinkedList<Food>();
  }

  public LinkedList<Food> getComponents() {
    return components;
  }

  public boolean addComponent(Food food) {
    if (food != null) {
        boolean found = false;

        Node<Food> current = components.getHead();
        while(current != null) {
          if(current.data.getDescription().equals(food.description)) {
            found = true;
          }
          current = current.next;
        }
        // for(int i = 0; i < components.size() ; i++) {
        //     if(components.get(i).description.equals(food.description)) {
        //         found = true;
        //     }
        // }

        if(!found) {
          components.add(food);
          this.calories += food.getCalories();
          return true;
        }
        
    }
    return false;
  }

  public void display() {
    components.display();
  }

  @Override
  public String toString() {

    return "";
      // StringBuilder sb = new StringBuilder(description + ":\n");
      // for (Food f : components) {
      //     sb.append("  - ").append(f.toString()).append("\n");
      // }
      // sb.append("Total: " + calories + " calories");
      // return sb.toString();
  }
}