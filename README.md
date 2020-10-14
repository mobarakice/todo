# Sample TODO Android App Architecture

The Sample TODO app has been developed for demonstrating the MVVM (Model View
ViewModel ) design pattern and Android Architecture component (Navigation component,
LifeCycle, ViewModel, LiveData, Databinding), Retrofit 2 for network operation, and RxJava for
handling asynchronous and background tasks. In this document will describe project architecture
and all APIs by following below sections:
● App architecture blueprint
● Project structure with app process flow (based on the packaging)
● API short print with references

## App architecture blueprint

We are using MVVM design patterns and to flow best practices. The chart below will show
architecture details.

## Project structure with app process flow (based on the packaging)

The app has the following packages:


```
● data ​: It contains all the data accessing and manipulating components.
● ui ​: View classes along with their corresponding ViewModel.
● utility ​: Utility classes.
```
Screen navigation flow:

## API short print with references

Below library and API has been used to develop this app.
● MVVM design pattern
https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewm
odel-e7eeee76b73b​)
https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel
● Navigation component to manage all kinds of screen transactions, for more checkout
here: ​https://developer.android.com/guide/navigation/navigation-getting-started
● ViewModel reference
https://developer.android.com/topic/libraries/architecture/viewmodel
https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e
● LiveData reference
https://developer.android.com/topic/libraries/architecture/livedata
● Room database reference
https://developer.android.com/training/data-storage/room
● Android Data binding reference
https://developer.android.com/topic/libraries/data-binding
● RxJava and RxAndroid
https://github.com/ReactiveX/RxJava
https://github.com/ReactiveX/RxAndroid
