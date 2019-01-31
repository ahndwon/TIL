# Ch3_Data_Type_Basic
스위프트의 기본 데이터 타입은 모두 구조체를 기반으로 구현되어 있다.


## 3.1 Int와 UInt
정수 타입.
**Int** :  +, - 부호를 포함한 정수를 뜻함.
**UInt** : 이 중 - 부호를 포함하지 않는 0을 포함한 양의 정수

각각 8비트, 16비트, 32비트, 64비트
Int8, Int16, Int32, Int64, Uint8, Uint16, UInt32, UInt64

32비트 아키텍처 : Int32 -> Int, UInt32 -> UInt
64비트 아키텍처 : Int64 -> Int, UInt64 -> UInt

진수별 정수 표현
```swift
let decimalInteger: Int = 28
let binaryInteger: Int = 0b11100		// 2진수로 10진수 28을 표현
let octalIntger: Int = 0o34			// 8진수로 10진수 28을 표현
let hexadecimalIntger: Int = 0x1C		// 16진수로 10진수 28을 표현
```


## 3.2 Bool
True , false
```
let iLoveYou: Bool = true
```


## 3.3 Float과 Double
부동소수점을 사용하는 실수
부동소수 타입

Double - 64비트 부동소수 표현
Float - 32비트 부동소수 표현

64비트 환경에서 Double 최대 15자리 십진수, Float 최대 6자리


## 3.4 Character
Character - 문자
스위프트는 유니코드 9 문자 사용
변수명으로 이모티콘과 한글도 사용 가능


## 3.5 String
String - 문자열
유니코드9
값의 앞뒤에 큰따옴포 사용하여 표현
`let name: String = "yagom"`


## 3.5.1 특수문자
특수문자는 백슬래시에 특정한 문자를 조합하여 사용

**가장 많이 사용하는 특수문자**
* \n - 줄바꿈 문자
* \\ - 문자열 내에서 백슬래시 표현할때 사용
* \” - 문자열 내에서 큰따옴표 표현할 때 사용
* \t - 탭 문자. 키보드의 탭키를 눌렀을 때와 같은 효과
* \0 - 문자열이 끝났음을 알리는 null 문자

## 3.6 Any, AnyObject와 nil
* **Any** - 스위프트의 모든 데이터 타입을 사용할 수 있다는 뜻
* **AnyObject** - Any보다 한정된 의미로 클래스의 인스턴스만 할당 가능 하다는 뜻
* **nil** - ‘없음’을 나타내는 스위프트의 키워드(자바의 null)












#Swift