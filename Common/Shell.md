# Shell
## 쉘의 이해
* 쉘(shell)이란 키보드로 입력한 명령어를 운영체제에 전달하여 그 명령어를 실행하게 하는 프로그램을 말함

* 쉘은 기본적인 의미는 껍데기로 이는 사용자와 운영체제의 내부(커널) 사이의 인터페이스를 감싸는 층이기 때문에 그러한 이름이 붙여 졌음

* 쉘은 일반적으로 명령 줄과 그래픽 형의 두 종류로 분류됨

* 명령 줄 쉘은 운영 체제 상에서 명령 줄 인터페이스(CLI)를 제공하는 반면에, 그래픽 쉘은 그래픽 사용자 인터페이스(GUI)를 제공함

### Bash
* Bash란 Bourne Again Shell 의 **스티브 본**(Steve Bourne)이 개발한 최초 유닉스 쉘 프로그램인 sh의 확장판 

* 1989년 발표되어 GNU 운영 체제와 리눅스 등의 운영 체제에 기본 쉘로 탑재됨 

### 쉘 프롬프트
* 쉘 프롬프트(prompt)란 커맨드 라인 인터페이스를 말함

* 일반적으로 커맨드 프롬프트 라인은 아래의 형식을 갖음

* 사용자@호스트이름:(작업 중인)디렉토리전체경로명$ 예) susan@ubuntu:/bin$
* 
* 프롬프트의 마지막 글자가 $가 아니라 \#일 경우, 이는 관리자 권한(root)을 가진 사용자로 로그인했다는 것을 의미


## *파일 시스템* 
### *파일 시스템*
* 파일 시스템(file system)이란 컴퓨터에서 파일이나 자료를 쉽게 발견 및 접근할 수 있도록 보관 또는 조직하는 체제를 의미함

* 리눅스에서 일반적으로 사용되는 파일 시스템은 ext(extended file system, 확장 파일 시스템)로 스테펜 트위디(Stephen Tweedie)가 제작하여 1992년 4월에 배포함

* 종류에는 ext, ext2, ext3, ext4가 있음

### 파일 시스템 구조
* 계층적인 디렉토리 구조로 파일을 구성함

* 트리 형식으로 디렉토리(윈도우즈에서는 폴더라고 함)를 구성하고 각 디렉토리에는 파일이나 다른 디렉토리가 포함될 수 있음

* 파일 시스템의 최상위 디렉토리를 루트(root) 디렉토리라고 하며 이 역시 파일들과 하위 디렉토리들을 포함하고 있고 하위 디렉토리 역시 디렉토리들과 파일들을 가지고 있음

* 윈도우즈와는 달리 리눅스는 시스템에서는 단일 파일 시스템으로 관리함


## *쉘 스크립트*
쉘 명령어 파일 포맷 : .sh
해쉬뱅
`#1/bin/sh`
`#1/bin/bash`
`#1/bin/zsh`

Service(Server, Client)
       Logging
          |
          |
          |
          |

      CloudWatch
      Stackdriver     ---------> 분석 ---------> Firebase
                                                Facebook

                                                Datadog
                                                ES / LS


       Growth Marketing
(Performance)

    Facebook 광고
    Play Store
    인스타그램
    네이버
    Homepage(랜딩 페이지)
        유입                     전환


 Linux: Package Manager
    : 라이브러리나 도구를 설치하는 방법

1. 소스를 다운 받아서 직접 빌드해서 설치.
    문제점: 의존성을 해결하기 어렵다.

2. Package Manager를 사용하면 쉽게 의존성을 해결할 수 있다.
    Debian, Ubuntu: apt, apt-get
    Redhat, Amazon Linux: yum
    macOS: port, brew

 Shell 명령어
   내장 명령어: 쉘이 지원하는 명령(cd)
   외장 명령어: cp, grep, rm


###  Signal
    Linux에서 Process에게 전달하는 명령어 집합
    $ kill -l

###  VPN(Virtual Private Network)
 원격 근무
  192.168.1.1 - iptime

###  gzip / gunzip
   : 파일 하나에 대한 압축만 수행된다.
 
 tar: 여러 개의 파일을 하나로 묶어준다.
   tar cvf test.tar *
   gzip test.tar        -> test.tar.gz
     $ tar cvfz test.tar.gz *

   bzip2 test.tar       -> test.tar.bz2
     $ tar cvfj test.tar.bz2 *

   xz
     $ xz test.tar



#공통