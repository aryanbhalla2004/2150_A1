public class Food {
  protected String description;
  protected int calories;

  public Food(String description, int calories) {
    this.description = description;
    this.calories = calories;
  }

  public String getDescription() {
    return description;
  }

  public int getCalories() {
    return calories;
  }

  @Override
  public String toString() {
    return description + " (" + calories + " calories)";
  }
}