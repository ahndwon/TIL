Android Navigation

# Android Navigation

[Naver Engineering](https://tv.naver.com/v/9910794)



![94c708a08281100a4960c45ed8ed64c8.png](../_resources/12b84deb48c24b6a8e2b8a1b773dbce5.png)




![268e4590aa64555b4c88e7e1812448ff.png](../_resources/dc5d6245617a447fbae735906e0c627b.png)



![e4a378534b405a474abb3891e0e3471c.png](../_resources/ec01b7feb6a640eb80824df79fff1c02.png)



![f1e11d8828623e4bcf7dcf294f9f6e1e.png](../_resources/e0a480b129ed48bb9d1c58969a3730d6.png)



## 안되는것

1. Fragment + ViewModel
    LifeCycleOwner를 조심해야한다.
2. RecyclerView Leak
    Fragment에서 Adapter를 참조할 때, Leak 발생 가능
    - Fragment는 View만 Destroy될 수 있다.
    - Destroyed RecyclerView가 Adapter 를 참조하고 있으면 Leak이 발생한다.
    - 대안 1 : onCreateView(), onViewCreated()에서만 adapter를 참조한다.
    
![1e7bca03984308f6f3ee59ba738d5aae.png](../_resources/2dd558f0c96748c0a21886b4493287df.png)

    - 대안 2 : onDestroyView()에서 adapter를 null로 참조를 해제한다.

![d6a928a439ca7e0ba69f8cb857a6ef04.png](../_resources/8b2e3ad970bb4d33aa45fb258afd964d.png)


3. SystemUiVisibility
    

![6fa4a5c21071c6e139c059082c0a4ff4.png](../_resources/a143404c467c4fba9bca7942789e283d.png)


## AndroidX Navigation의 아쉬운 점

### 1. Action 중복

![9e01ae19049b085bc0f765fed10d71c0.png](../_resources/adf740b4811e485c82584ddb0c69a5c0.png)


![220f1d28ca8e484b71327a48684e644b.png](../_resources/7a76f3e9210546b68f880e4215cd0a70.png)


### 2. Handling Back Key



![59920a98598bf50884c42dffb2b00bd5.png](../_resources/882410154dfd4ed09c0e63ffab056bd9.png)



![31a66d6e43305bef8532b0b1dfb49ca2.png](../_resources/eec8742f93f043e78c4c4f6e74e9839c.png)



![90cb73c44afa6953140d22d23418e3c5.png](../_resources/734d4daf43dc44d2bf7bb0f2ff7bd51d.png)



![f24053dfa5fa6ef97dc6a93df36035c1.png](../_resources/fd30cef52fce43d69cdb85be02504dda.png)



### 3. No Activity Result


![27b7d75fb1452c58f8ab31ab73fb6e89.png](../_resources/8873e011f7a44f85a5cd3890b69e71e8.png)


![e4f2aaf087dacbe80fdea3c5c537b9eb.png](../_resources/ffb53d9832644f2db64fb84de01fbec9.png)


![074a8147ed11d41a8c89578df2b29e3d.png](../_resources/b204975145ff42b9a8767be253d96c8b.png)


![f9e96eb3e4c79d4808d3ddcd4e9c473f.png](../_resources/591c8726f67646cdaa4471a0be0606c7.png)


![a5f1d8288d4089015e3b9b0c80298c33.png](../_resources/d0d6d666584c4a6bbe47cb5807eb26f6.png)


![b83f7619417ff3be146837ecc037f5b1.png](../_resources/c4d056eebe3e42b7afecb08db10b667b.png)


![0030784f8d8a937efa148096c11cc44a.png](../_resources/466035bad9c1440fa0c2b3fc34e96fa2.png)


### 4. No Multiple BackStack


![98e002deab92f479f3adb899334de695.png](../_resources/d9fadeb7cffe4902afba06ce4b1bda13.png)



## 요약
- ViewModel을 사용할 때, Fragment의 LifeCycleOwner를 주의하세요.
- RecyclerView에서 Memory Leak를 확인하세요
- 하나의 Window를 공유하여 제어하는 방법을 고민해야 합니다.
- Local Action을 다른 화면에서 호출하면 Exception이 발생됩니다.
- Stable 버전에는 Custom Back 기능이 제공되지 않습니다.
- Result 기능도 제공되지 않습니다. (ex. ActivityResult)
- Multiple BackStack 기능이 필요하면, 공식 Sample을 참고하세요.





