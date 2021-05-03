# Fake-Call-App-with-SOS-Alert
Abstract 

The aim of the project is to develop an Android application that lets its users to send 
Notifications to their trusted contacts  in case of an emergency or a panic situation. The users can send messages with the press of a single button in the app or on the press of simple panic trigger button. The phone numbers and the contents of the text messages is preset from within the application. The text messages sent, along with the content, also have the last known Geo-location of the user. This is very helpful in tracking the whereabouts of the person. This application also comes with a feature to place a Fake call to the user’s android device to help the user to get out of unexpected or unwanted situations.

1.Introduction

SOS (which stands for Save Our Souls or Save Our Ships) has primarily been used as an International Morse code distress signal. It is commonly used in navigation by Sailors when under attack by Pirates or when they need help of some kind. But the signal is not limited to navigation and is used in a more general sense whenever a notification has to be sent about a situation that requires immediate attention.

As much as we would like to get rid of them, panic or emergency situations are unavoidable and usually unexpected.If the nature is situations is not dangerous then it can be handled in a slightly different manner that leads to concept of Fake Call. Fake call is that which seem real but tries to fool the bystander. The incoming fake call proves helpful in bailing out from the the situation.The nature and consequences of these situations can vary significantly and in worst cases also be life threatening. Therefore it would be really nice to have some mechanism by which we can notify certain people about such circumstances and increase the chances of receiving help as soon as possible. The need for such a mechanism increases even more as in this era of technology, platforms exist to support them.

 One such platform and a very common one in that is a Smartphone. Almost everyone today carry a Smartphone with them as they become more and more affordable and easily available. Also within the Smartphone market Android is the clear leader in terms of market share.  Hence developing an Android application becomes an obvious choice.
 
1.1. Project Description

Fake call app is an application that is meant to run on Android devices mainly smartphones. The main functions and features of the application are –

Features:
i.Panic triggers to send distress signals to trusted contacts.
		On holding the volume down button for three seconds triggers the activities such as get location access and send text message.

ii.SQLite Database to manage all the contact information.
	     Two databases are used to store trusted contact information and fake caller information 

iii.Distress signals can also be sent by push of a button.
		 On press of SOS alert button also triggers activities such as get location access and send text message.

iv.Timer to trigger fake calls.
	A timer triggers the Fake call after the time expires, that is specified by the user.

v.Fake Caller UI design made to seem realistic.
	Simple UI design made easy for the user.


2.Proposed System

2.1.Module Description:

i.Services – This is the component of the application that is typically used to perform long background tasks that do not have a user interface. For example – a service is used to track the location of the device. 

ii.Receivers – This is the component of the application that typically listens for some events or responses from other services. For example – A receiver is used to fetch the location co-ordinates from the location service and then add this location to the text message.
 
iii.SQLite – Android platform provides libraries for SQLite database. A SQLite database is a relational database that is local to an Android device. It requires no configuration and is available to use for an app developer. For example – SQLite is used in the app to store various information about his trusted contacts and caller contact. 

iv. Location Tracker – It is used to fetch the location of the Android device. The app uses  the GPS provider  to find the location for the device. GPS provides accurate data about the location.
 
3.Conclusion

Fake Call App is an essential app to have on a Smartphone. It is a personal security app that lets you send notifications to certain people via text messages in case of emergencies. The text messages sent also have location link which would redirect to google maps. Faking the incoming  call is really helpful in bailing out from unwanted situations.Fake Call App was my first attempt at an Android application. It gave us very good exposure to the Android platform and mobile development in general. The app enabled me in understanding the basic of Android development and learning about SQLite databases, Google Maps API for Android.

4.Future Work

The current work on the Fake Call app has a lot of essential features that would be used in case of an emergency situation like distress messages making fake incoming calls  from within the app on tap of a single button. An app for such a purpose has a lot of scope for enhancement. In the future the app may include features like – 

i.A home screen widget that can be used as a triggering point to send panic 	notifications. A user would then not have to open the app to send these panic 	notifications.
 
ii.Initiating a call to a number set from within the application when the user 	presses the panic button. 

iii. The app can also listen to incoming messages from the set contacts. If these 	message have a pre-defined text like “UPDATE LOCATION” the app can reply 	with a text message containing the current location or for some other text like 	“AUDIO” in which case the app can record a short audio and send it as an email 	to the person. This is very helpful as you may have already pressed the panic 	button and may be in some trouble where you cannot reply. This way the person 	can track you constantly and also understand something about the nature of the 	emergency from the audio clip.
