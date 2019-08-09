# Automatic-Android-Alert-Notification-System
*Author: GARGEE SRIVASTAVA
 *Contact details: srivastava.gargee@gmail.com
 *Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

Abstract

The goal of this project is to design a notification system using Android application to connect it to the educational web server of the college.
It achieve high and quick organized communication between instructor and students, save time, effort by connecting Android application to the educational database of the college.
. It provides a wide range of information about educational performance,attendance, state of student in classroom and  helps in sending the notification to selected teacher or student devices automatically.
It includes various types of alerts like text,speech,vibration,image or a screen flash.The required alert can be chosen according to circumstances.

INTRODUCTION:

It was built to enable and demonstrate the  next-generation of smart-classrooms which is based on real  time-feedback driven so that the instructor is always  notified 
of the attention ,understanding and involvement of the  students during the lecture. Based on this real time  feedback, the instructor can evaluate himself 
and  change his/her teaching pattern to make the class  more engaging as well as monitor the activity of the  students in the classroom .
Also, the students can get the real time updates of their activity in class like attendance, marks, mental status in the class etc. 
For this a special automatic alert system has to be developed to minimize the human interactions.
The Android SDK provides the tools and APIs necessary to begin developing applications on the Android platform using the Java programming language .
It uses a web page for  all information about the students and alert id  already exist in the mysql database , and the client side is the android application 
for students. PHP is the most commonly used as a server-side scripting language. To make PHP work as a server-side scripting language ,
I had used a PHP parser and a web server.


Steps Involved

1.	Wrote PHP scripts to access the database informations in server side

2.	Parsed the PHP output into JSON stands for JavaScript Object Notation. 
JSON is a lightweight format for storing and transporting data.
JSON is often used when data is sent from a server to a web page. JSON is "self-describing" and easy to understand.


3.	Created a request handler to  send and process message and Runnable objects associated with a thread's message queue.

4.	Verified the connection between the server and client android device and their communications protocols.

5. used AsyncTask

Android AsyncTask is an abstract class provided by Android . It gives us the liberty to perform heavy tasks in the background 
and keep the UI thread light thus making the application more responsive.
Android application runs on a single thread when launched. Due to this single thread model tasks that take longer time to fetch the response can make the application non-responsive. 
To avoid this I had used android Async Task to perform the  task i.e fetching the data from server in background on a dedicated thread and passing the results back to the UI thread. 
The result can be any alert type that needs to be triggered. Hence use of Async Task in android application keeps the UI thread responsive at all times.

The basic methods used in an android AsyncTask class are defined below :

•	doInBackground() : This method contains the code which needs to be executed in background. In this method we can send results multiple times to the UI thread by publishProgress() method. To notify that the background processing has been completed we just need to use the return statements. I had taken the data through handler in this method. 
•	onPreExecute() : This method contains the code which is executed before the background processing starts

•	onPostExecute() : This method is called after doInBackground method completes processing. Result from doInBackground is passed to this method
•	The three generic types used in an android AsyncTask class are given below :
•	Params : The type of the parameters sent to the task upon execution
•	Result : The type of the result of the background computation

6.	Try various alert notifications.


ALERT NOTIFICATIONS TYPE:
1.	Vibration alert:   It was developed easily using the vibration methods present in android.

2.	Text alert:   I had used used Snackbars for that as it provide lightweight feedback about an operation.
They show a brief message at the bottom of the screen on mobile I had used the indefinite time span to make the user see the message 
forcefully and it will  disappear when swiped off screen.

3.	Audio alert: I had used text to speech method where the text can be retrieved from the database and the required text can later be
converted to speech and sent to the client devices.

4.	Image: Images can be retrieved from the database.

5.	Screen Flash: I had put a permanent layout on the top of the existing layout and set its visibility to gone.
When the client device will receive that it has to show the screen flash then it will run a timer task thread for 2 seconds 
and set the visibility of the layout to visible and then again it tracks back to the same invisible state when the thread will stop.


