# Calcolo Codice Fiscale (Fiscal Code Calculator)

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

This is an Android application that calculates the Italian "Codice Fiscale" (Fiscal Code) based on user-provided information.

## Description

The Italian "Codice Fiscale" is a unique alphanumeric code used to identify individuals for tax and administrative purposes. This app simplifies the process of calculating this code by taking the following inputs:

*   **Surname (Cognome)**
*   **Name (Nome)**
*   **Date of Birth (Data di Nascita)**
*   **Gender (Sesso)**
*   **Municipality of Birth (Comune di Nascita)**

The app then uses the provided information to generate the corresponding fiscal code.

## Features

*   **User-Friendly Interface:** A simple and intuitive layout for easy data entry.
*   **Input Validation:** Checks for empty fields and invalid date formats, providing helpful error messages.
*   **Municipality Autocomplete:** The app includes an autocomplete feature for the municipality of birth, making it easier to select the correct location.
*   **Fiscal Code Calculation:** Accurately calculates the fiscal code based on the official Italian algorithm.
*   **Clear Output:** Displays the calculated fiscal code in a clear and readable format.
* **Error handling:** The app handles errors such as empty fields, invalid dates, and invalid municipalities.
* **Toast messages:** The app uses toast messages to provide feedback to the user.


## Project Structure

*   `app/src/main/java/com/example/calcolocodicefiscale/`: Contains the main Java source code for the app.
    *   `MainActivity.java`: The main activity of the app, handling user input and fiscal code calculation.
    * `utils`: Contains the utility classes.
        * `Comuni.java`: Class that represents a municipality.
        * `ComuniService.java`: Class that reads the csv file.
        * `DatePickerFragment.java`: Class that manages the date picker.
        * `FiscalUtils.java`: Class that contains the logic to calculate the fiscal code.
*   `app/src/main/res/`: Contains the resources for the app, such as layouts, images, and strings.
    *   `layout/`: Contains the layout files for the app's UI.
    *   `values/`: Contains the string resources.
* `app/src/main/assets`: Contains the csv file with the municipalities.


