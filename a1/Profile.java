public class Profile {
    private String username;
    private LinkedList<Food> consumedFoods;
    
    
    public Profile(String username) {
      this.username = username;
      this.consumedFoods = new LinkedList<Food>();
    }

    public void eat(Food food) {
      if (food != null) {
        consumedFoods.add(food);
      }
    }

    public int getTotalCalories() {
      int sum = 0;
      Node<Food> current = consumedFoods.getHead();
      while(current != null) {
        sum += current.data.getCalories();
        current = current.next;
      }

      return sum;
    }

    // public int getMacrow() {
    //   int sum = 0;
    //   Node<Food> current = consumedFoods.getHead();
    //   while(current != null) {
    //     sum += current.data.getCalories();
    //     current = current.next;
    //   }

    //   return sum;
    // }

    public String getUsername() {
      return this.username;
    }

    public LinkedList<Food> getEaten() {
      return consumedFoods;
    }

    
    @Override
    public String toString() {
      return "";
      // return ("Meal history for" + this.username + ":");
      // Node<Food> current = consumedFoods.getHead();
      // while(current != null) {
      //   current.data.toString();
      //   current = current.next;
      // }
        // return "User: " + username + "\nTotal Calories: " + getTotalCalories();
    }
}