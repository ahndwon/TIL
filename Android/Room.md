Room

# Room
## Introduction


![Architecture Components](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/img/a7da8f5ea91bac52.png)


**Entity**
Room으로 작업할 때 데이터베이스 테이블을 나타내는 어노테이트된 클래스

**SQLite database**
On device storage. Room persistence library가 이 데이터베이스를 만들어주고 관리해준다.

**DAO**
Data Access Object. SQL 쿼리를 함수로 매핑한 것. DAO를 사용할때 메소드를 호출하면, 룸이 나머지를 알아서 해준다.

**Room database**
데이터베이스 작업을 간단화해주고 SQLite database로의 access point 역할을 함(SQLiteOpenHelper를 숨긴다). Room database는 DAO를 사용해서 SQLite database로 쿼리를 보낸다.

**Repository**
여러 데이터 소스들을 관리하기 위해 사용

**ViewModel**
Repository(data)와 UI 사이의 커뮤니케이션 센터. UI는 더 이상 데이터의 근원에 신경 안 써도 된다.ViewModel 인스턴스는 액티비티/프래그먼트 재생성에서도 살아남는다.

**LiveData**
observe 될 수 있는 데이터 홀더 클래스. 항상 가장 최신 데이터를 **소유/캐쉬** 한다. 데이터가 변경 될 경우 옵저버들에게 알려준다. LiveData는 라이프사이클에 귀속된다. UI 컴포넌트는 그냥 관련된 데이터를 observe하고 멈추가나 재개 하지 않는다. LiveData는 라이프사이클 상태 변화에 귀속되기 때문에 이것들을 자동적으로 관리한다.


## RoomWordSample
[Android Room with a View - Kotlin](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0)

![architecture](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/img/1205d9f95688b35b.png)

### Update gradle

`apply plugin: 'kotlin-kapt'`

```gradle
// Room components
implementation "androidx.room:room-runtime:$rootProject.roomVersion"
implementation "androidx.room:room-ktx:$rootProject.roomVersion"
kapt "androidx.room:room-compiler:$rootProject.roomVersion"
androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"

// Lifecycle components
implementation "androidx.lifecycle:lifecycle-extensions:$rootProject.archLifecycleVersion"
kapt "androidx.lifecycle:lifecycle-compiler:$rootProject.archLifecycleVersion"
androidTestImplementation "androidx.arch.core:core-testing:$rootProject.androidxArchVersion"

// ViewModel Kotlin support
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.archLifecycleVersion"

// Coroutines
api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$rootProject.coroutines"
api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines"


kotlin {
    experimental {
        coroutines "enable"
    }
}
```




