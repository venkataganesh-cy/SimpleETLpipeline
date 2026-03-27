# 🚢 Titanic ETL Pipeline

A end-to-end data pipeline built with **Java**, **Spring Boot**, and **MySQL** that extracts, transforms, and loads the Titanic passenger dataset. Built from scratch as a hands-on learning project to understand real-world ETL concepts.

---

## 📌 What This Project Does

This pipeline reads raw Titanic passenger data from a CSV file, validates and stages it in MySQL, transforms it into a clean typed dataset with derived fields, and exposes REST endpoints to trigger and monitor runs — all automated via a built-in scheduler.

---

## 🏗️ Architecture

```
CSV File (titanic.csv)
      │
      ▼
┌─────────────┐
│   Extract   │  CsvReaderService → List<PassengerRecord>
└─────────────┘
      │
      ▼
┌─────────────┐
│   Staging   │  StagingService → staging_passengers table
│             │  • Duplicate guard (existsByPassengerId)
│             │  • Validation flag (is_valid)
│             │  • Audit timestamp (loaded_at)
└─────────────┘
      │
      ▼
┌─────────────┐
│  Transform  │  TransformService → passengers table
│             │  • Type casting (String → Boolean, Integer, BigDecimal)
│             │  • Null handling
│             │  • Derived field: age_group (Child / Adult / Senior / Unknown)
└─────────────┘
      │
      ▼
┌─────────────┐
│    Load     │  PassengerRepository → passengers (target table)
└─────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3.x |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Build tool | Maven |
| Utilities | Lombok, SLF4J / Logback |

---

## 📂 Project Structure

```
src/main/java/com/etldemo/titanicpipeline/
│
├── TitanicPipelineApplication.java     # Entry point, @EnableScheduling
│
├── controller/
│   └── PipelineController.java         # REST endpoints
│
├── scheduler/
│   └── PipelineScheduler.java          # @Scheduled automated runs
│
├── runner/
│   └── PipelineRunner.java             # CommandLineRunner (runs on startup)
│
├── service/
│   ├── PipelineService.java            # Orchestrates all three phases
│   ├── CsvReaderService.java           # Extract — reads titanic.csv
│   ├── StagingService.java             # Load to staging table
│   └── TransformService.java           # Transform and load to target table
│
├── entity/
│   ├── StagingPassenger.java           # Raw staging table entity
│   └── Passenger.java                  # Clean target table entity
│
├── repository/
│   ├── StagingPassengerRepository.java
│   └── PassengerRepository.java
│
└── model/
    └── PassengerRecord.java            # Plain Java model for CSV rows

src/main/resources/
├── application.properties
└── titanic.csv
```

---

## 🗄️ Database Design

### `staging_passengers` — raw landing table

| Column | Type | Notes |
|---|---|---|
| id | BIGINT | Auto-generated PK |
| passenger_id | INT | From CSV |
| survived | VARCHAR | Raw string "0" or "1" |
| pclass | VARCHAR | Raw string |
| name | VARCHAR(512) | |
| sex | VARCHAR | |
| age | VARCHAR | Nullable in source |
| sib_sp | VARCHAR | |
| parch | VARCHAR | |
| ticket | VARCHAR | |
| fare | VARCHAR | |
| cabin | VARCHAR | Mostly blank in source |
| embarked | VARCHAR | |
| loaded_at | DATETIME | Pipeline audit timestamp |
| is_valid | BOOLEAN | false if missing critical fields |

### `passengers` — clean target table

| Column | Type | Notes |
|---|---|---|
| passenger_id | INT | PK |
| survived | BOOLEAN | Typed from "0"/"1" |
| pclass | INT | Typed |
| name | VARCHAR(512) | |
| sex | VARCHAR | |
| age | DECIMAL | Nullable |
| sib_sp | INT | |
| parch | INT | |
| ticket | VARCHAR | |
| fare | DECIMAL | |
| cabin | VARCHAR | null if blank |
| embarked | VARCHAR | null if blank |
| age_group | VARCHAR | Derived: Child / Adult / Senior / Unknown |
| transformed_at | DATETIME | Pipeline audit timestamp |

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8

### 1. Clone the repo

```bash
git clone https://github.com/your-username/titanic-pipeline.git
cd titanic-pipeline
```

### 2. Create the database

```sql
CREATE DATABASE etl_demo;
```

### 3. Configure application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/etl_demo
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 4. Run

```bash
mvn spring-boot:run
```

The pipeline fires automatically on startup and then every 60 seconds. Duplicate rows are safely skipped on repeated runs.

---

## 🌐 REST Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/pipeline/trigger` | Manually trigger a full pipeline run |
| GET | `/api/pipeline/status` | Health check — confirms app is running |

### Example with curl

```bash
# Trigger a run
curl -X POST http://localhost:8080/api/pipeline/trigger

# Check status
curl http://localhost:8080/api/pipeline/status
```

---

## ✅ Key Concepts Demonstrated

- **ETL pattern** — clear separation of Extract, Transform, Load phases
- **Staging table** — raw data landing zone with validation flags, never drop data silently
- **Idempotency** — pipeline is safe to run multiple times without creating duplicates
- **Data quality** — `is_valid` flag marks rows with missing critical fields
- **Type safety** — staging uses raw strings; target table uses proper Java/SQL types
- **Derived fields** — `age_group` computed during transform, did not exist in source data
- **Scheduling** — `@Scheduled` with `@EnableScheduling` for automated runs
- **Separation of concerns** — `PipelineService` is the single source of truth, shared by both the scheduler and the REST controller

---

## 📊 Dataset

**Titanic Dataset** from Kaggle — 891 passenger records with fields including survival status, passenger class, age, fare, and embarkation port.

Source: [kaggle.com/datasets/yasserh/titanic-dataset](https://www.kaggle.com/datasets/yasserh/titanic-dataset)

---

## 🗺️ What's Next (Planned)

- [ ] OpenCSV integration for proper quoted-field CSV parsing
- [ ] Spring Batch migration for chunk-based processing
- [ ] Pipeline run history table (track each run's inserted/skipped/failed counts)
- [ ] Unit tests with JUnit 5 and Mockito
- [ ] Docker + docker-compose setup

---

## 👤 Author

Built by [Your Name] as a hands-on data engineering learning project.
