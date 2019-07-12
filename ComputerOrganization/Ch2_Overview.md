# Ch2_Overview

![](Ch2_Overview/970DFFE2-053B-4394-BA98-27CD1CD27C8D.png)

* Application software
	* high-level 언어로 작성
* System software
	* compiler : HLL code를 머신 코드로 번역
	* OS : service code
		* handling input/output
		* managing memory and storage
		* scheduling tasks & sharing resources

* Hardware
	* Processor, memory, I/O controllers

## 무어의 법칙 (1965)
* 칩 당 트랜지스터 수가 18개월 마다 두 배로 늘어난다.
* 칩의 정밀도가 올라갈 수록 fabrication plants(제조 설비)의 비용은 기하급수적으로 증가한다.
* 40년 이상 지켜짐



## Inside the Processor (CPU)
* Datapath : performs operations on data - 데이터가 이동하는 경로
* Control: sequences data path, memory, …
* cache memory : small fast SRAM memory for immediate access to data
	-> 메모리는 느리기 때문에 캐쉬를 통해 CPU의 idle 시간을 줄임



## Abstraction
* Abstraction 는 복잡함을 덜어줌
	* lower-level detail을 숨김

* ISA (Instruction Set Architecture)
	* hardware/software interface

* Application Binary Interface
	* ISA plus system software interface

* Implementation
	* the details underlying and interface




LAN - Local Area Network : Ethernet
WAN - Wide Area Network : Internet

## Instruction Codes
* **Program** - a sequence of (machine) instructions
* (Machine) **instructions**
	* A group of bits that tell the computer to **perform a specific operation** -> a sequence of micro-operation


## Example of Instruction Codes
* Load
	* load data stored in memory to a register
* Store
	* Store data in a register to memory
* Add
	* add two numbers stored in registers
* Increment
	* Increase a number stored in a register
* Clear
	* Set to zero in a register



## Response Time and Throughput
* Response time : 작업이 걸리는 시간
	* how long it takes to do a task

* Throughput : 단위 시간 당 한 일
	* total *work* done per unit time

* Elapsed Time
	* Total response time
	
* CPU time
	* Time spent processing a given job
		* discounts I/o time, other jobs shares                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    

## CPU Clocking
* Operation of digital hardware governed by a constant rate clock
* Clock Period : duration of a clock cycle
* Clock frequency (rate) : cycles per second


![](Ch2_Overview/64191602-91CC-4E07-9F39-6E8B83AF616E.png)

![](Ch2_Overview/CD1F50F1-C695-4E69-820F-455FB2B29EC6.png)


Clock Cycles = Instruction Count x Cycles per Instruction
CPU Time = Instruction Count x CPI x Clock Cycle Time
	= Instruction Count x CPI / Clock Rate


## Instruction Count and CPI

![](Ch2_Overview/3BFAC6A4-E109-456B-A240-034FD1CEEA7B.png)


![](Ch2_Overview/A9034D78-2545-4E71-B639-4D153B917835.png)

![](Ch2_Overview/58E965E5-ABFB-4330-B0B3-4483F0981DF1.png)

## Power
![](Ch2_Overview/004094D4-CAE8-4735-BE74-C6831B799DC8.png)


**Power wall**
- voltage 전압을 더 이상 줄일 수 없다.
- heat 열을 더 이상 줄일 수 없다.


-> 멀티프로세서로 해결


## Multiproccesors
멀티코어 프로세서
* 명시적인 병렬 프로그래밍을 해야함
	* 하드웨어가 여러 인스트럭션을 동시에 실행함
	* 프로그래머가 알 수 없다
	
* 어려운 거
	* 	퍼포먼스를 위해 프로그래밍
	* 로드 밸런싱
	* communication, **synchronization** 최적화
	



## Amdahl’s Law
암달의 법칙
T improved = T affected / improvement factor + T unaffected

![](Ch2_Overview/8081CBE9-998C-4C2E-8350-3AF5AAC93975.png)

![](Ch2_Overview/E232CD1D-3A9E-46E3-A079-C4AA1F09E485.png)






#컴퓨터구조 