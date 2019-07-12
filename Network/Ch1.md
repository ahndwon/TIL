# Ch1
## The Network core
Mesh of interconnected routers
-> 그물처럼 상호연결된 라우터들

## 패킷 스위칭
원본을 쪼개서 패킷으로 전송

### store-and forward
패킷의 전체가 라우터에 도착해야 다음 링크에 전송이 가능하다.

One-hop numerical example
*  L = 7.5 Mbits : 전체 용량
* R = 1.5 Mbps : 
* **one-hop transmission delay** = 5 sec ( = L/R)
-> End - end delay = 2L/R (assuming zero propagation delay)

### Queueing delay, loss
* 링크의 transmission rate 보다 링크로의 arrival rate가 더 높을 경우 :
	* 	패킷들인 큐에 들어가게 되어 링크로 전송되기 위해 대기함
	* 메모리(버퍼)가 **가득찰 경우** 패킷들은 손실된다.
	-> 해결법 : arrival rate를 transmission rate에 맞춰줘야 된다. (버려지는 것을 막기 위해)

* **Forwarding** (포워딩) : 라우터의 인풋에서 적절한 라우터 아우풋으로 패킷을 이동하는 행위 -> 패킷을 보내는 행위
* **Routing** (라우팅) : 패킷의 전송 경로를 결정하는 것 -> 어떤 경로가 최선인지 판단하는 것

라우팅 알고리즘 : 라우터끼리 서로 소문을 내어 정보를 파악한 후 shortest path algorithm을 사용하여 데이터 전송


### Circuit Switching
* 엔드- 엔드 리소스들이 서로의 “call”을 위해 미리 할당됨
	* 옛날 전화
* 연결을 독점함 -> no sharing resources, dedicated resources
* 콜이 없으면 회로는 idle 상태


**하지만 패킷 스위칭이 만능은 아니다.**
### **패킷 스위칭 장점** 
* Bursty 한 데이터엔 효과적
	* 리소스 공유
	* 간헐적인 연결을 할 떄 적합
	
### 단점
* 연결이 보장되어야 할 경우 부적합
* 혼잡(congestion)이 발생 할 수 있다. -> 패킷 딜레이와 손실

**패킷 스위칭을 서킷 스위칭처럼 행동하게 하는 법**
* audio와 video app들의 대역폭(Bandwidth)를 보장
* 아직까지 해결되지 못한 부분


## 패킷 손실, 딜레이
Delay : packet being transmitted , queueing
Loss : free buffers

![](Ch1/79FD72CD-A610-412D-A123-9848DE4507E9.png)

![](Ch1/7014D559-F68F-4AFA-8AB9-B4CAA553E934.png)


* Throughput : 실제로 보내진 전송 속도
* Bandwidth : 최대 보낼 수 있는 전송 속



![](Ch1/CFAFE09E-7C23-448B-A53F-5B6EE497E0F2.png)


패킷 스니핑(sniffing) - 패킷을 훔쳐봄

IP snooping : 패킷을 다른 목적지로 보냄






#네트워크