# Retrofit
Square가 제공하는 안드로이드와 Java 애플리케이션을 위한 라이브러리, 안전한 타입(type-safe) 방식의 HTTP 클라이언트이다. 
안전한 타입 방식의 HTTP 클라이언트이므로 URL 생성이나 매개 변수의 설정 등을 걱정할 필요없이 네트워크로 보낼 쿼리 필요만 알면 된다. Retrofit을 사용하면 몇몇 인터페이스를 작성하는 것으로 이런 작업을 쉽게 할 수 있다.


## 요청 메소드
Retrofit은 어노테이션을 통해 요청 메소드를 정의한다.
기본으로 제공하는 요청 메소드는 GET, POST, PUT, DELETE, HEAD 이다

```java
@GET("/users/list")

//정적 쿼리 인자를 URL에 명시할 수 도 있다.
@GET("/users/list??sort=desc")
```


## 사용법
Retrofit은 HTTP API를 자바 인터페이스 형태로 사용한다.

```java
public interface GitHubService {
  @GET("/users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
```

Retrofit 클래스로 GitHubService 인터페이스를 구현하여 생성한다.
```java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com")
    .build();

GitHubService service = retrofit.create(GitHubService.class);
```

각각의 Call은 GitHubService를 통하여 동기 또는 비동기하는 HTTP 요청을 원격 웹서버로 보낼 수 있다.

```java
Call<List<Repo>> repos = service.listRepos("octocat");
```

요청을 실행하기 위해선 Call 객체를 초기화하고 실행해야 한다.
```java
GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
Call<List<Contributor>> call = gitHubService.repoContributors(“square”, “retrofit”);
List<Contributor> result = call.execute().body();
```

비동기적 호출을 하기 위해선 enqueue를 사용해서 콜백을 생성해야 한다.
```java
call.enqueue(new Callback<List<Contributor>>() {
  @Override
  public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
    // handle success
  }

  @Override
  public void onFailure(Throwable t) {
    // handle failure
  }
});
```



## 컨버터
기본적으로 Retrofit은 HTTP 요청 본문을 OkHttp의 ResponseBody 형식과 @Body에 이용하는 RequestBody 타입만 역질렬화 할 수 있다. 그 이외의 형식들을 사용하기 위해선 컨버터로 변화해야 한다.
Retrofit은 Gson, Jasckson, Moshi, Protobuf, Wire, Simple XML등을 컨버터로 지원하며 사용자가 직접 정의할 수도 있다.




## 코틀린 기반의 안드로이드 Retrofit + OkHTTP
Retrofit과 OkHTTP를 사용하여 Github API를 통해 OAuth를 받아오는 예제를 짰다.
안드로이드는 자바가 아닌 코틀린으로 작성하였다.

Retrofit과 OkHttp를 사용하기 위해선 app 수준에서 build.gradle에 추가하여야 한다. Github API는 response를 JSON의 형태로 주기 때문에 GSON도 추가하였고 oauth는 브라우저를 통해 하기 위해 Chrome Custom Tabs도 추가하였다. 또 코드를 간소화하기 위해 dsl인 anko도 추가했다.

```xml
dependencies {
	implementation "org.jetbrains.anko:anko:0.10.5"
	implementation "com.android.support:customtabs:27.1.1"			implementation 'com.google.code.gson:gson:2.8.5'

	implementation 'com.squareup.okhttp3:okhttp:3.10.0'
	implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
	implementation 'com.squareup.retrofit2:retrofit:2.4.0'
	implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
}
```

요청 메소드는 인터페이스로 구현하고 어노테이션을 통해 요청들을 명시한다.
```java
interface AuthApi {
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String): Call<Auth>
}
```

getAccessToken의 타입은 Call<Auth>이므로 Auth 객체도 구현해야 한다.  이때 @SerializedName을 통해 직렬화시에 대한 이름도 명시하여 변수명에 따른 에러를 방지한다.
```java
 data classAuth(
        @field:SerializedName("access_token")
        val accessToken: String,
        @field:SerializedName("token_type")
        val tokenType: String) {
}
```

다음엔 Retrofit 클래스의 Builder()를 통해 AuthApi 인터페이스를 구현한다. baseUrl을 지정해주고 사용할 클라이언트도 지정한다. 그리고 Gson으로 변환할 것이므로  컨버터를 추가하고 마지막으로 구현할 인터페이스를 리플렉션을 통하여 지정한다.
```java
val authApi: AuthApi = Retrofit.Builder().apply{
	baseUrl("https://github.com/")
  client(httpClient)
  addConverterFactory(GsonConverterFactory.create())
}.build().create(AuthApi::class.java)
```

생성한 인터페이스로 Call을 받아오고 enqueue를 통해 비동기적으로 호출한다. 
enqueue를 좀 더 편리하고 직관적으로 이용하기 위해 익명 객체를 사용하기 보단 람다를 사용하기 위해 확장함수를 통해 확장했다. 코틀린의 편리함을 알 수 있는 대목이다.
```java
call.enqueue({
  it.body()?.let{
		toast(it.toString())   
	}, {
		toast(it.message.toString())
})

fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit, failure: (t: Throwable) -> Unit) {
  enqueue(object: Callback<T> {
     override fun onFailure(call: Call<T>, t: Throwable) = failure(t)
     override fun onResponse(call: Call<T>, response: Response<T>) = success(response)
    })
}
```



#android