GENERAL USAGE NOTES:
This application represents a carpooling platform designed for two types of users: drivers and passengers.
The entry point is a login page, where users can log in to their accounts. If a user does not have an account, they are redirected to a registration page. On the registration page, 
users provide their personal information and select their user type (driver or passenger). If the user chooses to register as a driver, additional fields appear for entering car details,
such as the car type and registration number.
Upon successful registration, all the provided information is stored in the database, and the user must log in to access the application.
Drivers:
Logged-in drivers are redirected to the driver’s page, where they can view a list of passengers, rate them, and add new rides. To create a new ride, drivers must input details such as
the starting location, destination, estimated duration, and route price. This information is saved in the database, and a map of the ride is displayed.
Passengers:
Logged-in passengers are redirected to the passenger’s page, where they can browse all available rides. Passengers can select a ride that best suits their needs based on the provided ride details.
Upon selecting a ride, they can view information about the driver and choose to book the ride. Booking the ride adds the passenger to the database. Passengers can also rate drivers through a dedicated 
rating option.
