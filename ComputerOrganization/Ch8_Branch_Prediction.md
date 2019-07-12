# Ch8_Branch_Prediction

* 긴 파이프라인은 브랜치 outcome 을 미리 알 수 없다
	* Stall penalty 발생

* 브랜치의 outcome을 예측 불가
	* 예측이 틀렷을때만 stall

* In MIPS pipeline
	* 통계적으로 not take인 브랜치 예측 가능
	* delay 없이 브랜치 이후 명령어 fetch 가능


## Dynamic Branch Prediction
* 하드웨어가 실제 브랜치 행동 기록
	* 각각의 브랜치의 실제 히스토리 기록

* 미래의 행동이 트렌드롤 지속할거라고 간주
	* 틀렸을 경우 re-fetching시 stall하고 히스토리 업데이트함

![](Ch8_Branch_Prediction/BB307E26-29AC-4ED2-A6A9-ECE5ED0BA825.png)


## Branch Direction Prediction
* **Direction predictor (DIRP)**
	* Map conditional - branch PC to taken/not-taken (T/N) decision
* **Branch history table (BHT)** - simplest predictor
	* PC indexes table of bits ( 0 = N, 1 = T) , no tags
	* 근본적으로 : 브랜치는 마지막에 갔던 곳으로 다시 감

## Branch History Table (BHT)
가장 간단한 방향 예측기
*  PC indexes table of bits ( 0 = N, 1 = T) , no tags
* 	* 근본적으로 : 브랜치는 마지막에 갔던 곳으로 다시 감
* 단점 : **inner loop branch**
![](Ch8_Branch_Prediction/CEEC318D-92D0-4F91-99D8-7F85CC0DCACF.png)



## Two-Bit saturating counters
![](Ch8_Branch_Prediction/4FC24E84-A7FB-4259-8A28-812D86239170.png)

* **Hysteresis**

## Branch Target Address Prediction
* **Branch target buffer (BTB)**
	* a small hardware table
	* is - a - branch = (BTB[hash(PC)].tag == PC) ? 1 : 0
	* predicted-target = (BTB[hash(PC)].tag == PC) ? BTB[PC].target : 0 
	
## Compiler Approach : Delayed Branching
**컴파일러의 최적화**45
![](Ch8_Branch_Prediction/AA3D584D-D1E4-47C2-9345-A81DADDC4EBB.png)

항상 branch 다음 슬롯은 딜레이를 위해 남겨놓는다
그래서 여기에 다른 instruction을 채워서 효율성 증가


## Precise Exception with Pipeline
* Traps (인스트럭션에 의해 invoke)
	* 그 다음의 인스트럭션을 모두  squash
	* pc를 exception handling routine로 설정

* External Interrupts - 비동기적으로 발생하는 이벤트
	* 프로세서 상태가 MEM과 WB에서 업데이트됨
	* WEM, Wb 중간에서 반드시 인스트럭션을 완료해야 한다.

### Superscalar	
	* 	2-way superscalar -> 한번에 인스트럭션을 2개 fetch

### Out-of-order Execution
	* 	Instruction Level Parallelism (ILPs) 
		* 프로세서는 인스트럭션의 순서를 동적으로 변경하여 ILP를 최대화해야 한다.

### Hardware Multithreading (a.k.a Intel Hyper Threading)
	* 	PC 와 레지스터 파일을 제외한 하드웨어 자원들을 공유하여 TLP 최대화
		* TLP - Thread Level Parallelism
	* 프로세서는 하드웨어 스레드를 스케쥴링을 책임진다
		* 소프트웨어 멀티스레딩 기술이 아님









#컴퓨터구조