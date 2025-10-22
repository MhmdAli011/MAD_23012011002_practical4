---


## ⏰ PRACTICAL 4 — ALARM WITH FOREGROUND SERVICE (KOTLIN)




### 🎯 AIM : Create an Android Alarm application by using service & BroadcastReceiver.
---
📖 DESCRIPTION:
---------------
This Android app allows users to set an alarm ⏱️ at a chosen time.
When the alarm triggers, a **Foreground Service** starts that:
 - 🔔 Displays a high-priority notification.
 - 🎵 Plays a looping alarm sound using MediaPlayer.
 - 🚀 Runs continuously until the user cancels it.

This project demonstrates how to work with:
👉 AlarmManager, BroadcastReceiver, Foreground Service,
👉 Runtime permissions, NotificationChannel, and MediaPlayer.

--------------------------------------------------------
🧭 WHAT YOU CAN DO:
--------------------------------------------------------
1️⃣ Tap **"Create Alarm"** → Pick a time from the TimePicker.  
2️⃣ The selected time appears on the screen as confirmation.  
3️⃣ Tap **"Cancel Alarm"** → Stops the sound and cancels the scheduled alarm.  

--------------------------------------------------------
⚙️ HOW IT WORKS (HIGH-LEVEL FLOW):
--------------------------------------------------------

🟣 MainActivity:
   • Opens the TimePicker dialog for time selection.  
   • Schedules an exact alarm using AlarmManager and PendingIntent.  
   • Sends the intent to AlarmBroadcastReceiver.  

🟣 AlarmBroadcastReceiver:
   • Receives the system broadcast when alarm time occurs.  
   • Starts or stops the AlarmService safely.  
   • Uses startForegroundService() on Android O and above.  

🟣 AlarmService:
   • Runs as a Foreground Service with a visible notification.  
   • Creates a Notification Channel for Android O+.  
   • Plays the system alarm tone in a continuous loop.  
   • Stops and cleans up when the alarm is canceled.  

--------------------------------------------------------
🔒 PERMISSIONS & ANDROID VERSION DETAILS:
--------------------------------------------------------
📍 Permissions used:
   - SCHEDULE_EXACT_ALARM → Required for exact alarms (Android 12+)
   - POST_NOTIFICATIONS → Needed for showing foreground notifications (Android 13+)
   - WAKE_LOCK → Keeps CPU active while alarm is playing (optional)

⚠️ Notes:
   - On Android 12+, you may need to manually enable Exact Alarms in settings.  
   - On Android 13+, notification permission is requested at runtime.  

--------------------------------------------------------
🧱 PROJECT STRUCTURE:
--------------------------------------------------------
📂 MainActivity.kt → UI + scheduling exact alarms  
📂 AlarmBroadcastReceiver.kt → Handles alarm triggers and starts service  
📂 AlarmService.kt → Foreground Service that shows notification and plays sound  
📂 AndroidManifest.xml → Declares permissions, service, and receiver  

--------------------------------------------------------
🧰 BUILD & RUN:
--------------------------------------------------------
• 🧩 Minimum SDK: 24  
• 🎯 Target / Compile SDK: 36  
• 🧠 Recommended IDE: Android Studio Giraffe or newer  

▶️ Steps:
1. Open the project in Android Studio.  
2. Grant required permissions (Exact Alarms & Notifications).  
3. Build and Run on a real or virtual device with audio output.  
4. Tap “Create Alarm” and observe notification & sound.  
5. Use “Cancel Alarm” to stop it anytime.  

--------------------------------------------------------
💡 NOTES & TIPS:
--------------------------------------------------------
✨ If you set a past time, the app automatically moves the alarm to the next day.  
🔁 Alarms are **not persistent** after device reboot (no BOOT_COMPLETED receiver).  
🔊 If you don’t hear the alarm, check your Alarm / Notification volume level.  
🧩 Foreground service ensures the alarm keeps working even when the app is closed.  

--------------------------------------------------------
🧠 ANDROID CONCEPTS COVERED:
--------------------------------------------------------
- AlarmManager & PendingIntent  
- BroadcastReceiver  
- ForegroundService & NotificationChannel  
- MediaPlayer  
- Runtime Permissions  
- Uri.parse(), startActivity(), ContextCompat & ActivityCompat  
- Modern service handling (Android O+ compliance)  

--------------------------------------------------------
👩‍💻 Screenshot:
--------------------------------------------------------
<br>

<img width="300" height="847" alt="Screenshot 2025-10-22 102039" src="https://github.com/user-attachments/assets/3f810a29-a185-42c7-86d5-9a4107e9edd9" />
<img width="300" height="841" alt="Screenshot 2025-10-22 102141" src="https://github.com/user-attachments/assets/cb0e13ed-8a3c-4901-b75b-f510ff10acb9" />
<img width="300" height="841" alt="Screenshot 2025-10-22 102258" src="https://github.com/user-attachments/assets/55e63595-3987-40e3-8237-bff4d52e7af5" />
<img width="300" height="844" alt="Screenshot 2025-10-22 102541" src="https://github.com/user-attachments/assets/332e07e0-3f88-4451-a80f-c1c547c95157" />
<img width="300" height="843" alt="Screenshot 2025-10-22 102636" src="https://github.com/user-attachments/assets/0b314075-b6c3-49ea-9b4a-c4f6d614c306" />
<img width="300" height="841" alt="Screenshot 2025-10-22 102714" src="https://github.com/user-attachments/assets/d86f93ee-1d83-48cc-8c07-8d5323c85adb" />





