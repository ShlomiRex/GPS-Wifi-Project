# A GPS Wifi Project
A project for second year of engineering.
Collaborators: Shlomi

# F.A.Q
Q: Why are you using ArrayList of ArrayList of objects insted of string?
A: When we read CSVPckg file and want to use the objects, we need to convert each field to his type. So, less time is spend converting back and forth objects and testing them if they OK. This decision is based on sorting the CSVPckg. If we sort, each iteration will need to convert the strings to objects, taking inormous time.



# KEY NOTES
1. Record - A line from CSVPckg
2. Records - Collection of Record
3. Factory - Class designed to get X and the product will by Y
4. CSVPckg has 2 headers: 1 is wigle, and 1 is mac,ssid,time ect...


# TODO List by priority
##### 0. Decide which file in G MON to read
##### 1. Create the application(Android application AND desktop application)
Program java application which get g-mon folder and create orginized CSVPckg file.
All of us will make simple java library which takes *file* and convery it to CSVPckg

@Shlomi will make android application, @Amit and @Shiler will make desktop application. The application has GUI and stuff

##### 2. Create the server.
##### 3. Manage information

## G MON Features
- [x] BSSID (It's like mac address of network card)
- [x] Lattitude, Longtitude 
- [x] SSID
- [x] Crypt: Wifi security level (Open, WPA2, WPA3...)
- [x] Beacon Interval
- [x] Connection Mode (What is this?)
- [x] Channel (What is this?)
- [x] RXL (What is this?)
- [x] Date
- [x] Time
- [ ] Altitude
- [ ] Device recognision

# Client Application
Make application which converts KML or CVS and upload to server

# Client Presentation
Create a app that stores the information and proccess it.

# Server
A server that stores the information

# Information Manage
Create application which the human can change the information in the server.



###### Notes
Use firebase 
~~
