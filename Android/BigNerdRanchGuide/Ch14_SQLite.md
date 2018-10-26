# Ch14_SQLite
안드로이드 장치의 각 애플리케이션은 자신의 샌드박스에 디렉터리를 갖는다. 그리고 샌드박스에 파일을 저장하면 다른 애플리케이션이나 다른 사용자의 접근을 막아준다.

**샌드박스 경로**
`/data/data/애플리케이션_패키지`

그러나 대부분의 애플리케이션 데이터는 평범한 파일로 저장되지 않는다.
 -> 하나만 수정해도 전체를 바꿔야 되기 때문 -> SQLite로 해결

## SQLite
MySQL이나 PostgreSQL처럼 오픈 소스 관계형 데이터베이스이다. 


## 스키마 정의하기
```kotlin
class CrimeDbSchema {
    companion object {
        class CrimeTable {
            companion object {
                const val NAME = "crimes";
            }
        }
        
        class Cols {
            companion object {
                const val UUID = "uuid"
                const val TITLE = "title"
                const val DATE = "date"
                const val SOLVED = "solved"
            }
        }
    }
}

```


## 데이터베이스 생성하기
안드로이드에선 SQLiteDatabase의 인스턴스로 열 수 있는 Context의 메서드, 즉 openOrCreateDatabase(…)와 databaseList()를 제공한다.


1. 데이터베이스 존재 확인
2. 없다면, 데이터베이스와 테이블을 생성하고 초기 데이터를 추가
3. 있다면, 데이터베이스를 열고 버전(스키마 버전)을 확인
4. 구버전이면 새로운 버전으로 업그레이드 하는 코드 실행


## SQLiteDatabase 열기

```kotlin
fun getDatabase(context: Context) : SQLiteDatabase? {
    return CrimeBaseHelper(context).writableDatabase
}
```

**getWritableDatabase() 를 호출하면 생기는일**
1. _data_data_어플리케이션_패키지_db 를 연다. 만약 db 가 없으면 새로운 데이터베이스 파일을 만든다.
2. 데이터베이스가 최초로 생성되는 경우에는 onCreate(SQLiteDatabase)를 호출하고 우리가 지정한 버전 번호를 저장한다.
3. 최초로 생성한 것이 아닌 경우에는 데이터베이스의 버전 번호를 확인한다. 그리고 CrimeOpenHelper의 생성자 인자로 전달한 버전 번호가 데이터베이스에 저장된 것보다 높으면 onUpgrade(SQLiteDatabase, int, int)

최초로 데이터베이스를 생성하는 코드 : onCreate(SQLiteDatabase)
데이터베이스를 업그레이드 하는 코드 : onUpgrade(SQLiteDatabase, int, int)

### ContentValues 사용하기
데이터베이스에 데이터를 추가하거나 갱신하는 것은 ContentsValues라는 클래스의 도움을 받아 수행된다.
키와 값의 쌍으로 구성되며 자바의 HashMap또는 안드로이드 Bundle과 유사하다.







#android/실무에바로적용하는안드로이드