# 🍳 RecipeSphere - Dynamic Culinary Network

RecipeSphere is a modern, enterprise-grade Spring Boot web application designed for chefs and food enthusiasts to publish culinary recipes, maintain custom personal cookbooks, and exchange community reviews. 

The project solves the problem of unorganized recipe tracking by providing a clean, multi-user web portal equipped with data validation safeguards, automatic relational cascade management, and a highly responsive, modern UI layout.

---

## ✨ Core Features

* **Session-Based Authentication**: Secure registration and login infrastructure to decouple guest traffic from author privileges.
* **Dynamic Recipe Feed**: A centralized discovery dashboard fetching public recipes with text-truncation previews.
* **Personal Digital Cookbook**: An isolated workspace allowing authenticated users to manage, track, and update only their own recipe entries.
* **Dynamic Textual Ingredient Mapping**: An intuitive interface that parses raw, comma-separated string inputs from the frontend directly into independent, structured database entities.
* **Relational Cascading**: Internal constraints that allow users to delete recipes safely. Deleting a recipe automatically terminates associated ingredients and reviews behind the scenes, eliminating SQL constraint errors.
* **Modern UI Styling Layout**: Built natively using CSS custom variables, unified viewport constraints, emerald/slate color palettes, and interactive validation error displays.

---

## 🏛️ Application Architecture

The project strictly follows the **Single Responsibility Principle (SRP)** and isolates system behaviors across separate business boundaries:

```text
src/main/java/com/exam/recipeplatform/
├── model/
│   ├── dto/
│   │   ├── RecipeCreateBindingModel.java   # Payload constraints for recipe form data
│   │   └── ReviewBindingModel.java         # Content validation bounds [1-5 Rating, Non-blank]
│   └── entity/
│       ├── Ingredient.java                 # Structured ingredient schema mapping
│       ├── Recipe.java                     # Master entity holding relational cascade mappings
│       ├── Review.java                     # Dynamic community rating metadata
│       └── User.java                       # Master profile account domain data
├── repository/                         # Spring Data JPA Repository layer (4 core interfaces)
├── service/
│   ├── impl/
│   │   ├── IngredientServiceImpl.java      # Concrete business layer parsing ingredients
│   │   ├── RecipeServiceImpl.java          # Multi-user data protection logic for master recipes
│   │   ├── ReviewServiceImpl.java          # Relational aggregation layer for feedback
│   │   └── UserServiceImpl.java            # Account registration and resource tracking
│   └── ... (Interfaces)
└── web/                                    # Granular, separated routing layers
    ├── AuthController.java                 # Handles isolated user sessions and account creation
    ├── HomeController.java                 # Delivers the responsive welcome index view
    ├── RecipeController.java               # Contextual handler for creation, update, feed, and cookbook
    └── ReviewController.java               # Isolated endpoint dedicated to user rating processing

```

---

## ⚙️ Prerequisites & Environment

Before building the application, ensure your environment meets the following requirements:

* **Java Runtime Environment**: JDK 21
* **Database Engine**: MySQL Server 8.0+
* **Build Automation**: Gradle (Wrapper bundled within the repository)
* **Operating System**: macOS / Linux / Windows

---

## 🛠️ Installation & Database Setup

Follow these steps to initialize the database and install dependencies locally on your machine:

1. **Clone the Repository**
```bash
git clone [https://github.com/your-username/RecipePlatform.git](https://github.com/your-username/RecipePlatform.git)
cd RecipePlatform

```


2. **Initialize the MySQL Database Schema**
   Log into your local MySQL terminal or workbench and create a clean database. Hibernate will automatically manage table generation (`ddl-auto=update`) during startup:
```sql
CREATE DATABASE recipe_db;

```


3. **Configure Your Connection Properties**
   Open `src/main/resources/application.properties` and synchronize the username and password fields to match your local MySQL configuration parameters:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_REAL_LOCAL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

```



---

## 🚀 Usage & Execution Instructions

To execute the application context locally, run the following commands or compile via your IDE setup:

### Sync Gradle & Compile Application

```bash
./gradlew build -x test

```

### Launch the Application Server

Run the boot loader sequence from the terminal:

```bash
./gradlew bootRun

```

Alternatively, open `src/main/java/com/exam/recipeplatform/RecipeplatformApplication.java` inside IntelliJ IDEA and click the green **Play (Run)** icon next to the main method (`Control + R` on macOS).

### Landing Gateways

Once the console prints `Tomcat initialized with port 8080 (http)`, point your web browser to:

* **Main Application UI**: `http://localhost:8080`
* **Local Landing Node**: `http://localhost:8080/login`

---

## 📄 Support


If you encounter runtime environment failure exceptions or need assistance during deployment setups, feel free to choose one of the following options:

* Open a new ticket under the GitHub **Issues** tab.
* Reach out to the development maintainers or submit an email request through the academic exam platform.

