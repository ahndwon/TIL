Intent

# Intent, Bundle
passive data structure holding an abstract description of an action to be performed

해야할 액션들이 담긴 자료구조

## 사용처
- 액티비티 실행

- 서비스 실행

- 브로드캐스트 전달
  -> 브로드캐스트는 어떤 앱이나 수신할 수 있는 메세지다.
  
  
## 인텐트 종류
- 명시적 인텐트 
    실행할 앱을 명시적으로 지정.(패키지명이나 컴포넌트 클래스 이름 지정)
    명시적 인텐트를 통해 액티비티 실행하거나 서비스 실행 가능.
    
- 암시적 인텐트
    실행할 컴포넌트를 명시적으로 지정하지 않고 수행할 일반적인 액션을 선언하여 다른 다란 앱의 컴포넌트가 처리할 수 있도록 한다.
    
    
    
## 암시적 인텐트 수신
수신할 인텐트를 Android Manifest에 <intent-filter>로 명시.


## Pending Intent
인텐트 객체의 래퍼(wrapper) 객체
외부의 앱이 내부의 인텐트를 마치 내 앱의 프로세스에서 실행된거 마냥 사용할 수 있도록 함

    주 사용처
        - Notification을 통해 유저가 상호작용 할 때 실행될 인텐트 선언
        - App widget를 통해 유저가 상호작용 할 때 실행될 인텐트 선언
        - 미래에 실행될 인텐트 (ex. AlarmManager가 Intent 실행)
        
        
### 사용 방법
생성시 어떤 인텐트를 사용할지 정해야됨

- Activity 시작을 위한 Intent -> PendingIntent.getActivity()
- Service 시작을 위한 Intent -> PendingIntent.getService()
- BroadcastReceiver 시작을 위한 Intent -> PendingIntent.getBroadCast()
- 

#Bundle
A Bundle is very much like a Java Map object that maps String keys to values. It's used to pass information between activities and other application components. It's also used by the framework to capture and restore state information.

The reason Android doesn't use plain old Map objects for this is that Map is too flexible; it can contain objects (such as, say, I/O streams) that cannot be serialized. The Bundle API restricts the types of objects that can be added to a bundle in such a way that the bundle's contents are guaranteed to be serializable. The Android framework relies on this property.

I suggest that you read the documentation on Application Fundamentals. This explains, among other things, what bundles and intents are and what they are used for.

# 사용 방법
Your first create a Bundle object

`Bundle b = new Bundle();`
Then, associate the string data stored in anystring with bundle key "myname"

`b.putString("myname", anystring);`
Now, create an Intent object

`Intent in = new Intent(getApplicationContext(), secondActivity.class);`
Pass bundle object b to the intent

`in.putExtras(b);`
and start second activity

`startActivity(in);`
In the second activity, we have to access the data passed from the first activity

`Intent in = getIntent();`
Now, you need to get the data from the bundle

`Bundle b = in.getExtras();`
Finally, get the value of the string data associated with key named "myname"

`String s = b.getString("myname");`








