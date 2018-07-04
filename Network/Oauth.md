# Oauth
인터넷 사용자들이 비밀번호를 제공하지 않고 다른 웹사이트 상의 자신들의 정보에 대해 웹사이트나 애플리케이션의 접근 권한을 부여할 수 있는 공통적인 수단으로서 사용된, 접근 위임을 위한 개방형 표준이다.
이 매커니즘은 표준으로서 구글, 페이스북, 트위터 등 많은 기업들이 사용한다.


## 인증 방식
OAuth 인증은 소비자와 서비스 제공자 사이에서 일어난다.
	1. 소비자가 서비스 제공자에게 요청토큰을 요청한다.
	2. 서비스 제공자가 소비자에게 요청토큰을 발급해준다.
	3. 소비자가 사용자를 서비스 제공자로 이동시킨다. 여기서 사용자 인증이 수행된다
	4. 서비스 제공자가 사용자를 소비자로 이동시킨다.
	5. 소비자가 접근토큰을 요청한다.
	6. 서비스 제공자가 접근토큰을 발급한다.
	7. 발급된 접근토큰을 이용하여 소비자에서 사용자 정보에 접근한다.


**OpenID 인증과 OAuth의 차이점**
OAuth는 인증 프로토콜이 아닌, 인가 프로토콜이다.
![](Oauth/F98B2BDE-C3E4-423B-87D4-CC50D640ADFF.png)
[OAuth](https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/OpenIDvs.Pseudo-AuthenticationusingOAuth.svg/512px-OpenIDvs.Pseudo-AuthenticationusingOAuth.svg.png)


#네트워크
