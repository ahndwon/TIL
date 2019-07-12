# Ch5_Arithmetic_for_Computers
Bits are just bits
	
**Operation on integers**
	* Addition and subtraction
	* multiplication and division
	* dealing with overflow

**Floating-point real numbers**
	* Representation and operations

## Integer Addition
* 범위 밖일 경우 오버플로우 발생
	* 	+ve와 -ve 를 더할 시 오버플로우 X
	* +ve 두 개를 더할 시 : 결과 sign이 1 일 경우 오버플로우
	* 	- ve 두 개를 더할 시 : 결과 sign이 0일 경우 오버플로우

## Integer Subtraction
* Add negation of second operand
* 범위 밖일 경우 오버플로우 발생
	* 	**두개**의 +ve와  **두개**의 -ve 를 **뺄** 시 오버플로우 X
	* +ve 와 -ve를 뺄 시 : 결과 sign이 0 일 경우 오버플로우
	* 	- ve 와 +ve를 뺄 시 : 결과 sign이 1일 경우 오버플로우


## MIPS Divistion
* HI/LO 레지스터들을 결과를 위해 사용한다.
* Instructions
![](Ch5_Arithmetic_for_Computers/5286FA76-4DD8-4A5F-B603-6BC3A29B6039.png)
	* 오버플로우 X, 0으로 나누기를 체크해주지 않음
		* 소프트웨어가 체크를 해야된다
	* mfhi, mflo를 사용해서 결과에 접근한다.


## Floating Point
* 비 정수형 숫자들을 나타냄

![](Ch5_Arithmetic_for_Computers/BD99D2DA-C03C-4DB5-9C30-CBB13898978D.png)

![](Ch5_Arithmetic_for_Computers/B3EEFD90-6D3B-435C-9FF7-8F3BF827CE2A.png)




![](Ch5_Arithmetic_for_Computers/DA0F30F4-C934-477B-9097-CB8F517A34D0.png)
![](Ch5_Arithmetic_for_Computers/C5B2E174-227F-4CA9-AAF7-CFD893A9EC42.png)


![](Ch5_Arithmetic_for_Computers/3B00B07C-5382-4B3D-A4B9-81BE24984B92.png)


![](Ch5_Arithmetic_for_Computers/86B1547D-F396-4F4F-A6DE-2DD56DD9DDA8.png)









#컴퓨터구조