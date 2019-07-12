# Ch2_Principles_SMTP
Application Layer는 메세지를 어떻게 보낼지만 고민하면 된다.

Always-on host

**Self scalability** (자가 확장성)

**프로세스** : 호스트 내에 실행중인 프로그램

IP 주소 : 컴퓨터를 식별하는 주소
Port 번호 : 컴퓨터 내의 프로세스를 식별하기 위한 주소

메세지를 받기 위해선 Identifier가 있어야 한다.
Identifier = (IP address, port number)

포트 번호 
HTTP Server : 80
Mail server : 25

## App-layer protocol
* Types of messages 
	* 	Request , Response
* Message syntax (문법)
	* 메시지에 어떤 필드, 어떻게 필드가 묘사되는지
* Message semantics (문맥)
	* 필드 정보의 의미 : 들어오는 값이 어던 뜻을 가지는가
* Rules
	* when and how processes send & respond to messages


Throughput : 단위 시간 당 실제로 받은 데이터 양
Bandwidth : 단위 시간 당 받을 수 있는 최대 데이터 양


이메일 주소 : 대소문자 구별 X
한글도 지원하면 가능

![](Ch2_Principles_SMTP/A21F73C9-D77B-4A30-A3D2-3695E901CBEC.png)


## 이메일의 주 구성 요소
* User Agents
* Mail servers
* SMTP
* Mail access protocol

### POP3
POP3 “download and delete” mode
	* 클라이언트 변경 시 이메일 다시 읽기 불가능
POP3 “download-and-keep” : 다른 클라이언트에서 메시지를 복사

POP3 -> stateless across sessions

### IMAP
메세지를 전부 서버에 저장





#네트워크