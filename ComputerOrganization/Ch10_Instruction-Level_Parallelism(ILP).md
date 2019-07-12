# Ch10_Instruction-Level_Parallelism(ILP)
## Instruction-Level Parallelism (ILP_
* Pipelining : executing multiple instructions in parallel
* To increase ILP
	* Deeper pipeline
		* Less work per stage -> shorter clock cycle
	* Multiple issue
		* Replicate pipeline stages -> multiple pipelines
		* start multiple instructions per clock cycle
		* CPI < 1, so use Instructions Per Cycle (IPC)
		* e.g., 4GHz 4way multiple-issue
			* 16 BIPS, peak CPI = 0.25, peak IPC = 4
		* But dependencies reduce this in practice


## Multiple Issue
* **Static multiple issue**
	* Compiler groups instruction to be issued together
	* packages them into “issue slots”
	* compiler detects and avoids hazards

* **Dynamic multiple Issue**
	* CPU examines instruction stream and chooses instructions to issue each cycle
	* Compiler can help by reordering instructions
	* CPU resolves hazards using advanced techniques at runtime


## Speculation 
추측
* “Guess” what to do with an instruction
	* Start operation as soon as possible
	* check whether guess was right
		* if so, complete the operation
		* if not, roll-back and do the right thing
* 	Common to static and dynamic multiple issue
* Examples
	* speculate on branch outcome
		* roll back if path taken is different
	* speculate on load

## Compiler/Hardware Speculation
* Compiler can reorder instructions
	* move load before branch
	* can include “fix-up” instructions to recover from incorrect guess

* Hardware can look ahead for instructions to execute
	* buffer results until it determines they are actually needed
	* 추측이 부정확할 경우 버퍼를 flush


## Speculation and Exceptions
* 만약 추측하여 실행한 인스트럭션에서 예외가 발생하면?
	* 예) 널 포인터 체크 전에 추측성 로드

*  Static speculation
	* can add ISA support for deferring exceptions

* Dynamic speculation
	* can buffer exceptions until instruction completion (which may not occur)


## Static Multiple Issue
* 컴파일러는 인스럭션을 “issue packets”로 그룹화한다.
	* 싱글 사이클로 이슈화 될 수 있는 인스트럭션의 그룹들
	* 필요한 pipeline 자원들로 결정됨

* Issue packet 을 매우 긴 인스트럭션으로 여겨라
	* 다중 동시 작업들을 특성화한다.
	-> Very Long Instruction Word (VLIW)

## Scheduling Static Multiple Issue
* 컴파일러는 반드시 일부/전부 hazard를 제거해야 한다.
	* 인스트럭션을  issue packet으로 reorder
	* no dependencies with a packet
	* 각각의 패킷 사이에 의존성 존재할 가능성 있음
		* ISA에 따라 다르다. 컴파일러는 반드시 알아야한다.
	* Pad with top if necessary


## MIPS with Static Dual Issue
* Two-issue packets
	* One ALU/branch instruction
	* One load/store instruction
	* 64-bit aligned
		* ALU/branch, then load/store
		* pad an unused instruction with nop
![](Ch10_Instruction-Level_Parallelism(ILP)/DC2A54A2-9969-4AC2-9E04-6C546900AE8D.png)

![](Ch10_Instruction-Level_Parallelism(ILP)/6512B750-073C-460B-8567-FC3039E3AD35.png)

## Hazards in the Dual-Issue MIPS
* More instructions executing in parallel
* EX data hazard
	* forwarding avoided stalls with single-issue
	* 같은 패킷 load/store 안에 있는 ALU 결과를 사용할 수 없다
	-> 두개의 패킷으로 나누어야 한다, effectively a stall
* Load-use hazard
	* Still one cycle use latency, but now two instructions
	* More aggressive scheduling required

![](Ch10_Instruction-Level_Parallelism(ILP)/5838FD73-C1A8-47F8-A19B-3664A7C51B07.png)


![](Ch10_Instruction-Level_Parallelism(ILP)/696372EA-6834-40C9-A11F-C5BFB42B32A7.png)
![](Ch10_Instruction-Level_Parallelism(ILP)/91BD70DB-BD91-4181-9C0B-16245BD023A2.png)
![](Ch10_Instruction-Level_Parallelism(ILP)/F91518CA-D22B-4981-AB27-8A64865BB761.png)









#컴퓨터구조