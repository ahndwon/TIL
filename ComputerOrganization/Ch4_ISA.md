# Ch4_ISA
A Case Study With MIPS

## Computer Architecture
### ISA - Instruction Set Architecture
* 	HW 와 SW 사이의 인터페이스를 정의함
* 호환성 문제로 쉽게 변깅하기 힘듬
* 칩 제조사에서의 다양한 ISA

### 마이크로 아키텍쳐
* ISA의 implementation


## 머신 디자인의 두가지 주요 원칙
* Instruction은 숫자로 표현되며 데이터와 구분 불가능하다
2. 프로그램은 데이터와 마찬가지로 변경 가능한 메모리에 저장된다


### Stored program computers
	* Instructions 는 이진수로 표현됨 -> 데이터와 같음
	* Instuctions 와 데이터는 메모리에 저장됨
	* 프로그램은 프로그램 위에 구동됨
	* 이진수 호환성은 컴파일된 프로그램이 다른 컴퓨터에서 실행될 수 있게 해줌


## Mainstream ISAs
* X86 - IntelㅁMD , 
ㄷ* CISC,
	* 16, 32 ,64 bit
	* type : Register - memory
* ARM
	* 32, 64 bit
	* RISC 
	* Register - Register
* MIPS
	* 64bit
	* RISC
	* type : Register - Register


## MIPS-32 ISA
32 / 64 bit - 한번에 데이터를 보내는 양

**Instruction Categories**
	* Computational
	* Load / Store
	* Jump and Branch
	* Floating Point
	* Memory Management
	* Special
	

Registers - R0 ~ R31, PC, HI, LO

![](Ch4_ISA/B8A8FD3C-8189-4542-8BAF-AC326FC71306.png)



## MIPS Design Principles
* 간결함은 규칙적인것을 좋아한다
	* Instruction Size 고정
	* 작은 크기의 instruction formats
	* Opcode는 항상 가장 첫 6bit

* Smaller is Faster
	* 한정된 instruction set
	* 레지스터 파일 내에 한정된 갯수의 레지스터
	* 한정된 갯수의 addressing modes

*  Make the common case fast
	* 레지스터 파일에서의 Arithmetic operands
	* Instruction이 immediate operands를 포함하도록 허용


## MIPS Arithmetic Instructions
* Arithmetic operations
	* Add and subtract, three operands
		* Two sources and one destination

**word** : 32-bit data

![](Ch4_ISA/A609A1DC-DDE2-4672-B737-50188A6665AA.png)

## MIPS Register Operands
* Arithmetic instructions use register operands
* MIPS는 32 x 32 bit register file을 가진다
	* 자주 접근하는 데이터에 사용
	* Numbered 0 to 31
	* 32-bit data called a “word”
* 어셈블러 이름들
	* $t0, $t1, … , $t9 -> 일시적 밸류
	* $s0, $s1, … , $s7 -> 저장된 밸류

![](Ch4_ISA/F0BE1466-B0AD-460D-BAAA-86F3CB27345A.png)



## MIPS Register Operand Example
![](Ch4_ISA/491A214C-4248-45DC-B180-D20014DF9152.png)




## MIPS Memory operands
* Main memory는 composite data로 사용됨
	* Arrays, structures, dynamic data
* Memory 는 byte addressed
	* 각 주소는 8비트이다
* Words are aligned in memory
	* address는 반드시 4의 배수여야 한다.
* MIPS is Big Endian
	* Most-significant byte at least address of a word


## MIPS Memory Operand Example
![](Ch4_ISA/FEB46461-B703-459C-9709-8CDA91C50786.png)


## Register vs Memory
* Regsiters are faster to access than memory
* 메모리 데이터ㅓ를 사용하기 위해선 Load ㅘ store을 해야 한다
	->. 더 많은 Instruction을 사용해야 한다.
* 컴파일러는 레지스터를 변수로 최대한 많이 사용해야 한다.
	* 덜 쓰는 변수들들만 메모릴 ㅗ옮겨야 한다
	* 레지스터 최적화는 중요하다.


![](Ch4_ISA/42D35DDC-4183-43DF-9D24-63380D1C8FA0.png)

![](Ch4_ISA/0986F3EC-1C0E-4BE4-9385-963E7534ED6D.png)




## Basic Blocks
: **Sequence of instructions**
* No embedded branched (맨 끝 예외)
* No branch targets (맨 처음 예외)


## Branch Instruction Design
Hardware for <, <=, … is SLOWER than = , !=
* combining with branch involves more work per instruction, requiring a **slower clock**
* All instructions penalized




## Six Steps in Execution of a Procedure
1. Main routine (caller) 가 procedure (callee)가 접근 할 수 있는 곳에 파라미터를 위치시킨다
2. Caller 가 Callee 에게 컨트롤을 넘겨준다
3. Callee는 필요한 스토리지 자원을 얻는다
4. Callee 는 필요한 작업을 한다
5. Callee는 Caller가 접근 할 수 있는 곳에 결과 값을 위치시킨다.
	*  $v0 , $v1 : 두 가지 값을 결과 값으로 등록한다.
6. Callee 는 caller에게 컨트롤을 넘겨준다.
	* $ra : 기존 point에 address register를 리턴


## Procedure Calls
**Jump and Link**
`jal ProcedureLabel`
	* 	address of following instruction put in $ra
	* jumps to target address

**The jal instruction actually saves PC + 4 in register $ra to link to the following instruction to set up the procedure return.** 
-> jal은 $ra에 PC + 4 인 값을 저장한다.


**Procedure return : jump register**
`jr $ra`
함수를 타고 또 타들어갈 경우 $ra 가 덮어씌어지게 되므로 이미 있던 값을 Stack 에 저장한다.
	* copies $ra to program counter -> 다음으로 실행할 명령을 저장, ex (함수 이동시에도 어디로 돌아올지 저장)
	* computed jumps에도 사용될 수 있따.

$ra 함수가 끝나면 돌아가야 할 주소

## Allocating Space on the Stack
* 스택은 레지스터에 안 맞는 지약 변수들을 저장하기도 한다
	* ex ) local arrays or structures
* **frame pointer** - 함수가 실행되면 시작 위치를 가리킴 (base 주소)


##  Memory Layout
* Text - Program code
* Static data : global variables
	* ex) static variables in C, constant Arrays and Strings
	* $gp initialized to address allowing +- offsets into this segment

* Dynamic Data : Heap
	* ex) malloc in C, new in Java
* Stack : automatic storage


![](Ch4_ISA/08BF2EC9-5828-4F04-B57A-C2CFDB884924.png)


## Character Data
* Byte-encoded character sets
	* ASCII - 128 chracters
		* 95 graphic, 33 control
	* Latin - 1 : 256 characters
		* ASCII, +96 more graphic characters

* Unicode : 32-bit character set
	* Used in Java, C++ wide characters,
	* 제일 널리 쓰임
	* UTF-8, UTF-16 : variable-length encodings
		*  아스키를 8비트로, 다른 캐릭터를 16비트로

![](Ch4_ISA/EDC88CC2-4EBD-48D3-A480-F9404B5F16A2.png)

## Addressing : 32-bit immediate operands
* 대부분 상수는 작다
	* 16bit가 효율적

* 만약 16로는 부족하여 32비트를 사용해야 할 경우도 존재







#컴퓨터구조