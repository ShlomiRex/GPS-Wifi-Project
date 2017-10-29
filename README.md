# A GPS Wifi Project
A project for second year of engineering.
Collaborators: Amit , Shiler, Shlomi

To edit this file read https://help.github.com/articles/basic-writing-and-formatting-syntax/

# TODO List by priority
##### 0. Decide which file in G MON to read
##### 1. Create the application(Android application AND desktop application)
Program java application which get g-mon folder and create orginized CSV file.
All of us will make simple java library which takes *file* and convery it to CSV

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

# Problems On The Way
1. Unsure if want G MON or other applications. Missing altitude and device recognision.
2. After choosing G MON, unsure which file to get the data from. There are several files in the g mon folder, and the most basic is the txt.
3. Because of this we need system that can try to sniff the best file to read from. If the file is corrupted or invalid data then go to the next file. If all are corrupted, inform the user
4. After we got the file 100% corruption free we need to think how to scan the file: If its text its easy. If its CSV (which currently I cant see any data on those files), if its KML and etc.


###### Notes
Use firebase 
Shiler has gone to Miluim 29/10/2017 for 1 week
