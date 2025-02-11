# Receipt Processor API

## 📌 Project Overview

The Receipt Processor API is a Spring Boot application that processes receipts and calculates reward points based on specific rules. The application provides two endpoints:

- **POST **``: Accepts a receipt JSON, assigns it a unique ID, and stores it in memory.
- **GET **``: Returns the points awarded for a given receipt ID.

The API is designed to run as a **Dockerized application**, ensuring easy deployment and portability.

---

## 🚀 Running the API

### **1️⃣ Clone the Repository**

Before running the API, clone the GitHub repository and navigate into the project folder:

```sh
git clone https://github.com/ChinmayDeosthali/receipt-processor.git
cd receipt-processor
```

---

### **2️⃣ Running with Docker (No Java Required)**

If Java is not installed on your system, you can run the API using Docker:

#### **Step 1: Build the Docker Image**

```sh
docker build -t receipt-processor .
```

#### **Step 2: Run the API Container**

```sh
docker run -p 8080:8080 receipt-processor
```

The API will be available at:

- **Process a Receipt:** `POST http://localhost:8080/receipts/process`
- **Get Points for a Receipt:** `GET http://localhost:8080/receipts/{id}/points`

#### **Example Request & Response**

**1️⃣ Process a Receipt - Request**

```json
{
  "retailer": "M&M Corner Market",
  "purchaseDate": "2022-03-20",
  "purchaseTime": "14:33",
  "items": [
    {
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    }
  ],
  "total": "9.00"
}
```

**Response:**

```json
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }
```

**2️⃣ Get Points for a Receipt**

```sh
GET http://localhost:8080/receipts/abc123/points
```

**Response:**

```json
{ "points": 109 }
```

---

### **3️⃣ API Documentation with Swagger UI**

The API is documented using **Swagger UI** for easy testing and interaction.

#### **Access Swagger UI**

Once the application is running, open your browser and navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

This will display all available endpoints, request/response examples, and allow you to test API calls directly.

---

### **4️⃣ Running Locally with Maven (Java Required)**

If Java 17+ and Maven are installed on your system, you can run the API directly:

#### **Step 1: Build the Application**

```sh
mvn clean package
```

#### **Step 2: Run the API**

```sh
java -jar target/receipt-processor-0.0.1-SNAPSHOT.jar
```

The API will start on:

- **Process a Receipt:** `POST http://localhost:8080/receipts/process`
- **Get Points for a Receipt:** `GET http://localhost:8080/receipts/{id}/points`

---


