# Ch4_network_data
## Two Key network-layer functions
* **Forwarding**
	* 라우터에서 다른 라우터로 패킷을 전송하는 것
	* move packets from router’s input to appropriate router output 

* **Routing**
	* 패킷을 어떤 경로로 전송할지 결정하는 것
	* determine route taken by packets from source to destination 
	* Routing algorithms (protocols) – RIP, OSPF, IS-IS, BGP, … 


![](Ch4_network_data/56E2A3AC-1914-44C6-9EB3-8E344DD68A6C.png)


## Data plane, control plane
**Data plane** : 라우터 인풋으로 들어온 데이터그램이 어떤 라우터 output port로 갈지 결정함

**Control plane** :   라우터들 사이에서 source host에서 destination host 로 어떤 경로로 보낼지 판단함
	* 	traditional routing algorithms : router에 구현됨
	* software-defined networking (SDN) : (remote) server들에 구현됨


## Per-router control plane
![](Ch4_network_data/4D2B0135-2B11-4635-838F-44CEAE48F994.png)



## Network layer service models
![](Ch4_network_data/E202EDEF-E1CA-4CB1-BD94-9A3B9BFF7F75.png)


ATM - Asynchronous Transfer Mode : 신뢰성 있는 배달을 위해 생긴 프로토콜
하지만 현재는 Internet이 단순해서 널리 쓰임



## Routing Architecture
![](Ch4_network_data/AF15C5B9-914F-49FB-8A12-C9C8563C30BE.png)


## Longest Prefix Matching
![](Ch4_network_data/2F000C3A-4CD2-4174-96AA-C944B4D7C41C.png)



## Packet Scheduler
![](Ch4_network_data/BF4AE070-A093-4398-848F-E5BF90BD93B2.png)


## Selection Strategies
![](Ch4_network_data/B72A92FA-E1FF-4456-B266-C1B1201843C6.png)
* WFQ 단점 : 중요도가 낮은 것은 계속 무시됨

## Drop Strategies
![](Ch4_network_data/11AEFCBE-D00B-4BD7-B474-74A1C2CD6FF2.png)
* Active queue management - TCP 헤더에 적용됨, 경고를 줌

NAT - Network Address Translator


## The Internet Network Layer
![](Ch4_network_data/79A4A6DF-0AFA-48EC-A2CB-B460679FFD9E.png)

## IP Datagram format
![](Ch4_network_data/CB81AB97-B2E3-416A-B13B-3CD149FFD928.png)

![](Ch4_network_data/808BEB34-D826-4B47-A750-204A210395E6.png)
## IP fragmentation, reassembly


MTU(Maximum transmission UNIT) 는 IP header 포함
![](Ch4_network_data/D477BAC4-A3CC-44D0-89AB-C957E2508253.png)


## Fragmentation Example
![](Ch4_network_data/CFAE49AC-1E37-4C37-9BF7-5FA6CE79D6B4.png)

![](Ch4_network_data/AB72F958-5E75-4C69-B90B-D677FA86F8BC.png)


![](Ch4_network_data/4FE42B98-76ED-42DD-AE40-CE66C1D194B0.png)

가정 : 하나의 TCP segment는 IP datagram에 들어간다


Fragment는 TCP 헤더 상관하지 않는다.

![](Ch4_network_data/6A5A2BF7-0827-4E31-9E97-B2C4591DCD2B.png)

![](Ch4_network_data/CFB1DF9F-5C5F-4FE1-96FE-02D54D2FD9F4.png)


**CIDR (Classless InterDomain Routing)**
![](Ch4_network_data/FD0DEFCA-62EC-4DD2-A7E9-385E5B203D06.png)


DHCP (Dynamic Host Configuration Protocol)
	- 서버로부터 주소를 동적으로 할당 받음
	* 서버 한테서 주소를 할당 받아 일정시간 동안 빌려씀 (lifetime )

![](Ch4_network_data/37E377C2-90C5-4542-B42A-39B1C86ACEA3.png)

![](Ch4_network_data/BDA9717A-7939-484C-B3A7-6B0547193C17.png)



## Subnetting
Subnet : 컴퓨터 그룹
Subnetting : IP그룹을 더 작은 하위 그룹으로 쪼개는 것

![](Ch4_network_data/D33C5DAE-38DF-40E6-8361-C657D50CBB8F.png)


![](Ch4_network_data/E4E9204B-FD1A-4563-A332-DA89492C504C.png)


![](Ch4_network_data/536BAE93-5932-4251-94C7-C2F13FD78D30.png)


자라는건 **subnetting**
합치는건 **supernetting**
![](Ch4_network_data/F81BC2A2-9C76-42B2-8E7D-806DA71F485D.png)


## NAT traversal problem
![](Ch4_network_data/CA847F63-7F5C-4967-A37E-75184BB91630.png)

![](Ch4_network_data/BFE8D268-50F8-4598-A01F-581E2D960738.png)
![](Ch4_network_data/6A5A3C38-D12C-467A-AE6D-659A818B5C89.png)

ICMP: internet control message protocol


## IPv6 생긴 이유	
IPv4로는 주소가 부족함
![](Ch4_network_data/BE2594FC-EF11-4FB8-91E6-54600949B8A8.png)

## IPv6 - Addressing Model
![](Ch4_network_data/EE6792CC-9F3F-4D16-B520-8D39C3084E29.png)


## IPv4, IPv6 헤더 비교
![](Ch4_network_data/9A19ADBF-17AD-4C86-85D3-82B9E89FCDB3.png)

Header Length가 사라짐 -> 헤더 40바이트로 고정
Header Checksum 사라짐 (라우터가 하는 일을 줄여주기 위해)

![](Ch4_network_data/A57146C8-7D10-415D-8A39-6D41756232EB.png)


![](Ch4_network_data/D51940C1-3611-4A4F-BE84-14D818D8DF37.png)

1500 = 40 basic header + 1460
1000 = 40 + 8 (재결합을 위한 extension ) + 952
1460  = 952 + 508
40 + 8 + 508 = 556

Path MTU = 경로 중 MTU 최소값
Header : 40 byte


헤더가 만들어질때 IPv4와 IPv6는 다르다 -> 차이점 알고 잇어야함

## Tunneling
![](Ch4_network_data/76EC2E10-9B35-4FDD-AAE6-7D37AA5C649C.png)
![](Ch4_network_data/6EC4E86C-B37D-4855-BF71-859EC9618F93.png)













#네트워크