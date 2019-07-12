# Ch3_Principles
## Transport Services and Protocols
* 다른 호스트에서 실행중인 어플리케이션 프로세스들의 논리적 상호 통신
* **Sender** : 앱 메시지들을 조각들로 나누어서 네트워크 계층으로 전달함
* **Receiver** : 조각들을 메시지로 조립하고 앱 계층으로 전달함
* Internet : TCP, UDP, SCTP, DCCP


**TCP (Transmission Control Protocol)**
신뢰할수 있는, 순차적 전달
* Congestion Control - 네트워크 계층에서 일어나지만 앱 계층에서 보내는 속도를 조절해야 해결의 되므로 해결이 힘들다. -> 서로 다른 계층에서 연관되어 일어나는 문제이므로 해결이 어려움
* Flow Control
* Connection Setup

**UDP (User Datagram Protocol)**
신뢰 불가, 비순차적 전달

**사용불가능한 서비스**
: Delay Guarantees, Bandwidth guarantees



## Reliable Data Transfer
![](Ch3_Principles/1EF8072D-0033-4319-BAD6-34A4735348E0.png)



![](Ch3_Principles/16898693-AD1F-44EB-B3CE-52768B8ED124.png)

![](Ch3_Principles/FC0CC5DE-9DD2-40B0-A2B8-6FF8F1B9A59F.png)


![](Ch3_Principles/1D00A061-2FB2-40E9-8E9B-0A25EDD5762E.png)

![](Ch3_Principles/472985DD-7CF7-4D7C-8999-1860785D2F34.png)

![](Ch3_Principles/B1761C1B-B087-40D9-BA95-014A9B37112E.png)

![](Ch3_Principles/D5C78CD1-089C-40CD-BBFF-453360659836.png)

![](Ch3_Principles/52D7035B-7E72-43AE-90CF-211DAABF3F02.png)
**Receiver**
* pkt2를 받지 못해 계속 ack1을 보내고 pkt2 이후의 패킷들은 버린다.

**Sender**
* pkt2를 보내고 타이머 가동
* ack1이 중복되어 수신되도 무시함
* pkt2 타이머의 타임아웃 발생 -> pkt2 부터 **윈도우 사이즈**만큼 패킷들 재전송



![](Ch3_Principles/A8FB2CAB-2EB3-4362-A935-DB663FA1B4D7.png)
**Receiver**
* ack0, ack1 전송됨
* ack2와 ack3를 보냈지만 손실됨 
* pkt4, pkt5를 수신
* ack4, ack5 전송 하지만 ack4 손실

**Sender**
* pkt0~pkt3 전송
* ack0, ack1 수신
* pkt4~pkt5 전송
* ack2의 타이머가


![](Ch3_Principles/5E3FB6CE-CC00-48B6-9606-2E253CCC2D01.png)


## Congestion Control

![](Ch3_Principles/89FBE1C6-6E86-4E9D-BD10-BFDA7E04B819.png)













#네트워크