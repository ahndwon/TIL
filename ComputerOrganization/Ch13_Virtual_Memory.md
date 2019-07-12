# Ch13_Virtual_Memory
##  Memory system in 1950’s
* lw R1, 4(R3) // R1 <- M[R3+4]

* EDSAC, early 50’s
	* Effective address = physical memory address

* Only one program ran at a time, with unrestricted access to entire machine (RAM + I/O devices)
	* Single-programmed execution environment


## Multiprogramming
* Motivation
	* 초기엔 I/O operations were slow and each word transfer involves the CPU
	* CPU , I/O of 2 more programs 이 overlapped되면 cpu throughput이 Higher
	* Why?
		* Multiprogramming

* Location Independent programs
	* Programming and storage management ease
		* 더 좋은 베이스 레지스터 필요
* Protection
	* Independent programs should not affect each other inadvertently
		* Need for a bound register


## Separate Areas for Program and Data 
![](Ch13_Virtual_Memory/4F3A7910-9DE7-4048-86C4-FCC911827336.png)


## Memory Fragmentation
Segmentation 의 단점 -> Fragmentation
![](Ch13_Virtual_Memory/9CF7E032-BB2C-457A-A271-7A163B1DF2D4.png)

* 유저가 왔다갔다 할수록 저장소는 “fragmented됨


## The Problem
	* 물리 메모리는 무한하지 않음
		* 프로그래머가 물리 메모리에 들어갈 코드와 데이터 사이즈에 대해 걱정해야 하나?
	* 	프로그래머가 두 개의 프로세스가 같은 물리 메모리를 안 쓰도록 해야하나.

## Difficulties 
![](Ch13_Virtual_Memory/EFD83F6C-5CC5-40D2-82A8-C2B9CFFE786E.png)


## Virtual Memory
**개념** : 프로그래머가 커더란 주소 공간을 갖고 있다고 생각하도록 함. 실제로는 작은 물리적 공간
	-> 따라서 프로그래머는 물리 메모리를 걱정할 조절할 필요가 없다.

* 프로그래머는 자신이 “무한한” 물리 주소 공간을 갖고 있다고 가정해도 된다.

* hardware and software cooperatively and automatically manage the physical memory space to provide the illusion
	* Illusion은 각 독립적 과정을 위해 유지된다.


## Virtual Memory
* Level of indirection
* 각 instruction으로 생성된 주소는 **가상 주소**(Virtual Addresses, VAs)들이다.
* 메모리는 **물리적 주소**(Physical Addresses, PAs)들로 접근된다.
* VAs 는 PAs로 거칠게(coarse granularity) 번역된다. (**Page**)
*  OS(운영체제)가 VA -> PA mapping 과 다른 프로세스들을 조절한다.

![](Ch13_Virtual_Memory/4D4C6171-ADDF-45C5-8EB4-896303F3CC00.png)


![](Ch13_Virtual_Memory/B96325B8-2449-4AA1-A1E3-8E3C4A949BE5.png)


## A System with Virtual Memory (Page based) 
![](Ch13_Virtual_Memory/206C1925-7167-47E8-860B-3B9459A6B45D.png)

* **Address Translation** : 운영체제가 관리하는 page table을 통해 하드웨어가  가상 주소를 물리 주소로 변경함

## Demand Paging
* 가상 페이지는 다음과 같이 대응된다
	* 페이지가 물리 메모리에 존재할 경우 -> 물리적 페이지
	* disk의 한 위치

* 만약 접근한 가상 페이지가 메모리에 없고 , 디스크에 존재할 경우
	* 가상 메모리 시스템은 페이즈를 물리적 페이지로 옮기고 매핑을 조정한다

* 페이지 테이블은 가상 페이지를 물리 페이지로 매핑한는걸 기록하고 있다.


## Physical Memory as a Cache
* 물리 메모리는 디스크에 저장된 페이지들을 위한 캐쉬이다
	* 모던 시스템에서 많이 캣쉬 형태로 쓰임 ( 가상 페이지는 어느 물리 프레임에옫 매핑 될 수 있다)

* 다음과 같은 캐쉬 문제들이 있따.
	* **Placement** : 메모리에서 어떻게 페이지를 **찾고 둘**(place/find) 것인가.
	* **Replacement** : 메모리에 공간을 만들기 위해 어떤 페이지를 **지울**(remove) 것인가
	* Granularity of management : large, small, uniform pages?
		-> 관리할 페이지의 입자 크기?

	* write policy : 쓰기에 대해서 어떻게 할 것인가. Write back?

## Supporting Virtual Memory
* 가상 메모리는 하드웨어와 소프트웨어 둘다 지원해줘야 한다.
	* Page table은 메모리에 존재한다.
	* TLB (Translation Lookaside Buffers)라는 특수 하드웨어 구조에 캐쉬될 수 있다.
	

* **MMU - Memory Management  Unit**
	* Page Table Base Register, TLBs, page walkers 포함

* 운영체제가 MMU를 영향을 끼쳐
	* 페이지 테이블을 채우고, 물리 메모리에서 무엇을 변경할지 결정한다.
	* Context switch가 일어날때 페이지 테이블 레지스터를 변경
	* page faults를 처리하고 알맞은 매핑을 보장

## Some OS Jobs for VM
* 어떤 물리 페이지들이 비어있는지 추적
* 비어있는 물리 페이지들을 가상 페이지로 할당하다
* 페이지 변경 정책
	* 빈 물리 페이지 없을 시 어떤 것을 삭제해야 하나
* 프로세스들 사이 페이지 공유
	* Copy-on-write optimization

## Page Fault : A Miss in Physical Memory
페이지가 물리 메모리가 아닌 **디스크**에 존재할 경우
	* 	페이지 테이블 엔트리는 페이지가 메모리에 없음을 나타냄
	* 해당 페이지에 접근 시 **Page Fault Exception**이 발생함
	* OS운영체제 트랩 핸들러는 디스크로부터 메모리로 데이터를 이동시킴
		* 다른 프로세스들은 계속 executing 될 수 있음
		* 운영체제는 placement에 대해 Full control을 가짐

![](Ch13_Virtual_Memory/DA65A301-0A2E-43A0-BE3D-AC8C71098D1B.png)



##  Servicing a Page Fault
1. OS가 I/O 요청을 함
	* 주소 X 에서 길이 P 만큼 읽고, 주소 Y 에서 읽어서 저장도 가능

2. 읽기 발생
	* 	Direct Memory Access (DMA)
	* Under Control of I/O controller

3. Controller signals completion
	1. 프로세서 인터럽트함
	2. 운영체제는 suspended 프로세스들을 재개함

![](Ch13_Virtual_Memory/A5B902C8-988D-4D56-878A-67AD5C11AFBC.png)



## Page Table is Per Process
* 각 프로세스는 자기만의 가상 주소 공간을 갖는다
	* 각 프로그램을 위한 FULL 주소 공간
	* 메모리 할당, 공유, 링킹, 로딩을 간단화함

## Address Translation
* ISA에 의해 페이지 사이즈가 정해져 있음
* 요즘 - 4KB, 2MB, 1GB in x86


## Address Translation
![](Ch13_Virtual_Memory/9A7ABFED-F6E8-49E7-9F60-E8410FB6670F.png)

* VA -> PA mapping -  address translation
	* VA를 virtual Page Number(**VPN**)랑 page offset으로 쪼갬
	* VPN을 page table을 통해 Physical Page Number(**PPN**)로 번역함


## Address Translation Example
![](Ch13_Virtual_Memory/96ECFA37-DC11-432D-AA14-9CB7ACEEB1E4.png)


## Address Translation : Page Hit
![](Ch13_Virtual_Memory/0D1FD5C9-8A58-43A2-9732-05B8F096DE62.png)


## Address Translation : Page Fault
![](Ch13_Virtual_Memory/39DD79C9-AE65-41E3-B5A6-8E8631943C8C.png)


## Page Size Trade Offs
**큰 페이지의 장점**
	* 	더욱 많은 가상 주소 공간을 가질 수 있따
	* coverage가 넓어지므로 TLB 미스를 줄일 수 있다
	* 더 많은 TLB 엔트리를 가질 수 있따
	* tagging으로 인한 HW 비용을 줄일 수 있다.

**큰 페이지의 단점**
	* 	한 페이지 내에서 공간 낭비 (internal fragmentation)
	* 물리 공간 전체에서 공간 낭비 (external fragmentation)
	* 디스크에서 메모리로 크기 이동


## Page-Level Access Control (Protection)
* 모든 프로세스가 모든 페이지에 대해 접근 할 수 있는게 아님
	* 시스템에 접근하기 위해선 supervisor level privilege가 필요하다.

* 개념 : 프로세스의 페이지 테이블 access control information을 페이지 basis로 저장

* translation과 동시에 access control 을 enforce

-> **Virtual memory system serves two functions today**
Address translation (for illusion of large physical memory)
Access control (protection)


## VM as a Tool for Memory Access Protection
* Extend Page Table Entries (PTEs) with permission bits
* Check bits on each access and during a page fault
	* If violated, generate exception

![](Ch13_Virtual_Memory/072CF367-739A-43A0-AB74-3CD45ED4A923.png)


## Three Major Issues
1.  페이지 테이블은 얼마나 크고 어떻게 저장을 하고 접근을 하는가
2. Translation & access control check를 어떻게 더 빠르게 할 것인가
3. 캐쉬 액세스와 관련해 언제 translation을 하는가


## Virtual Memory Issue 1
* 페이지 테이블이 얼마나 큰가?

* 어디에 저장하는가
	
* How can we store it efficiently without requiring physical memory that can store all page tables 
	* Idea : multi-level page tables
	* first level page table만 물리 메모리에 있어야 한다
	


## Issue : Page Table Size
* 메모리를 저장하기 위한 page tables은 클 수 있다
* 페이지 크기가 너무 커지는게 단점
* Observation 
	* Process may not be using the entire VM space
 

## Solution : Multi-level Page Table
![](Ch13_Virtual_Memory/36B69F87-6919-4634-B1C0-94A44573F48C.png)
페이지 테이블 크기가 작아지는게 장점


![](Ch13_Virtual_Memory/C8735030-4272-429D-A56B-BE69C97104B3.png)

![](Ch13_Virtual_Memory/8C536A0B-E1A6-41FB-ADDC-3F7DBDDBB3FF.png)

![](Ch13_Virtual_Memory/F60CC00A-AC24-42FC-9B1E-099948558BAF.png)



![](Ch13_Virtual_Memory/5AC424C7-1569-4315-94F1-5214BFE3D74D.png)







#컴퓨터구조