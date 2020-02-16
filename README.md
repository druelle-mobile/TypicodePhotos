# Typicode Photos app

This Android application is my personal view of an exercise realized for **[Ad Scientiam](https://www.adscientiam.fr/)**.

See the following statements.

## Statements

### Estimated time

The development of this project is estimated around 1 to 2 hours.

### Objectives

Realize an Android application with Kotlin language, showing an items list, received with the following webservice :

**[http://jsonplaceholder.typicode.com/photos](http://jsonplaceholder.typicode.com/photos)**

The project must be versioned on a GIT repository.

Downloaded data must be available offline, even after an application's reboot.

A Unit Test class must be present.

### Pay attention on

* Code quality
* Architecture and used patterns
* Frameworks choice
* Configuration changes
* Relevance of commits

## JSONPlaceholder by Typicode

**[JSONPlaceholder](https://jsonplaceholder.typicode.com/)** is a free online REST API that you can use whenever you need some fake data.

It's great for tutorials, testing new libraries, sharing code examples, ...

## Conception

### Android requirements

**Minimum version :** Android 23 - Marshmallow (6.0)

**Target version :** Android 29 - Android 10

**Compile version :** Android 29 - Android 10

### Architecture and Dependencies

* Kotlin - [1.3.61](https://github.com/JetBrains/kotlin/releases/tag/v1.3.61)
* Gradle build - 3.5.3
* MVVM Pattern
* Android Architecture Components from [Jetpack](https://developer.android.com/jetpack)
    * [Navigation](https://developer.android.com/guide/navigation/)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Room](https://developer.android.com/topic/libraries/architecture/room)
    * [DataBinding](https://developer.android.com/topic/libraries/data-binding/)

* [GSON](https://github.com/google/gson) : to serialize and deserialize POJO/POKO to JSON and reverse
* [Retrofit](https://github.com/square/retrofit) : to make REST API calls
* [Koin](https://github.com/InsertKoinIO/koin) : for dependency injection
* [Material](https://github.com/material-components/material-components-android) : to use Material Design UI components
* [Constraint Layouts](https://developer.android.com/jetpack/androidx/releases/constraintlayout) : to simplify layouts' conception
* [Picasso](https://github.com/square/picasso) : to easily download images
* [SDP](https://github.com/intuit/sdp) : to provide a new unit size, that easily scale with different screen's sizes (SDP for Scalable DP)
* [SSP](https://github.com/intuit/ssp) : to provide a new unit size, that easily scale with different screen's sizes (SSP for Scalable SP)
* [Timber](https://github.com/JakeWharton/timber) : to easily get logs in application
* [Stetho](https://github.com/facebook/stetho) : to debug application
* [EasyPermissions](https://github.com/googlesamples/easypermissions) : to easily manage permissions


## Author

**Geoffrey Druelle** - [geoffrey-druelle.ovh](https://geoffrey-druelle.ovh)
