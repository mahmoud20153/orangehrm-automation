# OrangeHRM Test Automation Framework

Professional UI test automation framework for the OrangeHRM demo application, built with Java 17, Selenium 4 and TestNG, following the Page Object Model design pattern.

**Author:** Mahmoud Reda — [github.com/mahmoud20153](https://github.com/mahmoud20153)

##  Tech Stack

| Tool | Purpose |
|---|---|
| Java 17 (LTS) | Language |
| Selenium WebDriver 4.21 | Browser automation |
| TestNG 7.10 | Test framework & suite management |
| Maven | Build & dependency management |
| Selenium Manager | Automatic browser-driver resolution |
| Allure | Reporting |
| SLF4J | Logging |

##  Architecture

- **Page Object Model (POM):** one page class per screen; `BasePage` provides wait-safe interactions (no `Thread.sleep`, no implicit waits).
- **Thread-safe driver management:** `DriverFactory` uses `ThreadLocal`, making the suite parallel-execution safe and cross-browser (Chrome / Firefox / Edge).
- **Config-driven execution:** `config.properties` with JVM system-property override, e.g. `mvn test -Dbrowser=firefox -Dheadless=true`.
- **Failure evidence:** a TestNG `ITestListener` captures a screenshot automatically on every failure into `target/screenshots`.
- **State-transition POM:** navigation methods return the destination page object (`login()` → `DashboardPage`), giving compile-time safety.

##  Project Structure

```text
src/
├── main/
│   ├── java/com/mahmoud/automation/
│   │   ├── config/     ConfigReader
│   │   ├── drivers/    DriverFactory (ThreadLocal)
│   │   ├── pages/      BasePage, LoginPage, DashboardPage, PimEmployeeListPage
│   │   └── utils/      ScreenshotUtil
│   └── resources/      config.properties
└── test/
    └── java/com/mahmoud/automation/
        ├── base/       BaseTest (driver lifecycle)
        ├── listeners/  TestListener (screenshot on failure)
        └── tests/      SmokeTest, LoginTest, PimTest
testng.xml              Suite definition (root)
```

##  Current Test Coverage

- **Smoke:** login page loads with correct title
- **Login:** valid login → Dashboard · invalid login → error message · empty fields → "Required" validation
- **PIM:** navigation from Dashboard to Employee List

## How to Run

### Option 1 — IntelliJ IDEA (recommended for local dev)
1. Open the project in IntelliJ IDEA (JDK 17).
2. Right-click `testng.xml` → **Run 'testng.xml'**.

### Option 2 — Command line (CI/CD style)
Requires Maven 3.6+ on PATH:
```bash
mvn clean test
```

### Allure report
```bash
mvn allure:serve
```

##  Roadmap

- [ ] PIM module: add/search employee (CRUD) tests
- [ ] Admin module: user-role filter tests
- [ ] Leave module: apply-leave flow
- [ ] Data-driven tests via TestNG DataProvider
- [ ] GitHub Actions CI workflow

## Key Design Decisions

- Explicit waits only — deterministic, flake-resistant tests.
- Locators are private to page classes; tests contain zero Selenium imports.
- Unique, timestamp-based test data so reruns never collide.
- `.gitignore` excludes build output (`target/`) and IDE state (`.idea/`).
