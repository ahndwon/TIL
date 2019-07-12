# Ch2_P2P
## Napster
1. Peer 연결 -> central server에 알림
	* IP 주소
	* 컨텐츠

![](Ch2_P2P/77649C08-5056-474E-AD94-2A44C3FD3ED5.png)


## Gnutella
이미 존재하는 TCP 연결을 통해 쿼리 메시지가 보내짐
Peer 들이 쿼리 메시지를 포워드한다.
Reverse path를 통해 Query Hit이 보내진다
파일 전송 : HTTP
![](Ch2_P2P/D2A6CE59-7619-4E59-B5B9-52EDA887BD36.png)


## KaZaA
* 각 peer는 group leader 이거나 group leader 한테 assigned 되어 있다.
	* peer와 그룹 리더 사이의 TCP 연결
	* 몇몇 peer와 그룹 리더 사이의 TCP 연견들
* 그룹 리더는 자식들의 컨텐트를 전부 트래킹한다.



## File Distribution time : client - server
![](Ch2_P2P/FA1FD3E3-990F-4373-8B0A-9E913A8D60BF.png)


## File Distribution time : P2P
![](Ch2_P2P/DD3A6864-E3FE-4AD0-87DB-C84B670A9833.png)


## BitTorrent
![](Ch2_P2P/828B4F47-98E8-4DF4-B394-04D562BAFAA6.png)


## Distributed Hash Table (DHT)
* DHT - 분산된 P2P 데이터베이스
* 데이터베이스는 (key, value) 페어 들을 갖는다
	* key : 소셜 보안 넘버, value : 사람 이름
	* key : 영화 제목 , value : IP 주소
* Peer는 키를 통해 DHT를 쿼리한다.
* Peer는 (Key, Value) pair를 삽입 할 수 있다.
* Rule: assign key to the peer that has the closest ID
 e.g., n =4; peers: 1,3,4,5,8,10,12,14;
 key = 13, then successor peer = 14 
 key = 15, then successor peer = 1 

**To get integer key, hash original key** 
Original Key를 해쉬 펑션으로 돌리는 이유
-> Original Key는 문자열이기 때문에 길이가 가변적이므로 -> 고정시키고 숫자로 변경


## Circular DHT
![](Ch2_P2P/87823561-F078-454E-B52D-946C99DFA289.png)




#네트워크