# Ch3_Digital_Systems_and_Logic
## Hardware Design Hierarchy
![](Ch3_Digital_Systems_and_Logic/7062DC45-1B95-412F-B8E6-ECB6AAB998B5.png)


## Switch
가장 기본적인 원소

input이 1 이면 switch는 **asserted**

NAND - AND, OR, NOT은 NAND로 구성됨 -> 제일 트랜지스터로 구성하기 좋은 블럭


##  Type of Circuits
1. Combinational Logic
	* 중간 결과 기억 불가
	* 출력은 현재 입력에만 의존한다
	* ex ) circuits add A, B (ALUs)

2. Sequential Logic
	* 출력은 현재 입력 + 현재 상태(저장된 값)에 의존함
	* 메모리 원소를 포함한다
	* ex) 메모리, 레지스터


## ALU (Arithmetic Logic Unit)
* combinational digital electronic circuit - performs **arithmetic** and **bitwise operations** on integer binary numbers
* ALU 

## Truth Tables
	* CL 회로를 input 과 output 을 연관해서 보여주는 표
	* 출력은 현재 입력에만 의존한다
	* high/low voltage 보단 0/1로 추상화 한다
	* 모든 경우의 수 보여줌
	* 크기는 2^n 


![](Ch3_Digital_Systems_and_Logic/2AC5A25C-AB89-4EE5-B6A8-044DCBC6CDFE.png)



## Critical Path
* **longest Delay** between Any two registers in a circuit
* **Clock period** must be **longer** than this critical path or the signal will not propagate properly

클럭 주기는 반드시 Critical Path보다 길어야 한다. 아니면 시그널이 제대로 전파되지 않는다.


## 요약
* 하드웨어 시스템은 **Stateless** Combination Logic과 **Stateful** Memory Logic으로 구성된다.

* Voltage는 아날로그지만 논리 0과 1을 표현하기 위해 양자화됨

* Combinational Logic : equivalent circuit diagrams, Truth tables, and Boolean expression
	* 	Boolean algebra allows minimization of gates

* Sequential Logic == Stateful “Memory” Logic
	* State registers implemented from Flip-flops








#컴퓨터구조