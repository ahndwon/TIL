# Ch7_MIPS_pipeline
![](Ch7_MIPS_pipeline/55EEBF46-EC59-4034-AA02-762C2F6FF84C.png)

![](Ch7_MIPS_pipeline/B17DD6B6-8F60-4E0D-A08C-2A1BDE3513ED.png)


## Pipelining
* 중요한 성능 향상 테크닉
	* instruction throughput 향샹
	* **하지만** latency는 그대로

* latency vs. throughput : two view of performance
	1. at the program level
	2. At the instruction level

* Single instruction latency
	* 상관없음
	* 어차피 줄이기 어려움

* 각각의 인스트럭션을 빠르게 하기 보단 프로그램을 빠르게 하는게 목표
	* 	instruction throughput  -> program latency
	* key :  inter-instruction parallelism 사용

![](Ch7_MIPS_pipeline/2DE95B12-2277-4CDC-BFF5-596431B5B533.png)



## Recall : Simple MIPS Execution Model

![](Ch7_MIPS_pipeline/98CF7407-88C7-4D28-A3D3-D84EDF15E96F.png)




![](Ch7_MIPS_pipeline/3F34B94A-0848-444D-B55C-9B9DDD8A07E7.png)


Instruction latency - 4 * clock cycle length
**lw** - uses most hardware
**Structural hazard** - Mips architecture에선 신경 안써도 됨

**Data Hazards**
* Read after write (RAW) - MIPS 에선 이 hazard만 발생
* Write after read (WAR) 
* Write after write(WAW)

## Handling Data Hazards
* NOPs : No Operations
	* lw 일 경우 nops 2번
* Forwarding

![](Ch7_MIPS_pipeline/F322AAD7-D57C-4606-927E-49C0D36343AD.png)

![](Ch7_MIPS_pipeline/24F0CED1-7766-4705-9923-72DF5D4D4BF4.png)

![](Ch7_MIPS_pipeline/528AD765-CF3E-4AA1-9A5E-D9089DE37C1C.png)

![](Ch7_MIPS_pipeline/58B9A69F-DA05-4FD7-BC37-FB80BF5178DF.png)


![](Ch7_MIPS_pipeline/5E3A4C65-329D-470D-8EBA-5C68825B3DFE.png)


![](Ch7_MIPS_pipeline/C560F507-F92A-4B38-83B0-D3773B38E115.png)

![](Ch7_MIPS_pipeline/8D422112-2477-4E03-88F3-D1F4A941F87C.png)

![](Ch7_MIPS_pipeline/47AA6871-FA9F-4FF7-817A-45AE03DEFB8E.png)




#컴퓨터구조