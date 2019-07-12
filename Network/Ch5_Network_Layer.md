# Ch5_Network_Layer
## Routing Protocols
![](Ch5_Network_Layer/CC7AF464-6553-4A1B-B20E-2EA061F2259D.png)

![](Ch5_Network_Layer/A208E4C0-CCB7-48A7-BF05-5222DCEF5B19.png)


## Routing algorithm classification (1)
![](Ch5_Network_Layer/B36726A3-C1DE-4DDF-BD47-CB628C010B84.png)

2019.5.21) Network Layer - IPv6, distance vector routing
## Bellman Ford 
![](Ch5_Network_Layer/FCA79494-44C9-4635-AE0F-8AABA3456CB5.png)

#### Bellman-Ford example
![](Ch5_Network_Layer/1790E0F2-B0C8-4A01-8AC1-A37ED6451363.png)


**라우팅** :  비용이 제일 적은 최선의 경로를 찾는 작업


## Distance Vector Algorithm

![](Ch5_Network_Layer/F7164464-2E6A-422C-A37C-FDCBF3C07D71.png)



## Distance Vector Algorithm Example
![](Ch5_Network_Layer/997DCBDD-AC1F-4930-9124-481FB2685AD7.png)

## Distance Vector : Link cost changes (1)
* Node각 로컬 링크 코스트가 바뀌는걸 탐지함
* routing 정보 업데이트하고, distance vector 재계산한다
* 만약 DistanceVector가 바뀌면, 아웃들에게 알려준다.

라우터들끼리 주고받으며 소문냄
 

## Distance Vector : Link cost changes (2)
![](Ch5_Network_Layer/0AA9230C-4169-4CC1-BB44-6A8A9A89354B.png)


## Solution
* Split horizon
	* 만약 X의 이웃을 통과해야하는 경우 X의 이웃에게 경로를 소문내지 않음
	* C가 B를 통해 A로 갈 경우, C는 B에게 알리지 않는ㄴ다,
	
* Poisoned reverse
	*  만약 X의 이웃을 통과해야하는 경우 X의 이웃에게 무한한 경로를 알려줌
	* C가 B를 통해 A로 갈 경우, C는 B에게 C에서 A까지의 거리가 무한대라고 알려줌

## Split-Horizon with Poisoned Reverse
![](Ch5_Network_Layer/1FF09C0C-9A00-458F-B299-5B5DF59CBA8E.png)



## Inter connected Ases
AS :(**Autonomic System)**
![](Ch5_Network_Layer/9A97D46F-9542-4710-A8E5-3EC91746ADBF.png)


## OSPF - Open Shortest Path First
* “Open” : 공개적으로 가능한
* link state algorithm 사용
	* LS 패킷 보급
	* Topology map at each node 
	* Route computation using Dijkstra’s algorithm


## Border Gateway Protocol (BGP)
![](Ch5_Network_Layer/A653A924-115E-4877-BC3E-269E6868B694.png)
* eBGP : 외부
* iBGP : 내부


## BGP Messages
![](Ch5_Network_Layer/F87415C5-5DC4-454C-868E-4925A911AA30.png)


## BGP, OSPF, forwarding table entries
![](Ch5_Network_Layer/BF6CFFFC-F334-467E-B81D-3E3574A03E3E.png)



## BGP routing policy
![](Ch5_Network_Layer/C3B90C65-EEAE-4027-9E45-52C034625863.png)

![](Ch5_Network_Layer/098CFB9F-C017-45DE-AD13-1D53CF3FE47D.png)



## Why different Intra-, Inter-AS routing
Software Defined Networking
















#네트워크