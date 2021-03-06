# Photo Gallery

This photo gallery application lets users browse randomly selected images from Unsplash.com and save or download them.   

## Table of Contents
 - [Application Overview](#Application-Overview)
 - [Architectural Overview](#Architectural-Overview)
 - [Project Requirements Met](#Project-Requirements)
 - [Installation](#Installation)
 - [Application Creation & Learning Process](#Application-Creation-and-Learning-Process)
 - [Technologies Used](#Technologies-Used)
 - [Resources Used & Credits](#Resources-Used-and-Credits)
 
## Application Overview
Users may save their favorite images to the application by tapping the image and tapping the heart button. Favorite images are viewable in the favorites screen.  
<br/>
Users may also download images to their internal storage by selecting "download" from the overflow menu in the top app bar, switching on for the images they're interested in, and tapping "Start Download".    
<br/>
Downloaded images can be found in:   
Internal storage>Android>data>com.onramp.android.takehome>files>Pictures>galleryApp.   
<br/>
The search option is a work in progress, it currently does not function.
<br/>
Try the app here: [APK Link](https://drive.google.com/file/d/1vGZciqb5i_0QqLpZ_kQ2m1kQEaLYNCqY/view?usp=sharing)
<br/>
<img src="https://github.com/malinda-lin/Android-Image-Gallery-App/blob/main/public/photo_gallery.png" width="200">
<img src="https://github.com/malinda-lin/Android-Image-Gallery-App/blob/main/public/photo_gallery_save_favorites.gif" width="200">

## Architectural Overview
This application currently has two core features and those features are separated into two activities (Explore & Favorites). These activities share core UI components such as the top app bar, "GridView", and bottom navigation. Both activities follow the model view presenter (MVP) architecture pattern to decouple the UI and the back end implementation. Both activities deal with sending and receiving image data so they share a repository for the implementation of those functions.   
<br/>
The data sources are divided into local and remote. The local data source utilizes Android Room to store data and retrieve data while the remote data source utilizes the Unsplash API only to retrieve data.   
<br/>
For the file structure, files are divided by feature and purpose. For example, all files related to the explore activity are grouped, these include the explore activity, explore presenter, and explore contract. Files that are reused and shared such as fragments are grouped under the category of fragments, these include UI components like the bottom navigation and top app bar.   
<br/>
See [Application Creation and Learning Process](#Application-Creation-and-Learning-Process) to learn about how I would optimize this structure.

## Project Requirements
This section goes over how I addressed the requirements below:
- [Activities and Fragment Components](#Activities-and-Fragment-Components)
- [Service Component](#Service-Component)
- [MVP Architecture Pattern](#MVP-Architecture-Pattern)
- [REST API](#REST-API)
- [UI Components from Android Material Design Component Library](#UI-Components-from-Android-Material-Design-Component-Library)
- [Data Persistence (Android Room)](#Data-Persistence-(Android-Room))

### Activities and Fragment Components
This application has "Explore" and "Favorites" activities. The top app bar and bottom navigation are implemented as fragments.

### Service Component
This application's image downloading feature is a background service. I chose this feature as such because file downloads should continue even when users leave the application.   
<br/>
<img src="https://github.com/malinda-lin/Android-Image-Gallery-App/blob/main/public/photo_gallery_download_feature.gif" width="200">

### MVP Architecture Pattern
This application follows the MVP architectural pattern. This pattern allows for easier testing and separation of concerns. For each activity, I utilize a presenter that regulates the communication between the view (UI) and the model (data layer). The view receives user input or interactions and responses by updating the UI after the presenter provides the data. The presenter and view are linked through the contract which cleanly lay out the executable functions. The implementation for those functions are stored in the presenter. The data layer communicates with the presenter through the repository. The repository stores implementation for requesting or sending data to and from the data sources.    

Below is an example specific to the explore activity:   

<img src="https://github.com/malinda-lin/Android-Image-Gallery-App/blob/main/public/explore_getImages_data_flow.png" width="600">

### REST API
This application uses the Retrofit API interface to make GET requests from the Unsplash API. I chose the random images API route so that users could see different images every time they open the "Explore Activity". This API required registering for an API key. I stored this API key using BuildConfig to avoid exposing it.

### UI Components from Android Material Design Component Library
<img src="https://github.com/malinda-lin/Android-Image-Gallery-App/blob/main/public/photo_gallery_UI_components.png" width="450">
UI Components Used: App Bar: Top, Switches, Cards, Button, Bottom Navigation, Snackbar   

### Data Persistence (Android Room)
This application uses Android Room to store image data of images favored by users. This local database is set up through 3 key components. (Image Class, Image Data Access Object, Database)   
The image data class defines the image properties and their types so the database knows what it's storing. The image data access object or DAO is where the query types are defined and implemented. The image database class builds and create an instance of the room database. The database instance is used by the image repository to implement data transfers between the model and view. In this application, every time a user favors an image, the presenter triggers the repository and an instance of the image class is created and stored to the database using a database instance.

## Installation  
Using your phone or emulator: [APK Link](https://drive.google.com/file/d/1vGZciqb5i_0QqLpZ_kQ2m1kQEaLYNCqY/view?usp=sharing)  
````
  Phone -> Install .apk file on your phone and enable "download from unknown sources"

  Emulator -> Drag file to emulator
````
Using this repo:   
````
  git clone <this repo>
  Connect device (phone/emulator)
  Press "Run app" (Android Studio)
````

## Application Creation and Learning Process
To start this project, I outlined the requirements to use for the basis of a rough UI design. Next, I researched project requirements that were unfamiliar to me, mainly the MVP architecture pattern. Then, I outlined the file structure and potential files that I would need in order to follow this architecture pattern. Finally, I started developing my application by referring to my UI design.   

Since this was my first native Android application, I heavily relied on documentation, video tutorials, Stack Overflow, and Logcat. I celebrated every small victory and successfully implemented features but as I was developing, I was also thinking about how I could optimize. In future development, I would create my Explore Activity and Favorites activity as fragments hosted on a main activity because they share many UI components. This would eliminate the need for the top app bar and bottom navigation as fragments. Instead, I would include them as regular view components in the main activity layout.</b> Looking back on my code, I can see that there is much refactoring to do and additional best practices to be applied.   

Nonetheless, this project was an intense and rewarding learning experience. I used Android Studio to generate an .apk file and was so excited to see it running on my phone. Now, I look forward to building out my other ideas for the Android platform.   

## Technologies Used
- Android Studio (IDE)
- Kotlin (Language)
- Retrofit (REST Client built on OkHttp)
- Glide (Image Loading Framework)
- Android Room (Data Persistence, Local Database)
- Unsplash API (Third Party API)

## Resources Used and Credits

[Android Dev Docs on App Architecture](https://developer.android.com/jetpack/guide)   
[MVP Architecture Kotlin Code Example](https://github.com/android/architecture-samples/tree/todo-mvp-kotlin)   
[MVP Architecture Video](https://www.youtube.com/watch?v=h0Y1R2mL7Ys&list=PLEVlop6sMHCqragWmqo7JF92QXQoidRmu&index=2)   
[MVP Architecture Tutorial](https://www.raywenderlich.com/7026-getting-started-with-mvp-model-view-presenter-on-android)    
[Logcat Video Tutorial](https://www.youtube.com/watch?v=pqH69LcD2Kk)   
[Retrofit Documentation](https://square.github.io/retrofit/)   
[Retrofit Video Tutorial](https://www.youtube.com/watch?v=rAk1j2CmPJs&t=235s)   
[Android Room Documentation](https://developer.android.com/training/data-storage/room)   
[Android Room Video Tutorial](https://www.youtube.com/watch?v=lwAvI3WDXBY&t=728s)   
[Glide - Image Loading Library](https://github.com/bumptech/glide)   
[Using "setTag" Method](https://www.codota.com/code/java/methods/android.widget.ImageView/setTag)   
[Using GridView and BaseAdapter Video Tutorial](https://www.youtube.com/watch?v=ReUBY5i9ojY&t=1002s)  
[Coroutine Kotlin Language Documentation](https://kotlinlang.org/docs/reference/coroutines/coroutine-context-and-dispatchers.html#coroutine-context-and-dispatchers)   
[Coroutine Beginner Video Tutorial](https://www.youtube.com/watch?v=F63mhZk-1-Y)   
[Guide to Storing Secret Keys](https://guides.codepath.com/android/Storing-Secret-Keys-in-Android)   
[Adding Fragments Video Tutorial](https://www.youtube.com/watch?v=-vAI7RSPxOA)   
[Unsplash API](https://unsplash.com/documentation#get-a-random-photo)   
[Extracting APK](https://www.educative.io/edpresso/extracting-an-apk-file-from-android-studio)   
[Stack Overflow](https://stackoverflow.com/questions/7871430/increase-the-grid-spacing-in-android)   
[Material Design](https://material.io/)      
