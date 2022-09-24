object VersionsBuild {
    const val applicationId = "com.example.marvelapp"
    const val versionName = "1.0"
    const val compileSdk = 31
    const val buildTools = "30.0.3"
    const val targetSdk = 30
    const val minSdk = 23
    const val versionCode = 1
    const val build = "7.1.3"
    const val kotlin = "1.6.21"
    const val gsm = "4.3.10"
    const val firebasePlugin = "2.9.1"
    const val detektVersion = "1.20.0"
}

object VersionsApp {
    const val core = "1.6.0"
    const val appcompat = "1.3.1"
    const val constraint = "2.1.0"
    const val legacy = "1.0.0"
    const val navigation = "2.3.2"
    const val lifecycle = "2.4.0"
    const val dataStore = "1.0.0"
    const val room = "2.4.2"
    const val paging = "3.1.1"
    const val material = "1.4.0"
    const val coroutines = "1.6.0"
    const val lottie = "4.1.0"
    const val koin = "3.1.2"
    const val glide = "4.12.0"
    const val shimmer = "0.5.0"
    const val firebase = "30.1.0"
    const val firebaseCore = "21.0.0"
    const val okhttp = "4.9.0"
    const val retrofit = "2.9.0"
    const val javax = "1"
    const val gson = "2.8.6"
}

object VersionsTest {
    const val marvelTest = "com.example.marvelapp.KoinTestRunner"
    const val mockitoCore = "2.0.0"
    const val mockWeb = "4.9.3"
    const val fragmentTest = "1.4.1"
    const val espresso = "3.1.0"
    const val junitExt = "1.1.2"
    const val androidTest = "1.4.0"
    const val mockk = "1.12.4"
    const val koinTest = "3.0.1"
    const val mockitoAndroid = "2.22.0"
    const val junitTest = "4.13.2"
    const val archCore = "2.1.0"
    const val coroutinesTest = "1.6.0"
}

object AppVersions {

    //Build
    const val build = "com.android.tools.build:gradle:${VersionsBuild.build}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${VersionsBuild.kotlin}"
    const val gsm = "com.google.gms:google-services:${VersionsBuild.gsm}"
    const val firebasePlugin = "com.google.firebase:firebase-crashlytics-gradle:${VersionsBuild.firebasePlugin}"

    // AndroidX
    const val core = "androidx.core:core-ktx:${VersionsApp.core}"
    const val appcompat = "androidx.appcompat:appcompat:${VersionsApp.appcompat}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${VersionsApp.constraint}"
    const val legacy = "androidx.legacy:legacy-support-v4:${VersionsApp.legacy}"

    // Navigation
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:${VersionsApp.navigation}"
    const val navUi = "androidx.navigation:navigation-ui-ktx:${VersionsApp.navigation}"
    const val navPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${VersionsApp.navigation}"

    // ViewModel and LiveData
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${VersionsApp.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${VersionsApp.lifecycle}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${VersionsApp.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${VersionsApp.lifecycle}"

    //DataStore
    const val dataStore = "androidx.datastore:datastore-preferences:${VersionsApp.dataStore}"

    // Room
    const val roomKtx = "androidx.room:room-ktx:${VersionsApp.room}"
    const val roomRunTime = "androidx.room:room-runtime:${VersionsApp.room}"
    const val roomPaging = "androidx.room:room-paging:${VersionsApp.room}"
    const val roomCompiler = "androidx.room:room-compiler:${VersionsApp.room}"

    // Paging3
    const val paging = "androidx.paging:paging-runtime-ktx:${VersionsApp.paging}"

    // Material design
    const val material = "com.google.android.material:material:${VersionsApp.material}"

    //Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${VersionsApp.coroutines}"

    //Lottie animation
    const val lottie = "com.airbnb.android:lottie:${VersionsApp.lottie}"

    // Koin
    const val koinCore = "io.insert-koin:koin-core:${VersionsApp.koin}"
    const val koinAndroid = "io.insert-koin:koin-android:${VersionsApp.koin}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${VersionsApp.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${VersionsApp.glide}"

    // Shimmer
    const val shimmer = "com.facebook.shimmer:shimmer:${VersionsApp.shimmer}"
}

object CoreVersions {
    const val pagingCore = "androidx.paging:paging-common:${VersionsApp.paging}"

    // OkHttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${VersionsApp.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${VersionsApp.okhttp}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${VersionsApp.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${VersionsApp.retrofit}"

    //Coroutines Core
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${VersionsApp.coroutines}"

    //Javax Inject
    const val javax = "javax.inject:javax.inject:${VersionsApp.javax}"

    //Gson
    const val gson = "com.google.code.gson:gson:${VersionsApp.gson}"
}

object TestVersions {
    const val roomTest = "androidx.room:room-testing:${VersionsApp.room}"
    const val mockitoCore = "org.mockito:mockito-core:${VersionsTest.mockitoCore}"
    const val mockWeb = "com.squareup.okhttp3:mockwebserver:${VersionsTest.mockWeb}"
    const val fragmentTest = "androidx.fragment:fragment-testing:${VersionsTest.fragmentTest}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${VersionsTest.espresso}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${VersionsTest.espresso}"
    const val junit = "androidx.test.ext:junit:${VersionsTest.junitExt}"
    const val testCore = "androidx.test:core:${VersionsTest.androidTest}"
    const val testRunner = "androidx.test:runner:${VersionsTest.androidTest}"
    const val testRules = "androidx.test:rules:${VersionsTest.androidTest}"
    const val mock = "io.mockk:mockk-android:${VersionsTest.mockk}"
    const val koinTest = "io.insert-koin:koin-core:${VersionsTest.koinTest}"
    const val mockitoAndroid = "org.mockito:mockito-android:${VersionsTest.mockitoAndroid}"

    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${VersionsTest.mockitoCore}"

    // Unit tests
    const val junitTest = "junit:junit:${VersionsTest.junitTest}"
    const val coreTesting = "androidx.arch.core:core-testing:${VersionsTest.archCore}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${VersionsTest.coroutinesTest}"
}