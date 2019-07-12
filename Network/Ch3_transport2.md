# Ch3_transport2
TCP - 인터넷 전체의 80~90%


## UDP - User Datagram Protocol
	* **“No frills,”, “bare bones” internet transport protocol**
	* **“Best effort” service, UDP segments may be:**
		* 손실
		* 순서가 잘못된 상태로 전송
	* **ConnectionLess**
		* UDP 발신자와 수신자 사이에 Handshaking이 없음
		* 각각의 UDP segment는 독립적으로 처리됨
	* **UDP use** 
		* 멀티미디어 앱 스트리밍 (손실 용인, 전송률에 예민)
		* DNS
		* SNMP (Simple Network Management Protocol)

## UDP - segment header
![](Ch3_transport2/B2427BDA-45D5-4460-A1C6-6072F11766BF.png)

* Source port
* dest port
* length
* checksum
* application data (payload)

**왜 UDP를 쓰는지**
	* 	connection 형성이 없다 (이로 인한 딜레이가 없음)
	* 간단함 -> 수신자와 발신자 사이의 연결 상태가 없다
	* 작은 헤더 사이즈
	* congestion control이 없음 : UDP는 원하는만큼 빨라질 수 있음


## Appropriate use of UDP
* Inward data collection
	* **주기적인(Periodic)** active or passive sampling of data
	* **자동(Self)** self - test reports
* Outward data dissemination
	* **Broadcast** to network users
	* announcement of new node
* Request-response
* Real-time applications
	* Voice and telemetry
	* Applications involving a degree of redundancy
	* Real-time transmission requirements


## Quic protocol stack
* TCP와 UDP의 장점을 섞은 프로토콜 ( 구글에서 만듬)
* Quick UDP Internet Connections


## TCP Overview
* Full duplex data, (단방향은 simplex라고 함)
	* 양방향 데이터 플로우 , 같은 연결상에서
	* MSS : maximum segment size
* Connetion-oriented
	* 데이터 전송 전 handshaking 을 통해 수신자와 발신자의 상태를 초기화
* Flow controlled
	* 발신자는 절대로 수신자의 허용치를 넘어서지 않는다
* Point-to-point
	* 한 연결, 한 발신자, 한 수신자
* Reliable, in-order byte stream
	* 메시지들 사이에 경계가 없음 (맥락 없이 잘림)
* Pipelined : 
	* TCP congestion and flow control set window size
	* 받았든 안 받았든 데이터 일단 보냄


### * Reliable, in-order byte stream
* application 는 바이트 를 씀
* TCP는 segment를 전송함
* 어플리케이션은 바이트를 읽음
![](Ch3_transport2/97323B75-B128-4BC6-A04D-77AD05C547D2.png)
* 무조건 순서대로 보냄
* PUSH 플래그인 데이터는 무조건 어플리케이션한테 전달

## TCP segment structure
![](Ch3_transport2/E016B928-7861-4EE5-9662-04C2A796AA43.png)

![](Ch3_transport2/C9E7DB00-C18B-49BE-94B5-70FD0DE82025.png)

* **sequence numbers** : 데이터의 첫번쨰 바이트의 byte stream “number”
* **acknowlegement number** :  B가 A에게 보낸 무언가에 대한 수신자의 피드백
	* Seq # of new byte **expected** from other side
	* Cumulative ACK
	

### Flags
	* URG : Urgent data, 주로 잘 안 쓰임
	* ACK : ACK # valid, ACK 넘버가 유효한 값임을 알려주는 역할
	* PSH : push data now, 주로 안 쓰임
	* RST : 연결 재형성 요청
	* SYN : SYN임을 알려주는 역할
	* FIN :  연결 끊음을 알려주는 플래그

### Urg data pointer
	* urgent data가 존재할 시 어딨는지 가리키는 포인터
	
### Checksum
데이터가 훼손 되었는지 확인 할 수 있는 체크섬

### Receive window
수신자가 받고자 하는 바이트의 크기

![](Ch3_transport2/72DD6691-D90F-4D82-9CE0-35A6E6C26A4F.png)


## TCP round trip time, timeout
### TCP timeout 값을 정하는 기준
	* Longer than RTT
		* 하지만 RTT는 항상 다르다.
	* Too short : premature timeout, unnecessary retransmissions
	* Too long : slow reaction to segment loss

### How to estimate RTT?
	* 	sampleRTT: measured time from segment transmission until ACK receipts
		* Ignore retransmissions

	* Sample RTT will vary, want estimated RTT “smoother”
		* Average several recent measurements, not just current SampleRTT

## TCP Retransmission Scenario
![](Ch3_transport2/F0194C7C-748A-4122-8C4D-7ECE62681DF3.png)

## TCP reliable data transfer
![](Ch3_transport2/4176F136-DE73-4FBD-8D29-BD5C1F21F727.png)


## TCP sender events
* Data received from app
	* create segment with seq #
	* seq # is byte-stream number of first data byte in segment
	* Start timer if not already running
		* Think of timer as for oldest unasked segment
		* Expiration interval : TimeOutInterval

* Timeout
	* retransmit segment that caused timeout
	* restart timer

* Ack received
	* if ACK acknowledges previously unpacked segments
		* update what is know to be acked
		* start timer if there are still unacked segments

## TCP fast retransmit
발신자가 같은 데이터에 대해 3 ACKS를 받으면 -> triple duplicate ACKs
Resent unacked segment with smallest seq number
	* 	Likely that unacked segment lost, 그래서 타임 아웃 기다리지 않음


## TCP fast retransmit
![](Ch3_transport2/5274353F-9E0B-462F-950E-1D0509DA0652.png)
연결이 끊겼음을 연속된 같은 값에 대한 ACK을 통해 깨달아 Timer timeout  되기 전에 재전송


## TCP Flow Control
![](Ch3_transport2/8A94FB57-BC8C-4B3A-AF7A-8C7FC3ACBE39.png)

수신자가 발신자를 조절함
-> 너무 많은, 빠른 전송으로 인해 수신자의 버퍼가 Overflow 되지 않도록

![](Ch3_transport2/7CA0761B-1359-46A5-9B35-E9035292732E.png)
리시버의 free buffer -> rwnd value를 통해 알 수 있다.

## 2-way handshake
![](Ch3_transport2/F5A923DB-6394-4455-8F82-E6AE7FA18B80.png)

## Agreeing to establish a connection

![](Ch3_transport2/EBAB0AD8-11B8-4CCF-B403-6157D9ACCD2E.png)


## 3-way handshake
![](Ch3_transport2/1B2644B8-0540-4141-BAC5-F24330784261.png)

* SYN Flooding Attack 에 취약함
다수의 클라이언트가 접속 시도하고 연결을 종료하면 서버는 알 수 없어 생기는문제
간단하지만 막기 어려운 공격


![](Ch3_transport2/C0B6922B-5A15-4A15-B429-2155C9F29369.png)

## TCP : Closing a Connection
* 클라이언트, 서버 모두 연결 종료
	* FIN bit = 1 -> send TCP segment
* respond to received FIN with ACK
	* FIN을 받으면 ACK이 자체의 FIN과 합체 가능
* 연속된 FIN 처리 가능

![](Ch3_transport2/D34067C2-6B2E-4731-A7AF-91F7DCDEA127.png)


![](Ch3_transport2/7E8846AE-804C-4E65-9FB0-C993A1896DA1.png)


## AIMD , Additive increase multiplicative decrease
* **Approach** : sender increases transmission rate (window size), 손실이 발생할 때 까지 증가시킴
* **Additive increase** : cwnd를 RTT 마다 1 MSS 만큼 손실이 일어날때까지 증가시킴
* **Multiplicative decrease** : 손실이 발생하면 cwnd 를 반으로 자른다.


![](Ch3_transport2/2E1BDE2D-5C9B-4DEF-A535-5F9DE3010BFA.png)

Cwnd : Congestion Window
## TCP Congestion Control 
![](Ch3_transport2/E33691BE-B06E-4D43-A225-8BB79B341A26.png)


## TCP Slow Start
연결이 형성되면 손실이 일어날때까지 기하급수적으로 속도 증가시킴
-> RTT마다 cwnd를 2배씩 증가 (ACK이 와야 증가시킴)


## Congestion Avoidance
* Cwnd >= ssthresh 일 때 시함
* 각 성공적 ACK:
	* cwnd <- cwnd + mss * mss / cwnd
* Linear growth of cwnd
	* 각 RTT : cwnd <- cwnd + MSS. (1씩 증가)

 Segment size 는 윈도우 사이즈와 같다.


## TCP : detecting, reacting to loss
* Loss indicated by timeout : 
	* 3 duplicate ACKS 떄보다 더 심각한 손실 -> 일시적인 문제가 아니다
	* cwnd set to 1 MSS;
	* 윈도우는 기하급수적으로 증가하고 threshold까지 증가하면 값은 linear하게 증가
	

* 3 duplicate ACKs 로 인한 손실 -> four identical ACKS
	* Dup Acks는 네트워크가 몇 세그먼트를 보낼 수 있음을 나타냄
	* cwnd는 반으로 줄이고 윈도우는 그 후 linear하게 증가

* TCP Tahoe는 반드시 cwnd를 1로 맞춘다.

![](Ch3_transport2/C928999D-625B-44C0-B0EB-C96C96C1ABFF.png)


## TCP throughput
![](Ch3_transport2/6B215C1F-B84D-4235-89CF-EA671E9BD54B.png)



![](Ch3_transport2/42693671-27F8-437E-A923-7AF04F3BF3C1.png)
* 손실이 일어나면 throughput이 감소하므로 손실율이 적어야 10Gbps가 나온다.
-> 새로운 TCP 필요


## Why is TCP fair?
![](Ch3_transport2/02EDE600-0FF9-44D9-A0CC-F0821DD8E76C.png)
* 단, RTT가 같을 때에만 적용됨.
* 다를 경우 이런 형태의 그래프가 나오지 않음

![](Ch3_transport2/D4633CE5-8D3C-4A60-B85D-780A3CB0BC66.png)
* 꼭 모든 앱들이 TCP를 사용하는 것이 아니다.
* 대신 UDP를 사용하는것도 괜찮다.

#네트워크