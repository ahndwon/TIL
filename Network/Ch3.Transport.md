# Ch3.Transport
## Transport Layer
서로 다른 어플리케이션 프로세스들이 상호 통신하는 계층

Logical Communication between application processes running on different hosts

* **Sender** - 앱 메세지들을 부분(segment)으로 쪼갠 후 네트워크 계층에 전달
* **Receiver** - 쪼개진 부분들을 메세지로 합친 후 어플리케이션계층에 전달

## Transport layer protocols
**TCP** - Reliable, in-order delivery
* Congestion control
* Flow Control
* Connection Setup

**UDP** - unreliable, unordered delivery 
* no-frills extension of “best-effort” IP

불가능한 서비스
* Delay guarantees
* Bandwidth guarantees


**Multiplexing at sender**
여러 소켓에서 데이터를 처리, 나중에 demultiplexing을 위한 transport header를 추가함

**Demultiplexing at receiver**
Header info를 통해 받은 segment들을 올바른 소켓에 전달


## Reliable Data Transfer
### rdt 1.0
가정 - 채널 완벽히 신뢰 가능
-> bit 오류 무
-> 패킷 손실 무


### rdt 3.0
* 가정 - 채널이 패킷의 비트를 flip 할 수도 있다.
	* checksum을 사용해서 비트 오류를 탐지해야 한다.
* 채널에서 패킷 손실이 일어날 수 있다.
* 체크섬, 순서 번호, ACKs, 재전송이 도움이 되지만 완벽한 해결법은 아니다.


* Sender는 일정 시간 이상 ACK를 기다린 후, ACK를 수신하지 못하면 재전송한다.
* 








#네트워크