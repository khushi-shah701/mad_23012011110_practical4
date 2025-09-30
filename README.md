#Practical-4

AIM: Create an Android Alarm application by using service & BroadcastReceiver.
1) Create MainActivity according to below UI design.
2) Create AlarmBroadcastReceiver class
3) Create AlarmService Class
4) Add android.permission.SCHEDULE_EXACT_ALARM Permission in Manifest file

Project Overview and Detailed Description
This Android Alarm Application is designed to be a straightforward yet effective tool for users who need timely reminders or wake-up calls. The application centers around the core concept of scheduling tasks (playing an alarm sound) to occur at specific future times, a common requirement in many utility apps.

Core User Experience:

At a Glance Information: Upon launching, the user is greeted with the current date and time, providing immediate context.
Alarm Creation: A prominent "Create Alarm" button allows users to initiate the alarm setting process. This action typically presents a standard Android TimePickerDialog, ensuring a familiar and intuitive way to select the desired hour and minute.
Scheduled Alarm Visibility: Once an alarm is successfully set, the application's main interface updates to clearly display the time of the upcoming alarm. This visual confirmation assures the user that their alarm is active.
Alarm Cancellation: Users retain full control and can cancel any scheduled alarm before it triggers. A dedicated "Cancel Alarm" button becomes available for active alarms.
Audible Alert: The primary function is the audible alarm. When the system time matches the scheduled alarm time, a sound is played to alert the user. This critical function is designed to work reliably, even if the application is not in the foreground or the device screen is off.
Technical Architecture and Component Interaction:

The application's reliability and background operation are achieved through a well-defined interaction between several key Android components:

MainActivity.kt (The User Interface Hub):

This is the entry point and primary interaction screen for the user.
It's responsible for rendering the layout defined in activity_main.xml.
It handles user input, such as button clicks to create or cancel alarms.
It orchestrates the process of setting up an alarm with the AlarmManager. This involves:
Gathering the desired time from the TimePickerDialog.
Formatting this time into a Calendar object or milliseconds representation.
Creating an Intent that specifies AlarmBroadcastReceiver as the target component.
Wrapping this Intent in a PendingIntent. A PendingIntent grants the AlarmManager (an external system service) permission to execute the intent on behalf of the application at a later time.
It manages the display of UI elements, showing or hiding the "scheduled alarm" card based on whether an alarm is active.
It handles the direct cancellation of alarms by also interacting with AlarmManager to remove a previously set PendingIntent and directly stopping the AlarmService if it's currently playing.
AlarmManager (The System Scheduler):

This is a crucial Android system service. The application doesn't implement this; it uses it.
Its role is to execute a PendingIntent at a specified time.
MainActivity registers the alarm request with AlarmManager, providing the PendingIntent and the trigger time.
AlarmManager then takes over, holding onto this request. Even if the application is closed, AlarmManager will still trigger the PendingIntent when the designated time arrives (using RTC_WAKEUP flag ensures the device wakes up if asleep).
The application requests the SCHEDULE_EXACT_ALARM permission, which is necessary for precise alarm scheduling, especially on newer Android versions.
AlarmBroadcastReceiver.kt (The Event Listener):

This component is a BroadcastReceiver, designed to listen for system-wide broadcast intents.
It's registered in the AndroidManifest.xml to respond to the specific PendingIntent dispatched by AlarmManager.
When AlarmManager triggers the alarm, it sends the PendingIntent, which in turn activates AlarmBroadcastReceiver's onReceive() method.
The primary responsibility of this receiver in this application is to react to the alarm event by starting the AlarmService. It acts as a lightweight intermediary between the system's alarm trigger and the service responsible for the alarm action.
AlarmService.kt (The Sound Player):

This is a Service, a component designed for performing long-running operations in the background without a direct user interface.
When started by AlarmBroadcastReceiver (via startService()), its onStartCommand() method is invoked.
Inside onStartCommand(), it initializes a MediaPlayer instance.
The MediaPlayer is configured with an audio resource (e.g., a sound file like alarm.mp3 placed in the res/raw directory of the project).
mediaPlayer.start() is called to begin playback of the alarm sound.
The service is typically started as START_STICKY, suggesting that if the system kills the service due to low memory, it should attempt to restart it. However, for an alarm, the more critical part is that the AlarmManager will re-trigger if the alarm was set to be repeating or if persistence logic is added.
The onDestroy() method of the service is crucial for resource cleanup. When the service is stopped (e.g., by the user canceling the alarm, or by the system), onDestroy() should stop the MediaPlayer and call mediaPlayer.release() to free up system audio resources.
AndroidManifest.xml (The Application Blueprint):

This file declares all essential components of the application to the Android operating system.
It registers MainActivity as the launchable activity.
It registers AlarmBroadcastReceiver so the system knows it can receive intents.
It registers AlarmService so it can be started.
It declares necessary permissions, most importantly android.permission.SCHEDULE_EXACT_ALARM, which is vital for the AlarmManager to function as intended for precise alarms.
activity_main.xml (The Visual Layout):

This XML file defines the structure and appearance of the user interface in MainActivity.
It uses ConstraintLayout for flexible positioning of elements.
It includes MaterialCardView elements to group related UI components, such as one card for creating alarms (displaying current time, general info, and the create button) and another card that dynamically appears to show details of a set alarm (set time and the cancel button).
TextClock is used to display the current time, updating automatically.
TextViews display static and dynamic text.
MaterialButton elements provide interactive buttons for "Create Alarm" and "Cancel Alarm," often styled with icons.
A NestedScrollView ensures that the content can scroll if it exceeds the screen height, improving usability on smaller screens or if more information is added later.
Workflow Summary (Setting and Triggering an Alarm):

User opens MainActivity.
User taps "Create Alarm."
MainActivity shows TimePickerDialog.
User selects a time and confirms.
MainActivity calculates trigger time, creates an Intent for AlarmBroadcastReceiver, wraps it in a PendingIntent, and registers it with AlarmManager.
UI in MainActivity updates to show the scheduled alarm.
User may close the app or use other apps.
At the scheduled time, AlarmManager wakes the device (if needed) and sends the PendingIntent.
AlarmBroadcastReceiver.onReceive() is invoked.
AlarmBroadcastReceiver starts AlarmService.
AlarmService.onStartCommand() initializes MediaPlayer and plays the alarm sound.
User is alerted. The sound continues until the AlarmService is explicitly stopped (e.g., by the user canceling the alarm, which would involve MainActivity calling stopService() and also canceling the AlarmManager's PendingIntent).
This structured approach ensures that alarms are handled efficiently and reliably, forming the foundation of a functional alarm application.

Conceptual Output Screenshots
Alarm App Screens

Screen 1	
<img width="429" height="956" alt="image" src="https://github.com/user-attachments/assets/69531667-20cf-402d-9c38-fdcb055036d8" />
Main Screen - No Alarm Set
(As previously described/visualized)	

Screen 2
<img width="434" height="963" alt="image" src="https://github.com/user-attachments/assets/51bbfae1-9165-4ee5-9b14-d1905a598c94" />
Time Picker Dialog
(As previously described/visualized)	
Screen 3
<img width="434" height="962" alt="image" src="https://github.com/user-attachments/assets/52f0c2cc-726c-4139-85f8-47c3e451ef50" />
Main Screen - Alarm Set
(As previously described/visualized
