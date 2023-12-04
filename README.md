# Drone Delivery System

This project implements a RESTful service for managing a fleet of drones for the delivery of medications. It allows for registering drones, loading medications, checking loaded medications, checking available drones for loading, and monitoring the battery level of each drone.

## Features

- Register drones with specific details.
- Load drones with medication items.
- Check which medications are loaded on a specific drone.
- List available drones for loading.
- Check the battery level of a specific drone.
- Periodic task for monitoring drone battery levels.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6.3 or higher

### Installing

Clone the repository:

```bash
git clone [repository_url]
```

Clone the repository
```bash
cd Assessment
```

Build the Project using Maven
```bash
mvn clean install
```

Run the application:
```bash
mvn spring-boot:run
```

## Usage
The service exposes several REST endpoints:

1. Register Drone:

`POST /api/drones/register`

Payload:
```bash
{
    "serialNumber": "SN001",
    "model": "LIGHTWEIGHT",
    "weightLimit": 500,
    "batteryCapacity": 100
}
```

2. Load Drone with Medication:
```bash
{
    "serialNumber": "SN001",
    "model": "LIGHTWEIGHT",
    "weightLimit": 500,
    "batteryCapacity": 100
}
```
3. Check Loaded Medications:

`GET /api/drones/{droneId}/medications`

4. List Available Drones:

`GET /api/drones/available`

5. Check Drone Battery Level:

`GET /api/drones/{droneId}/battery`

The periodic task to check drones battery levels runs every 60 seconds and is saved in a text file in the root directory. The file name is drone_battery_logs.txt
## Running the tests
Execute the following command to run the unit tests:

```bash
mvn test
```
## Built With
- Spring Boot - The web framework used
- Maven - Dependency Management

## Authors
- [Joshua Niji](https://www.linkedin.com/in/joshua-adeniji/)  

