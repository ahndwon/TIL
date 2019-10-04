Android Manifest

# Android Manifest
모든 애플리케이션에는 루트 디렉터리에 AndroidManifest.xml 파일이 있어야 한다.
매니페스트 파일 - 컴포넌트 명시
app -> manifest -> AndroidManifest.xml

매니페스트 수행 작업
- Java 패키지의 이름 지정
- 앱의 구성 요소 설명
- 앱의 구성 요소 호스팅하는 프로세스 결정
- 권한 선언
- Instrumentation 클래스 나열
- 앱이 필요로 하는 Android API의 최소 레벨 선언
- 앱에 연결 되어야 하는 라이브러리 나열


**메니페스트 태그**
- <?xml> 해당 파일이 xml이며 버전, 인코딩 방식

- <manifest> 해당 파일이 매니페스트 데이터이며 패키지 위치 등

- <user-permission> 앱 권한 설정

- <user-library> 공유 라이브러리 지정

- <user-sdk> 앱 플랫폼 버전, API 레벨, 지정 API 보다 낮은 경우 앱 동작 정지

- <user-feature> 하드웨어, 소프트웨어 기능 선언

- <user-permission> 표준 시스템 권한 설정

- <permission> 보안 권한 설정

- <permission-group> 보안 사용 권한 그룹 제공

- <permission-tree> 보안 권한을 이름 트리로 설정하여 해당 이름 예하 트리의 권한을 가짐

- <permission> 보안 권한 설정

- <application> 앱의 실질적인 구성, 내용

- <activity> 페이지 단위(java, xml)

- <intent-filter> 앱 페이지의 활동, 컴포넌트 기능 선언

- <action>인텐드 필너의 요소

- <category> 인텐더 필터의 이름

- <data> 인텐더 필터의 데이터 사양, 데이터 유형

- <grant-uri-permission> 부모 콘텐츠 공급자의 데이터 하위 집합에 대해 부여 할 수 있는 권한

- <instrumentation> 응용 프로그램과 시스템의 상호작용 모니터링

- <meta-data> 임의 데이터 항목 이름과 값의 묶음

- <provider> 콘텐츠 공급자 구성요소 선언, 시스템에서 인식

- <receiver> 다른 응용프로그램에서 브로드 캐스트되는 인텐트를 수신함

- <service> activity와 달리 시각적인 인터페이스가 아닌 클래스(백 그라운드용)

- <supprots-screens> 다양한 크기의 화면에 대한 UI 최적화 설정

#android