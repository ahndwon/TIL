# Ch11_Memory Hierarchy Basics / Cache
## Memory Hierarchy
* **Trade-off** speed vs cost (or latency vs. capacity)
	* small, fast and expensive <- -> large slow and cheap components
![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/F4D6685D-B0DE-4AF9-93BE-F425F8E62485.png)

## Principle of Locality
* 프로그램들은 언제나 자신의 주소의 작은 부분을 접근한다.

* Temporal Locality
	* 최근에 접근한 아이템들은 또 접근할 가능성이 높다
	* ex - instructions in a loop, induction variables

* Spatial Locality
	* 접근된 아이템들 주변도 곧 접근할 가능성이 높다
	* ex - sequential instruction access, array data

## Caches
* “Cache” : hardware managed
	* 하드웨어가 자동으로 없는 데이터 가져옴
	* 온칩 SRAM 으로 만들어짐
	* off-chip. DRAM 과 반대

* Cache ABCs
	* Associativity
	* Block size
	* Capacity

* Performance Optimizations
	* prefetching & data restructuring
* Handling Writes
	* Write-back vs. Write Through
* Memory Hierarchy
	* Smaller, faster, Expensive -> bigger, slower, cheaper


## Cache Metrics
* Hit Latency
	* 대부분의 L1(1-3 cycles) and L2 caches (8-20 cycles) 는 고정
	* 몇 L2, L3 캐쉬는 가변적 latencies 가짐
* Miss rate = cache misses / cache accesses
* Miss latency
	* Time to fetch from the next level of cache or from the memory
* Average access time = hit latency + miss rate * miss latency

* Improving cache performance
	* Reduce hit latency -> smaller caches
	* Reduce miss rates -> larger caches
	* Reduce miss latency -> better lower-level cache/memory


## Hardware Cache Organization
* Cache는 하드웨어 해쉬 테이블임
	* with one exception : data not always present

* Unit of data
	* Cache block (or cacheline)
	* 32byte, 64byte, …

* Cache organization
	* 32KB organized as 1K blocks of 32B each
	* Each block can hold a 32Byte

* How to find data from cache
	* Address Mapping
	
* How to fill data to cache
	* Miss Handling


## Indexing : Looking Up A Block
![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/D9A687F7-2A7F-4AC5-9BB3-F98D326BE4D0.png)


![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/5763A1F7-4308-445C-A451-6EF7763B15E6.png)


## Serial and Parallel Tag/Data Access
* Serial tag and data array access
	* Access data array only for hits
	* 단점 : Long hit latency : tag access time + data access time
	* 장점 : saving power

* Parallel tag and data array access
	* Access data array for all accesses
	* 장점 : short hit latency
	* 단점 : wasting power for misses


## Handling a Cache Miss
* 만약 요청한 데이터가 캐쉬에 없을 시
	* 어떻게 집어넣냐

* Cache Controller - finite state machine
	* remember miss address
	* accesses next level of memory (L1 -> L2 -> L3 -> DRAM)
	* Waits for response
	* Writes data/tag into proper locations

![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/590137D7-3528-4F70-B252-F9DDCFD67CE4.png)


## Split vs Unified Caches
* Split I$ / D$ : instructions 와 데이턱가 다른 캐쉬에 저장
	* thit가 structural hazards를 최소화하가 위해
	* 더큰 unified I$/D$ 는 더 느릴 것이고, 2nd port even slower
	* Optimize I$ to fetch multiple instructions. No writes

* Unified L2, L3 : instruction and data together
	*  To minimize %miss
	* Fewer capacity misses -> 사용하지 않는 인스트럭션 capacity는 데이터를 위해 이용된다.
	* More conflict misses : instruction/data conflicts
		* A much smaller effect in large caches
	* Instruction/data structural hazards are rare -> simultaneous I$/D$ miss


## Replacement Policy
* If an empty space available
	* use the empty block space

* If not, evict(쫓아내다) an existing one
	* Random
	* FIFO (first-in first-out)
	* LRU (least recently used) -> 소프트웨어에서 주로 사용, 하드웨어는 cost로 인해 사용 X
	* Pseudo LRU : purer LRU is hard to implement with high associative caches


## Capacity and Performance
* Simplest way to reduce %miss : increase capacity
	* 장점 : Miss rate decreases monotonically
		* **Working set** : insns/data program is actively using

	* 	단점 :t hit 이 즈악한다
		* 캐시 사이즈가 증가할수록 latency가 증가한다.
		
## Effect of Cache Block Size
* Large blocks
	* 장점 :  Prefetching effects  - exploit spatial locality (reduce compulsory misses)
	* 장점 : tag storage overhead가 약간 감소하다.
	* 단점 : Capacity waste -> if small portion of blocks are actually needed
	* Miss latency : next-level memory hierarchy에서 버스 폭에 좌우됨

## Cache Conflicts
* 	두 개의 변수가 자주 접근해야 되는데 index가 같은 경우
	* 캐쉬에서 계속 충돌이 발생한다
	* 한번에 두 개의 캐쉬를 가질 수 없다
	* 많은 miss로 이어진다.

* Conflicts increase cache miss rate
	* 최악, 퍼포먼스 허락
	* Small program change -> changes memory layout -> changes cache mapping of variables -> dramatically increase/decrease conflicts


## Associativity
* Set-associativity : Two more possible locations for each address
	* Conflict 감소
	* hit latency 증가
![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/23BB7E8D-B231-4F29-AD4F-083EFA115C6C.png)


## Associative Cache Example
![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/DEF062E9-6AAA-4200-9FFE-42AD0343456E.png)


![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/C9473406-E0E7-4CF6-B67A-1F0D0FD5C252.png)

![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/03A19C4D-714A-4B3C-AC8F-EB91CF651DA2.png)

![](Ch11_Memory%20Hierarchy%20Basics%20%20Cache/A2A1DC5D-6958-49E5-B170-1F7F11CED295.png)


## Handling Cache Writes
* When to propagate new value to (lower level) memory?

* Option # 1 : write-through : immediately
	* Update next level of cache (or memory) for every write
	* If cache hit, update cache too
	* May generate many small writes to the same block
	* Can use coalescing buffers to reduce write traffics in the next level cache
	
* Option #2 : Write-back : when block is replaced
	* Requires additional “dirty” bit per block
		* Replace clean block : no extra traffic
		* Replace dirty block : extra “writeback” of block

## Inclusive vs Exclusive Caches
* Inclusive Caches (completely inclusive)
	* If a block A is in L1, -> A must be in L2
	* On a L1 miss, both caches are filled
	* an eviction Orr invalidation of a block in L2 -> probe L1 and invalidate the block in L1
	* Make sense only when L2 is much bigger than L1

* Exclusive Caches (completely exclusive)
	* A block can exist only one-level of cache at a time
	* On a L1 miss, only L1 cache is filled
	* On a L1 eviction, the victim block goes to L2
	* L2 becomes the victim buffer of L1
	* Can save capacity with no duplicate copies across levels of caches

* Less strict inclusivity or exclusivity (mostly inclusive caches?)

## Summary 












#컴퓨터구조