public class SingleFood extends Food {
  private String type;
  private double protein, fat, sugar; // Only applicable for certain types
  private String source; // Animal/Plant name (for DAIRY, MEAT, GRAIN)

  public SingleFood(String type, int calories, double protein, double fat, double sugar, String source, String description) {
    super(description, calories);
    this.type = type;
    this.protein = protein;
    this.fat = fat;
    this.sugar = sugar;
    this.source = source;
  }

  public String getSingleFoodType() {
    return this.type;
  }

  public double getProtein() {
    return protein;
  }

  public double getFat() {
    return fat;
  }

  public double getSugar() {
    return sugar;
  }

  @Override
  public String toString() {
    if(type.equals("FRUITVEG")) {
      return type.toUpperCase() + " " + description + " (" + calories + " calories)";
    } else {
      return type.toUpperCase() + " " + description + " (" + calories + " calories, " +
             protein + "g protein, " + fat + "g fat, " + sugar + "g sugar)";
    }
      
  }
}