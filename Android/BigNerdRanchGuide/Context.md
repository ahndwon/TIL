# 의존성 관리
1. Cocoapods
2. carthage
3. Swift Package Manager

iOS Library
	- Objective C
		-> Bridge가 제공되어야 사용 가능
	- Swift


### Android
- Retrofit + OkHttp + Gson 
 
### iOS
- AFNetworking(Alamofire) - Obj C (X)
- Alamofire - Swift

C/C++ : Library (함수 + 클래스) - 실행이 가능하지 않음
	- 정적 라이브러리
		- 소스에 라이브러리 자체를 포함시킴

	- 동적 라이브러리
		* 	필요 라이브러리가 없으면 프로그램 다운됨
		- 업데이트에 대해 용이함

## iOS에서의 프레임워크
Library + Header + Etc
	=> Framework

기존 프레임워크 의미 - 코드의 흐름이 존재

Library math.library
	- static library
 	prog + math.library => a.out
	(2k)		(1k)		     => 3K

	- dynamic library
	prog + math.libary => a.out
	(2K) 			   => 2K


## Cocoapods 설치
프로젝트 디렉터리로 ㄱㄱ

Visual Studio
	Solution
		-Project
		-Project

Android Studio
	Project
		-Module
		-Module

Xcode
	Workspace(SampleApp.scworkspace)
		-Project(SampleApp.xcodeproj)
		-Project(Pods.xcodeproj)
		-Project(Pods)
			-Alamofire



Deployment Target - Minimum sdk


## CocoaPods 사용법
프로젝트에서
$ pod init
$ vi pod file
Deployment Target 수정




Android: Main Thread
					1. Activity
            Handler => runOnUiThread()
					2. RxAndroid
             AndroidScheduler.main
            
iOs*
					1. GCD Library (Grand Central Dispatch)
             DispatchQueue.main.async {}
					2. RxCocoa
             Main Thread Scheduler*

## 이미지 라이브러리
Android - Glide
iOS - Kingfisher










#iOS