
# Product Service

## Overview
The Product Service manages the **product catalog**.  
It handles product creation, updates, and full-text search using Elasticsearch.

## Features
- Add, update, delete products (Admin only)
- View products by category
- Full-text search with Elasticsearch
- Stock management

## Tech Stack
- Java 17, Spring Boot
- Spring Data JPA + MySQL (RDS)
- Elasticsearch (OpenSearch on AWS)
- Dockerized microservice

## API Endpoints
- `POST /api/products` – Add a new product
- `GET /api/products` – Get all products
- `GET /api/products/{id}` – Get product details
- `GET /api/products/search?q=keyword` – Search products
- `PUT /api/products/{id}` – Update product
- `DELETE /api/products/{id}` – Delete product

## How to Run
```bash
mvn spring-boot:run
