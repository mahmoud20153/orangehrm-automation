# OrangeHRM Test Automation Framework

Professional UI test automation framework for the OrangeHRM demo application, built with Java 17, Selenium 4 and TestNG, following the Page Object Model design pattern.

**Author:** Mahmoud Reda — [github.com/mahmoud20153](https://github.com/mahmoud20153)

## 🛠 Tech Stack

| Tool | Purpose |
|---|---|
| Java 17 (LTS) | Language |
| Selenium WebDriver 4.46 | Browser automation |
| TestNG 7.10 | Test framework & suite management |
| Maven | Build & dependency management |
| Selenium Manager | Automatic browser-driver resolution |
| Allure | Reporting |
| SLF4J | Logging |

## 🏗 Architecture

- **Page Object Model (POM):** one page class per screen; `BasePage` provides wait-safe interactions (no `Thread.sleep`, no implicit waits).
- **State-transition POM:** navigation methods return the destination page object (`login()` → `DashboardPage`), giving compile-time safety. Methods that stay on the same page return `this`, enabling fluent chaining.
- **Thread-safe driver management:** `DriverFactory` uses `ThreadLocal`, making the suite parallel-execution safe and cross-browser (Chrome / Firefox / Edge).
- **Config-driven execution:** `config.properties` with JVM system-property override, e.g. `mvn test -Dbrowser=firefox -Dheadless=true`.
- **Data-driven testing:** `@DataProvider` classes keep test data separate from test logic; each case has a name that appears in logs and screenshot filenames.
- **Failure evidence:** a TestNG `ITestListener` captures a screenshot on every failure (saved to `target/screenshots` and attached to the Allure report).

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/com/mahmoud/automation/
│   │   ├── config/     ConfigReader
│   │   ├── drivers/    DriverFactory (ThreadLocal)
│   │   ├── pages/      BasePage, LoginPage, DashboardPage,
│   │   │               PimEmployeeListPage, PimAddEmployeePage,
│   │   │               PimPersonalDetailsPage
│   │   └── utils/      ScreenshotUtil
│   └── resources/      config.properties
└── test/
    └── java/com/mahmoud/automation/
        ├── base/       BaseTest (driver lifecycle, loginAsAdmin)
        ├── data/       LoginData (DataProviders)
        ├── listeners/  TestListener (screenshot on failure, Allure attachment)
        └── tests/      SmokeTest, LoginTest, PimTest
testng.xml              Suite definition (root)
```

## ✅ Current Test Coverage

- **Smoke:** login page loads with correct title
- **Login:**
    - valid login → Dashboard
    - logout → back to Login page
    - data-driven validation cases (empty username / password / both) → "Required" messages
    - data-driven invalid-credential cases → "Invalid credentials" error
- **PIM:**
    - navigation from Dashboard to Employee List
    - add employee with mandatory fields → Personal Details page shows the saved name

## ▶️ How to Run

### Option 1 — IntelliJ IDEA (recommended for local dev)
1. Open the project in IntelliJ IDEA (JDK 17).
2. Right-click `testng.xml` → **Run 'testng.xml'**.

### Option 2 — Command line (CI/CD style)
Requires Maven 3.6+ on PATH:
```bash
mvn clean test
```

Override configuration without editing any file:
```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

## 📊 Reports

### Allure report
```bash
mvn allure:serve
```

### Failure screenshots
Saved to `target/screenshots` as `<testName>[<caseName>]_<timestamp>.png`.

## ⚙️ Configuration

Settings live in `src/main/resources/config.properties`:

| Key | Description |
|---|---|
| `app.url` | Application base URL |
| `browser` | `chrome`, `firefox` or `edge` |
| `headless` | Run without a visible browser window |
| `explicit.wait` | Explicit wait timeout in seconds |
| `valid.username` / `valid.password` | Credentials used by `loginAsAdmin()` |

Any key can be overridden from the command line with `-Dkey=value`.

## 🗺 Roadmap

- [x] Page Object Model with `BasePage` and fluent navigation
- [x] Data-driven login tests via TestNG DataProvider
- [x] PIM module: add employee
- [ ] Negative tests for Add Employee (empty fields, duplicate employee ID)
- [ ] PIM module: search / edit / delete employee
- [ ] Admin module: user-role filter tests
- [ ] Leave module: apply-leave flow
- [ ] Retry analyzer for demo-site flakiness
- [ ] GitHub Actions CI workflow

## 💡 Key Design Decisions

- Explicit waits only — deterministic, flake-resistant tests.
- Locators are private to page classes; tests contain no locators.
- Test data is separated from test logic (DataProviders) and credentials come from configuration, not from test code.
- Unique, timestamp-based test data so reruns never collide.
- `.gitignore` excludes build output (`target/`), IDE state (`.idea/`) and report folders.

## 📝 Notes

Tests run against the public OrangeHRM demo site. Its data is reset periodically and shared with other users, so occasional slowness is expected.