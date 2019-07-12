# Ch2_Web, HTTP
* WWW - World Wide Web
* Web page = HTML file + referenced objects

URL - Uniform Resource Locator

http://www.someschool.edu:8080/someDept/pic.gif

**Protocol**://**hostname**:**port number**/**pathname**


## HTTP
* TCP 사용
*  상태 X (**stateless**)
-> 상태를 저장하는건 복잡## HTTP connections
* non-persistent HTTP
	* at most one object sent over TCP connection
	* 여러 오브젝트 다운로드는 여러 커넥션 필요

* Persistent HTTP
	* 여러 오브젝트가 하나의 TCP 커넥션을 통해 전송 가능

* RTT - Round Trip Time
	* 패킷 왕복 시간
	* 패킷이 클라이언트에서 서버로 전송되고 돌아올때까지의 시간

* Http response time
	* One RTT to initiate TCP connection
	* One RTT for HTTP request and first few bytes of HTTP response  to return
	* File transmission time

	* Non-persistent HTTP response time = 2RTT + file transmission time

## Persistent HTTP
![](Ch2_Web,%20HTTP/DA22B152-C654-4F7E-8023-7FEC87164F73.png)

* Non-persistent HTTP 문제
	* 오브젝트 당 2RTT가 필요하다
	* 각 TCP 연결의 OS overhead
	* 레퍼런스 객체들 떄문에 브라우저가 간혹 병렬 TCP 연결을 한다.

* Persistent http
	* response를 보내고 서버는 연결을 열어놓는다.
	* object 당 RTT 하나
	* 레퍼런스 오브젝트를 마주치는 순간 request를 전송


HTTP Message 종류
Request
Response

## 캐싱

![](Ch2_Web,%20HTTP/1A3EF875-44FF-4543-BE9F-D1B5651E180E.png)
1. Object size : 1 Mbits
2. Origin servers : 15 / sec
3. Data rate to browsers : 15 Mbps
4. RTT : 2 sec
5. Access link rate : 100 Mbps


Total delay = internet delay + access delay + LAN delay
			= 2 sec + minutes + mess


![](Ch2_Web,%20HTTP/97EDBB33-B6D6-420D-A40E-5046EBA9EE68.png)

*  캐싱을 할 경우 총 delay : (오리진 서버로 요청한 비율 * 서버 딜레이) + (캐시로 만족된 요청의 비율  * 캐시 딜레이)






#네트워크