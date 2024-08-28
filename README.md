# Lokal App - Android Intern Assignment

This repository contains the source code for the Lokal App assignment. The app is built using the latest Android development practices, showcasing a modern approach to mobile development with Kotlin, Jetpack Compose, and other cutting-edge technologies.

## Features

### 1. Bottom Navigation
- The app has a bottom navigation bar with two sections: **Jobs** and **Bookmarks**.
- The **Jobs** section displays a list of available job postings.
- The **Bookmarks** section shows the jobs bookmarked by the user, which are stored in a local database for offline viewing.

### 2. Jobs Screen
- Fetches job data from the given API.
- Displays job title, location, salary, and phone number on each job card.
- Supports **swipe to refresh** functionality to reload the jobs list.

### 3. Job Details
- Clicking on a job card navigates the user to a detailed view of the selected job, providing more information.

### 4. Bookmarking Jobs
- Users can bookmark jobs for later viewing.
- Bookmarked jobs are stored locally using **Room Database** for offline access.
- The **Bookmarks** tab displays all the bookmarked jobs.

### 5. State Management
- Proper state management for **loading**, **error**, and **empty** states is implemented throughout the app using **LiveData** and **Jetpack Compose**.

## Tech Stack

- **Kotlin**: Programming language used for the entire app development.
- **Jetpack Compose**: Used for building the UI in a declarative manner.
- **LiveData**: Used for observing data changes and updating the UI accordingly.
- **Retrofit**: HTTP client used for making network requests to the provided API.
- **Room Database**: Used for storing bookmarked jobs locally.
- **Swipe to Refresh**: Provides a pull-to-refresh user interface pattern.
- **Navigation Component**: Manages navigation between different screens in the app.
- **Dagger Hilt**: Used for dependency injection, ensuring a scalable and maintainable codebase.
- **Coroutines**: Used for managing background tasks and ensuring smooth UI interactions.

## Demo

You can view the working demo of the app here:
- **App APK and Video**: [Lokal App Files on Google Drive](https://drive.google.com/drive/folders/18EQI13rU1jvv2TAcf2rkpmiKw-Rgdvwe?usp=sharing)



