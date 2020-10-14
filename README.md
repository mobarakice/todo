# Sample TODO Android App Architecture

The Sample TODO app has been developed for demonstrating the MVVM (Model View ViewModel ) design pattern and Android Architecture component (Navigation component, LifeCycle, ViewModel, LiveData, Databinding), Retrofit 2 for network operation, and RxJava for handling asynchronous and background tasks. In this document will describe project architecture and all API's by the following below sections:
- App architecture blueprint
- Project structure with app process flow (based on the packaging)
- API short print with references

## App architecture blueprint
We are using MVVM design patterns and to flow best practices. The chart below will show architecture details.
![app_architecure_diagram](/blob/master/app_architecure_diagram.jpg)

## Project structure with app process flow (based on the packaging)
The app has following packages:
- **data:** It contains all the data accessing and manipulating components.
- **ui:** View classes along with their corresponding ViewModel.
- **utility:** Utility classes.

![Screen navigation flow:](/home/mobarak/Pictures/app_architecure_diagram.jpg)

## API short print with references
Below library and API has been used to develop this app.
- MVVM design pattern [Medium Post](https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b)
  & [Wiki](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
- Navigation component to manage all kinds of screen transactions, for more checkout [here](https://developer.android.com/guide/navigation/navigation-getting-started)
- ViewModel reference [Android Developer site](https://developer.android.com/topic/libraries/architecture/viewmodel) & [Medium Post](https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e)
- LiveData reference [Android Developer site](https://developer.android.com/topic/libraries/architecture/livedata)
- Room database reference [Android Developer site](https://developer.android.com/training/data-storage/room)
- Android Data binding reference
https://developer.android.com/topic/libraries/data-binding
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
