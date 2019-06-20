package lv.valters.spring.springbootfresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SummerBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummerBootApplication.class, args);
    }

    @Bean
    @Scope("prototype")
    public Meal randomMeal() {
        // object/bean prepartion stage ....
        String meal = "meal" + new Random().nextInt(100);
        return new Meal(meal);
    }

}

@RestController
class HomeController {

    @Autowired
    private DrinkService drinkService;
    @Autowired
    private FoodService foodService;

    @GetMapping
    public String home() {
        return "hello! you are at home!";
    }

    @GetMapping("/sum")
    public String sum(@RequestParam("number1") Integer number1,
                      @RequestParam("number2") Integer number2) {
        return String.valueOf(number1 + number2);
    }

    @GetMapping("/drink")
    public String drink(@RequestParam(value = "name", required = false) String personName) {
        if (personName == null) {
            personName = "Valters";
        }
        return personName + " will be drinking " + drinkService.getMeSomeDrink() + " this summer.";
    }

    @GetMapping("/food")
    public String food() {
        String text = "Food service has meal: " + foodService.getFoodServiceMeal();
        text = text + "\nDrink service has meal: " + drinkService.getDrinkServiceMeal();
        return text;
    }

}

@Service
class DrinkService {
    private List<String> availableDrinks = Arrays
            .asList("Water", "Pina Collada", "Aperol Spritz", "Beer", "Juice");

    @Autowired
    private Meal drinkServiceMeal;

    public String getMeSomeDrink() {
        int randomChoice = new Random().nextInt(availableDrinks.size());
        return availableDrinks.get(randomChoice);
    }

    public Meal getDrinkServiceMeal() {
        return drinkServiceMeal;
    }
}

@Component
class FoodService {
    @Autowired
    private DrinkService drinkService;

    @Autowired
    private Meal foodServiceMeal;

    public Meal getFoodServiceMeal() {
        return foodServiceMeal;
    }
}

class Meal {
    private String name;

    public Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                '}';
    }
}
