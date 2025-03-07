# Congestion Tax Calculator - Gothenburg

## Overview

This application calculates the congestion tax for vehicles entering and exiting the Gothenburg area in Sweden. It implements the specific tax rules defined for Gothenburg, including:

*   **Taxed Hours:** Congestion tax is applied during specific hours of the day (weekdays).
*   **Tax Amounts:** Different tax amounts are charged depending on the time of day.
*   **Daily Maximum:** The maximum tax per vehicle per day is capped at 60 SEK.
*   **Single Charge Rule:** If a vehicle passes multiple tolling stations within 60 minutes, only the highest tax amount is charged.
*   **Exempt Vehicles:** Certain vehicle types (e.g., emergency vehicles, buses) are exempt from the congestion tax.
*   **Tax-Free Periods:** No tax is charged on weekends, public holidays, days before a public holiday, or during the month of July.

## Features

*   **Congestion Tax Calculation:** Calculates the tax for a given vehicle and list of passage times using Streams API in Java.
*   **Exempt Vehicle Handling:** Identifies and exempts specific vehicle types.
*   **Single Charge Rule:** Applies the single charge rule, ensuring only the highest tax within a 60-minute window is charged.
*   **Daily Maximum:** Enforces the daily maximum tax of 60 SEK per vehicle.
*   **Tax-Free Period Logic:** Recognizes weekends and the month of July as tax-free periods (public holidays and days before public holidays need to be implemented).
*   **HTTP Endpoint**: It can calculate the tax using an HTTP POST endpoint.
*   **Parallel calculation** It can calculate the tax using parallel streams (need to be implemented).
*   **Complete tests**: It includes a set of tests to make sure everything works.

## Project Structure

The project is a Spring Boot application with the following key components:

*   **`src/main/java/com/example/demo/`:**
    *   **`entity/`:**
        *   `TaxAccumulator.java`: Class to accumulate the tax values.
        *   `TaxInterval.java`: Represents a tax interval (start time, end time, tax amount).
        *   `TaxRequest.java`: Represents the structure of the input data.
    *   **`service/`:**
        *   `CongestionTaxService.java`: The main service responsible for calculating the congestion tax.
    * **`controller/`**
        * `CongestionTaxController.java`: The REST controller to access the application.
    * `Application.java`: The entry point of the application.
*   **`src/test/java/com/example/demo/`:**
    *   `CongestionTaxServiceTest.java`: Contains the unit tests for the `CongestionTaxService`.
*   **`questions.md`:** A list of questions that were relevant during the development.
* `build.gradle`: The gradle file.

## Usage

### Running the Application

1.  **Prerequisites:**
    *   Java 17+
    *   Gradle

2.  **Build:**
    ```bash
    ./gradlew build
    ```

3.  **Run:**
    ```bash
    ./gradlew bootRun
    ```

### Using the API

The application exposes a REST endpoint for calculating the congestion tax.

*   **Endpoint:** `POST /tax/gothenburg/calculate`

*   **Request Body (JSON):**
    ```json
    {
      "vehicleType": "Car",
      "passages": [
        "2013-01-14T21:00:00",
        "2013-01-15T21:00:00",
        "2013-02-07T06:23:27",
        "2013-02-07T15:27:00",
        "2013-02-08T06:27:00",
        "2013-02-08T06:20:27",
        "2013-02-08T14:35:00",
        "2013-02-08T15:29:00",
        "2013-02-08T15:47:00",
        "2013-02-08T16:01:00",
        "2013-02-08T16:48:00",
        "2013-02-08T17:49:00",
        "2013-02-08T18:29:00",
        "2013-02-08T18:35:00",
        "2013-03-26T14:25:00",
        "2013-03-28T14:07:27"
      ]
    }
    ```
    *   **`vehicleType`:** A string representing the vehicle type.
    *   **`passages`:** A list of date-time strings in ISO 8601 format representing passages.

*   **Response Body (JSON):**

    ```
    97
    ```
    * The response is the calculated congestion tax amount in SEK.

## Testing

To execute the unit tests run the _CongestionTaxServiceTest.java_ file.

## Questions

The `questions.md` file contains a list of questions that would need to be clarified in a real-world scenario, such as:

*   How to obtain public holidays.
*   Error handling strategies.
*   Requirements for external data storage.
*   Authentication and authorization.

## Future Improvements

*   **External Data Store:** The tax rules are currently hardcoded. We can move them to an external data store (e.g., a database or configuration files).
*   **Multiple Cities:** Extend the application to support congestion tax rules for multiple cities:
    1. City-Specific Tax Rules: Move the tax rules (tax intervals, exempt vehicles, etc.) from hardcoded lists in CongestionTaxService to a data structure that can hold information for multiple cities.
    2. City Identifier: Introduce a city parameter to the API to specify which city's rules should be used for the calculation.
    3. External Data Storage: Retrieve tax rules data from external storage per city.
*   **More Robust Error Handling:** Add more comprehensive error handling and validation for input data.
*   **Detailed Tax Breakdown:** Provide a more detailed breakdown of how the tax was calculated.
*   **Public Holidays**: Make the public holidays dynamic.
*   **Exempt Vehicles**: Make the exempt vehicles dynamic.
*   **Tax intervals**: Make the Tax intervals dynamic.

## Dependencies

*   Spring Boot
*   Lombok
*   Junit