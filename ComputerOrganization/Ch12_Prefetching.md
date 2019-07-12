# Ch12_Prefetching
* 데이터를 캐쉬에 미리, 명상적으로 가져옴
	* 만약 성공적이면 캐쉬 미스를 줄일 수 있음
* 핵심 : 앞으로의 miss address 들을 정확히 예측할 수 있음
	* 소프트웨어, 하드웨어로 할 수 있다
	
* Effectiveness
	* Accuracy 정확도 - 몇개의 prefetches were useful
	* Coverage : 얼마나 미스를 줄일 수 있는 지
	* TimeLiness : prefetches 미리 충분히 한다

* 간단한 하드웨어 prefetching : next block prefetching
	* 단점 : Miss on address X -> X + block-size 만큼 미스 예측
	* 장점 : insns에서 작동함 : sequential execution
	* 장점 : works for data, arrays

## Common Prefetches
* 다음줄, 근처 줄 

* Stride
	* A 주소르롤 위해, A + S * B만큼 Prefetch.
	* 	Stride = S * B

* Pointer
	* Pointer chasing (linked list)
	* Find next address from data

* Correlation
	* Find address correlation : A is followed by B
	* Markov chain model

* Region
	* For address A, prefetch A - N, …, A - 1, A + 1, … , A + N

## Software Prefetching
* Use a special “prefetch” instruction
	* 하드웨어가 데이터를 가져오라고 하지만, 실제로 읽진 않음
	* 그냥 힌트이다
* 프로그래머나 컴파일러가 삽입함
* Example
	
![](Ch12_Prefetching/B885B323-567E-40E8-B314-6D097243ECA6.png)

* multiple prefetches bring multiple blocks in parallel
	* More “memory-level” parallelism (MLP)


## Software Restructuring : Data
* Capacity misses : poor spatial or temporal locality|
	* Several code restructuring techniques to improve both
		* 	loop blocking (break into cache-sized chunks), loop fusion
	* 재구조화는 semantics를 보존하는걸 컴파일러가 알아야 한다.

* Loop interchange : spatial locality
	* Example: for a row-major matrix, X[i][j] should be followed by X[I][j+1]
	* Poor code : X[I][j], followed by X[I+1][j]
	* Better code
		
![](Ch12_Prefetching/095F5F0F-741C-4BBA-911B-58EE9360FADD.png)



## Software Restructuring : Data
* Loop Blocking : temporal locality
	* **Poor code**	
		* ![](Ch12_Prefetching/023837B1-F598-4B60-A03B-F4B1A3F92171.png)


	* **Better code**
		* 배열을 CACHE_SIZE 청크로 자른다.
		* 하나의 덩어리에 모든 단계를 실행하고, 다음 덩어리로 진행	
		* ![](Ch12_Prefetching/86D2D110-0668-48C4-9483-FA99B22BE933.png)

	* 캐쉬 사이즈를 안다고 가정

![](Ch12_Prefetching/FC2C8EF0-2C8A-4646-9122-ED9AC7DCCDA6.png)











#컴퓨터구조