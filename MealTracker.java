import java.io.*;
import java.util.*;

public class MealTracker {
    private FoodLibrary foodLibrary = new FoodLibrary();
    private ProfileLibrary profileLibrary = new ProfileLibrary();

    public void processCommand(String command, BufferedReader reader) {
      String[] parts = command.split(" ");
      
      switch (parts[0]) {
        case "NEWFOOD":
          if (parts[1].equals("SINGLE")) {
            addSingleFood(parts);
          } else if (parts[1].equals("COMPOSITE")) {
            addCompositeFood(parts, reader);
          }
          break;
        case "NEWPROFILE":
          if (profileLibrary.addProfile(parts[1])) {
            System.out.println("NEW USER ADDED");
          } else {
            System.out.println("DUPLICATE USER NOT ADDED");
          }
          break;
        case "EAT":
          String food = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
          recordMeal(parts[1], food);
          break;
        case "PRINTCALORIES":
          printCalories(parts[1]);
          break;
        case "PRINTSERVINGS":
          printServing(parts[1]);
          break;
        case "PRINTMEALS":
          printMeal(parts[1]);
          break;
        case "QUIT":
          System.out.println("DONE");
          System.exit(0);
          break;
        default:
          if (command.startsWith("#")) {
            System.out.println(command);
          }
          break;
      }
    }

    private void addCompositeFood(String[] parts, BufferedReader reader) {
      int numComponents = Integer.parseInt(parts[2]);
      String description = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
      
      CompositeFood item = (CompositeFood)foodLibrary.getFood(description);


      if (item == null) {
        CompositeFood compositeFood = new CompositeFood(description);
        boolean isFailed = false;

        for(int i = 0; i < numComponents; i++) {
          try {
            String componentName = reader.readLine();
            Food findFoodInLibrary = foodLibrary.getFood(componentName);

            if(findFoodInLibrary == null) {
              System.out.println("FAILURE TO ADD FOOD");
              isFailed = true;
              break;
            } else {
              compositeFood.addComponent(findFoodInLibrary);
            }

          } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
          }
        }  
        
        foodLibrary.addFood(compositeFood);

        if(!isFailed) {
          System.out.println("NEW FOOD ADDED");System.out.println("FOOD UPDATED");
        }
        
      } else {
        boolean isFailed = false;
        for(int i = 0; i < numComponents; i++) {
          try {
            String componentName = reader.readLine();

            Food findFoodInLibrary = foodLibrary.getFood(componentName);
            if(findFoodInLibrary == null) {
              System.out.println("FAILURE TO ADD FOOD");
              isFailed = true;
              break;
            } else {
              item.addComponent(new Food(componentName, foodLibrary.getFood(componentName).getCalories()));
            }

          } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
          }
        }

        if(!isFailed) {
          System.out.println("FOOD UPDATED");
        }
      }
    }

    private void addSingleFood(String[] parts) {
      String type = parts[2];
      int calories = Integer.parseInt(parts[3]);
      double protein = 0, fat = 0, sugar = 0;
      String source = "";
      String description = "";

      if (type.equals("FRUITVEG")) {
        source = parts[4];
        description = String.join(" ", Arrays.copyOfRange(parts, 5, parts.length));
      } else if (type.equals("DAIRY") || type.equals("MEAT") || type.equals("GRAIN") || type.equals("OTHER")) {
        protein = Double.parseDouble(parts[4]);
        fat = Double.parseDouble(parts[5]);
        source = parts[6];
        description = String.join(" ", Arrays.copyOfRange(parts, 7, parts.length));
      }
      
    
      
      Food food = new SingleFood(type, calories, protein, fat, sugar, source, description);
      
      if (foodLibrary.addFood(food)) {
        System.out.println("NEW FOOD ADDED");
      } else {
        System.out.println("FOOD UPDATED");
      }
    }

    private void recordMeal(String username, String foodDescription) {
      Profile profile = profileLibrary.getProfile(username);
      Food food = foodLibrary.getFood(foodDescription);

      if (profile == null) {
        System.out.println("USER NOT FOUND");
      } else if (food == null) {
        System.out.println("FOOD NOT FOUND");
      } else {
        profile.eat(food);
        System.out.println("MEAL RECORDED");
      }
    }

    private void printCalories(String username) {
      Profile profile = profileLibrary.getProfile(username);
      if (profile == null) {
        System.out.println("USER NOT FOUND");
      } else {
        System.out.println("TOTAL CALORIES = " + profile.getTotalCalories());
      }
    }

    private void printMeal(String username) {
      Profile profile = profileLibrary.getProfile(username);
      LinkedList<Food> item = profile.getEaten();
      double p = 0, f = 0, s = 0;

      System.out.println("Meal history for " + username + ":");
      Node<Food> current = item.getHead();
      while(current != null) {
        
        Food test = foodLibrary.getFood(current.data.description);
        if(test instanceof CompositeFood) {
          CompositeFood compFood = (CompositeFood)test;
          LinkedList<Food> compFoodList = compFood.getComponents();
          System.out.println(compFood.description + ":");
          Node<Food> currentCompFood = compFoodList.getHead();
          while(currentCompFood != null) {
            Food food = foodLibrary.getFood(currentCompFood.data.description);
            SingleFood singleFood = (SingleFood)food;
            p += singleFood.getProtein();
            f += singleFood.getFat();
            s += singleFood.getSugar();
            System.out.println("\t" + food.toString());
            currentCompFood = currentCompFood.next;
          }
        } else {
          SingleFood singleFood = (SingleFood)test;
          p += singleFood.getProtein();
          f += singleFood.getFat();
          s += singleFood.getSugar();
          System.out.println(test.toString());
        }
        current = current.next;
      }

      System.out.println("Totals: ");
      System.out.println("\t" + profile.getTotalCalories() + " calories");
      System.out.println("\t" + p + " g protein");
      System.out.println("\t" + f + " g fat");
      System.out.println("\t" + s +  " g sugar");

    }

    private void printServing(String username) {
      Profile profile = profileLibrary.getProfile(username);
      if (profile == null) {
        System.out.println("USER NOT FOUND");
      } else {        
        System.out.println("Serving history for " + username);
        int fv = 0, d = 0, m = 0, g = 0, o = 0;
        LinkedList<Food> item = profile.getEaten();

        Node<Food> current = item.getHead();
        while(current != null) {
          Food test = foodLibrary.getFood(current.data.description);
          if(test instanceof CompositeFood) {
            CompositeFood compFood = (CompositeFood)test;
            LinkedList<Food> compFoodList = compFood.getComponents();
            Node<Food> currentCompFood = compFoodList.getHead();
            while(currentCompFood != null) {
              Food food = foodLibrary.getFood(currentCompFood.data.description);
              SingleFood singleFood = (SingleFood)food;
              if(singleFood.getSingleFoodType().equals("FRUITVEG")) {
                fv++;
              } else if(singleFood.getSingleFoodType().equals("DAIRY")) {
                d++;
              } else if(singleFood.getSingleFoodType().equals("GRAIN")) {
                m++;
              } else if(singleFood.getSingleFoodType().equals("MEAT")) {
                g++;
              } else if(singleFood.getSingleFoodType().equals("OTHER")) {
                o++;
              }
              currentCompFood = currentCompFood.next;
            }
          } else {
            SingleFood singleFood = (SingleFood)test;
            if(singleFood.getSingleFoodType().equals("FRUITVEG")) {
              fv++;
            } else if(singleFood.getSingleFoodType().equals("DAIRY")) {
              d++;
            } else if(singleFood.getSingleFoodType().equals("GRAIN")) {
              m++;
            } else if(singleFood.getSingleFoodType().equals("MEAT")) {
              g++;
            } else if(singleFood.getSingleFoodType().equals("OTHER")) {
              o++;
            }
          }
          current = current.next;
        }
       

        System.out.println("\tFruit & Vegetable: " + fv);
        System.out.println("\tDairy: " + d);
        System.out.println("\tMeat: " + m);
        System.out.println("\tGrain: " + g);
        System.out.println("\tOther: " + o);
      }
    }


    public void processFile(String filename) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null) {
        processCommand(line, reader);
      }
      reader.close();
    }
    

    public static void main(String[] args) {
      MealTracker tracker = new MealTracker();
      try {
        tracker.processFile(args[0]);
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
      }
    }
}