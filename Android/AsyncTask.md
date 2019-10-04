AsyncTask

# AsyncTask
기본정그로 UI 스레드에서 일어나는 부하를 줄여주기 위함.
몇 초 이내에 끝나는 짧은 작업을 위함.
비동기를 위한 추상화 클래스

`private class SomeTask extends AsyncTask<Params,Progress,Result>{
`

AsyncTask의 제네릭 타입들 

 1. Params -> 백그라운드 실행에 사용될 파라미터의 타입
 
 2. Progress -> main UI Thread에서 보여줄 progess를 위한 progress
 
 3. Result -> 백그라운드 작업 완료 후 얻는 결과
 
 
 
 ## 단점
    1. Block Main Thread 
    이미 AsyncTask가 실행 중일 때 또 하나의 AsyncTask가 실행될 경우 (버튼 재클릭, 혹은 화면 회전 등) 두번째 AsyncTask의 onPostExecute가 첫번쨰 AsyncTask가 끝날때까지 기다리게 된다.
    -> 앱 Freezing 발생
    
    2. 메모리 낭비
    AsyncTask가 동작 중에 화면 회전이 발생할 꼉우 새로운 액티비티와 AsyncTask가 생성되면서 메모리 낭비가 발생한다.
    
    3. 메모리 누수
    AsyncTask가 동작 중에 화면 회전이 발생하면 액티비티가 파괴되고 새로운 액티비티와 AsyncTask가 동작하는데 이때 첫번째 AsyncTask가 완료되면 결과를 기존 액티비티에 전달하려고 하지만 파괴되었으므로 메모리 누수가 발생한다.