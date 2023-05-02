Video Game Review

## Project Description

This project is a video game review site. It allows users to view, create, and delete reviews for video games through a mobile application. It also allows admins to approve pending games, delete games, and delete reviews. The project uses a MySQL database to store the data. The project uses a Laravel backend and API and an Android frontend. Here, JWT is used for user authentication. The project also has an admin panel. The admin panel is built with Laravel. The admin panel allows admins to create games, delete games, and delete After the user submits a review, the admin needs to approve it. The project also has an offline mode in the mobile app. In offline mode, users can view all games and reviews.

## Technologies Used

* Java & kotlin for Android development
* Laravel for backend & API development (version >)
* MySQL for database management
* JWt for authentication

## Features

List of features ready 

* Offline mode for mobile app
* Online mode for mobile app

### User

* Users can create accounts
* Users can login and logout
* Users can view all games
* Users can view a specific game details
* Users can view reviews for a specific game
* Users can create reviews
* Users can delete their own reviews
* Users can view their own reviews
* user can view all games and reviews in offline mode.

### Admin

* Admins can create games
* Admins can delete games
* Admins can update games
* Admins can approve pending games
* Admins can delete any review
* Admins can view all reviews
* Admin can view user location when they create a review
* Admin can update user Role
and more...

## Getting Started

### Prerequisites

* Android Studio
* PHP ^8.0
* Composer
* MySQL
* Laravel ^8.0


### Installing

1. Clone the repo
```bash
git clone git@github.com:Riaz-Mahmud/Video-Game-Review-Andorid-app-with-admin-panel.git
```
2. Host the backend on a server (No nedd to 'composer install' or 'npm install' because the vendor and node_modules folder is already included)
3. Make sure your project file location is 'projectFile/public'
4. Migrate the database tables
5. Create a .env file like .env.example and add the following
```bash
DB_CONNECTION=mysql
DB_HOST=
DB_PORT=
DB_DATABASE= 
DB_USERNAME=
DB_PASSWORD=
```
6. Generate storage link by running the following command. You can do it by cpanel tetminal or ssh
```bash
php artisan storage:link
```
of you can do it by hit the following url
```bash
http://project-url/system/storagelink
```

7. Open the project in Android Studio from 'Android Application' folder
8. Run the project on an emulator or physical device


## Usage

1. Open the app
2. Login or create an account
3. View games
4. View game details
5. View reviews for a game
6. Create a review
7. All location permission
8. View all reviews
9. Logout

## Contributors

* [Riaz Mahmud](https://github.com/Riaz-Mahmud "Riaz Mahmud")