# Ch2_DNS
## Domain name rule
* LDH - Letter (alphabet), Digit (0-9), Hyphen(-)
* Labels may not start or end with a hyphen
* max Length of label : 63 characters
* max Length of fully qualified domain name : 253 characters

## DNS services
	* Hostname to IP address translation
	* Host aliasing
		* Canonical, alias names
	* Mail server aliasing
	* Load distribution - 부하를 나눠줌
		* Replicated Web Servers : many IP addresses correspond to one name


**DNS를 중앙화(centralize) 하지 않는 이유**
*  문제가 생길 경우 치명적 (single point of failure)
* Traffic Volume
* 유지 보수


## Local DNS name server
* 각 ISP들이 갖고 있다
	* default name server라고도 함
* 최근 이름-to-주소 에 대한 정보를 로컬로 캐쉬한다.


## Iterative query
* 해당하는 정보가 없더라도 어떤 서버가 알고 있을지 알려줌
-> 연락된 서버가 연락할 서버의 이름을 알려줌


## Recursive Query
Root server 가 책임지고 찾아서 TTL(Time to Live) : 캐쉬의 지속 기한을 정해서 데이터를 보장

## DNS records
![](Ch2_DNS/4C6EC646-884E-4C9A-A7F6-34B911444E78.png)


#네트워크