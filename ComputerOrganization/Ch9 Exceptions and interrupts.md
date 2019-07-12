# Ch9 Exceptions and interrupts
## CPU must prepare for ‘unplanned’ events
* 탐지하고 빨리 처리해야 하는 굉장히 드문 예외 조건들을 처리할 수 있는 시스템적 방법
	* 인스트럭션은 실패 할거나 완수되지 못할 수도 있다.
	* 외부 I/O 장치들이 servicing이 필요하다
	* time-shared 시스템에서 quantum expiration

* Option 1 : 모든 가능한  만일의 사태에 대해 모든 프로그램을 연속적인 체크로 구현 ( **a.k.a. Polling**)
* Option 2 : 이상한 작업이 발생하지 않는 best-case 시나리오의 ’보통’ 프로그램을  작성
	* 하지만 하드웨어의 예외 조건들은 탐지해야한다.
	* 조건을 해결하고 권한을 다시 프로그램으로 돌려주는 exception handler에게 “transparently”하게 권한을 넘겨준다.


## Interrupt Control Transfer
* **Interrupt** - 시스템 루틴에 대한 계획되지 않은 함수 콜 (a.k.a - interrupt handler)
* 보통 함수랑 달리, 인터럽트된 스레드는 컨트롤 전환을 예측하거나 대비를 할 수 없다.
* 컨트롤은 나중에 인터럽트된 인스터럭션으로 메인 스레드로 반환된다.


## Types of Interrupts
* **Synchronous Interrupts** (a.k.a, **exceptions**)
	* exceptional conditions **tied to a particular instruction**
		* ex - illegal opcode, illegal operand, virtual memory management faults
	* faulting 인스트럭션은 완수될 수 없다.

* **Asynchronous Interrupts** (a.k.a, interrupts)
	* external events **not tied to a particular instruction**
		* ex - I/O events, timer events
	* Some flexibility on when to handle it
	약간의 딜레이는 허용되지만 계속 지연할 수 는 없다.

* System Call/Trap Instruction
	* 	예외를 발생시키는게 목적인 instruction


## Interrupt Sources
![](Ch9%20Exceptions%20and%20interrupts/7FECC803-454D-4B84-AACD-574E09708A61.png)


## Virtualization and Protection
* 모던 OS는 **time-shared multiprocessing**을 지원, 하지만 각 “user-level” 프로세스는 혼자라 생각
	* 각 프로세스는 user-level의 아키텍쳐적 상태의 프라이빗한 set을 본다. 이것은 user-level 인스터럭션 세트 에서만 변경이 가능하다.
	* 각 프로세스는 이 추상화 밖에 있는 상태나 장치를 직접적으로 보거나 조작할 수 없다.

* OS implements and manages a critical set of functionality
	* user-level 프로세스에서 low-level 세부 정보들을 뺌
	* 각 유저레벨 프로세스들을 서로와 자신으로부터 보호함

## Control and Privilege Transfer
* 유저 레벨 코드는 절대 privileged mode에서 실행되지 않는다.
* 프로세서는 interrupt 발생 시에만 privileged 모드에 들어간다.
	* user code는 OS kernel에 있는 핸들러에게 권한을 넘겨줌
* 핸들러는 유저 코드에게 권한을 돌려주기 전에 privilege 레벨을  유저 모드에게 다시 복원해준다.

![](Ch9%20Exceptions%20and%20interrupts/69EB4B82-C5D5-4410-A4FC-43C0C7C03F2A.png)


## Implementing Interrupts
### Handling Exception in MIPS
* In Mips, 예외는 System Control Coprocessor 0 (CP0) 가 관리한다
-> 메인 프로세서가 아닌 별도의 프로세서

* offending(or interrupted) 인스트럭션의 PC를 저장한다
	* In MIPS : Exception Program Counter (EPC) 
	-> 실제 프로그램 카운터는 아님, Fault를 일으킨 Exception을 기록함

* Save indication of the problem
	* in MIPS : cause register
	* 1-bit라 가정
		* 0 -> undefined opcode
		* 1 -> overflow
		 
* Jump to handler at 8000 00180

### Alternate Mechanism
* Vectored Interrupts
	* 핸들러 주소는 원인에 따라 결정된다

* Example
	* undefined opcode : 8000 0000
	* overflow : 8000 0180
	* … : 8000 0300

* Instructions either
	* Deal with the interrupt, or
	* Jump to real handler

### Handler Actions
* 원인을 읽고, 적합한 핸들러에게 전달
* action 판단 필요
* If restartable
	* Take corrective action
	* use EPC to return to program

* Otherwise
	* 프로그램 종료
	* EPC를 통해 에러 보고,

### “Precise” Interrupt/ Exception
![](Ch9%20Exceptions%20and%20interrupts/E7274742-69B4-48FB-B397-8C6C133CBF7A.png)

* 정확히 두 인스트럭션 의 사이에 예리한 interrupt가 발생
	* older instruction은 완전히 실행됨
	* younger instruction은 실행되지 않은 것처럼 동작
	* synchronous interrupts(exception)-> execution은 딱 faulting instruction 직전에 멈춘다. 그리고 인터럽트 핸들링이 끝나면 재개함.

### Exceptions in a pipeline
* another form of control hazard
* Consider overflow on add in EX stage
	add. $1,  $2,  $1
	* $1이 손실되는것을 예방해줌
	*  이전 인스트럭션 완수해줌
	* add랑 그 다음의 인스트럭션 flush 해줌
	* cause랑 etc 레지스터 값 설정함
	* 핸들러한테 컨트롤 넘겨줌

* 잘못 예측된 브랜치와 비슷함
	* 같은 하드웨어의 대부분을 사용함


### Exception Sources in Different Stages
* **IF**
	* 	Instruction memory address / protection fault
* **ID**
	* Illegal opcode
	* Trap -> unimplemented 된 인스트럭션의 SW 에뮬레이션
	* system call instruction (an intended exception requested by SW)

* **EX**
	* invalid results : overflow, divide-by-zero,…

* **MEM**
	* Data memory address, protection fault

* **WB**
	* Nothing can stop an instruction now…	 

* We can associate async interrupts (I/O) with any instruction/stage we like

![](Ch9%20Exceptions%20and%20interrupts/CD30AE64-5E8E-4EF6-8FD1-D66089A6E059.png)


## Multiple Exceptions
* Pipelining overlaps multiple instructions
	* 예외가 한번에 여러 개가 발생할 수 있다.
* Simple approach : 초기 인스트럭션에서 예외를 처리한다.
	* 연속된 인스트럭션 flush
	* “Precise” exceptions

* In complex pipelines
	* multiple instructions issued per cycle
	* out-of-order completion
	* maintaining precise exceptions is difficult

## Imprecise Exceptions
* 그냥 파이프라인을 멈추고 상태를 저장함
	* 예외 원인(exception causes)들 포함
* 핸들러가 처리하도록 한다
	* Which instructions had exceptions
	* which to complete or flush
		* may require “manual” completion
* Simplifies hardware, but more complex handler software
* Not feasible for complex multiple-issue out-of-order pipelines






#컴퓨터구조