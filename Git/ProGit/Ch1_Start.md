# Ch1_Start
## 버전 관리
* 버전 관리 (Version Control)  - 피알 변화를 시간에 따라 기록했다가 나중에 특정 시점의 버전을 다시 꺼내올 수 있는 시스템.


## 로컬 버전 관리
로컬에 데이터베이스를 통해 파일의 변경 정보를 관리
Mac OS X를 사용할 경우 RCS라는 VCS가 포함됨
![](Ch1_Start/url.png)
[VCS](https://git-scm.com/book/en/v2/images/local.png)


## 중앙집중식 버전 관리
다른 개발자와 협업을 할 경우를 위해 만들어진 VCS. 
파일을 관리하는 서버가 별도로 있고 
클라이언트가 중앙 서버에서 파일을 받아서 사용(Checkout)한다.

![](Ch1_Start/url.png)
[CVCS](https://git-scm.com/book/en/v2/images/centralized.png)

**장점** 
* 모두 누가 무엇을 하는지 확인 가능
- 관리자는 누가 무엇을 할지 관리 가능

**단점**
- 중앙 서버에 문제가 생길 경우 치명적(데이터베이스 및 히스토리 손실)
- 로컬 VCS도 같은 문제 존재


## 분산 버전 관리 시스템
Distributed Version Control System
예 - Git, Mecurial, Bazaar, Darcs 

단순히 파일의 마지막 스냅샷을 Checkout하지 않고 저장소를 전부 복제한다.
서버에 문제가 생기면 이 복제물로 다시 작업을 시작할 수 있다. 
클라이언트 중에서 아무거나 골라도 서버 복원 가능
모든 Checkout은 모든 데이터를 가진 진정한 백업

![](Ch1_Start/url.jpg)

[DVCS](http://farm5.staticflickr.com/4688/39158569672_475b32fd47_b.jpg)


## Git 역사
Linux 개발 커뮤니티가 사용하던 상용 DVCS와 관계 틀어지면서 커뮤니티가 직접 개발.초기 Git의 목표는 다음과 같다.
* 빠른 속도
* 단순한 구조
* 비선형적인 개발 (수천 개의 동시 다발적인 브랜치)
* 완벽한 분산
* Linux 커널 같은 대형 프로젝트에도 유용할 것


### Git 기초
**다른 VCS**
- 각 파일의 변화를 시간순으로 관리하면서 파일들의 집합을 관리한다.

**Git**
* 데이터를 파일 시스템 스냅샷으로 취급하고 크기가 아주 작다.
* Git은 커밋하거나 프로젝트의 상태를 저장할 때마다 파일이 존재하는 순간을 중요하게 여긴다.
* 파일이 달라지지 않으면 성능을 위해서 파일을 새로 저장하지 않는다.
* 단지 이전 상태의 파일에 대한 링크만 저장함
* 데이터를 **스냅샷의 스트림**처럼 취급함


### 거의 모든 명령을 로컬에서 실행
웬만한 모든 명령들은 로컬 파일과 데이터만 사용하기 때문에 네트워크에 있는 다른 컴퓨터는 필요가 없다.
오프라인 상태여도 Commit을 할 수 있으며 네트워크 상태에 따라 속도도 영향을 받지 않는다.

### Git의 무결성
**Checksum**
* Git에서 사용하는 가장 기본적인(Atomic) 데이터 단위이자 Git의 기본 철학
* Git이 없이는 Checksum을 다룰 수 없다 -> 파일의 상태도 알 수 없고 데이터도 잃을 수 있다.
* Sha-1 해시를통해 체크섬을 만듬 (40자 길이의 16진수 문자열)


## Git은 데이터를 추가한다
Git으로 무얼 해도 Git 데이터베이스에 데이터가 추가됨 -> 되돌리거나 데이터 삭제 불가능
다른 VCS처럼 Git도 커밋하지 않으면 변경사항을 잃어버림 -> 하지만 일단 스냅샷을 커밋하고 나면 다른 데이터를 잃어버리기 어려움


## 세 가지 상태
1. Committed
	- 데이터가 로컬 데이터베이스에 안전하게 저장됐다는 것을 의미
2. Modified
	* 수정한 파일을 아직 로컬 데이터베이스 커밋하지 않은 것을 의미
3. Staged
	* 현재 수정한 파일을 곧 커밋할 것이라고 표시한 상태

![](Ch1_Start/url.png)
[git](https://tlsdmstn56.github.io/assets/pro_git/ch1-figure-1-6.png)


**Git 디렉토리**
Git이 프로젝트의 메타데이터와 객체 데이터베이스를 저장하는 곳
GIt의 핵심. 다른 컴퓨터에 있는 저장소를 Clone 할 때 Git 디렉토리가 만들어진다.


**워킹 디렉토리**
- 프로젝트의 특정 버전을 Checkout 한 것
* Git 디렉토리는 지금 작업하는  디스크에 있고 그 디렉토리 안에 압축된 데이터베이스에서 파일을 가져와서 워킹 디렉토리를 만든다.

**Staging Area**
* Git 디렉토리에 존재함
* 단순한 파일이고 곧 커밋할 파일에 대한 정보를 저장한다.
* 종종 Index 라고 불리기도 하지만 Staging Area라는 명칭이 표준이 되어가고 있다.

**Git으로 하는 일**
1. 워킹 디렉토리에서 파일을 수정
2. Staging Area에서 파일을 Stage 해서 커밋할 스냅샷을 만듬
3. Staging Area에 있는 파일들을 커밋해서 Git 디렉토리에 영구적인 스냅샷으로 저장한다.

Git 디렉토리에 있는 파일 -> Committed 상태임.
파일을 수정하고 Staging Area에 추가 -> Staged
Checkout 하고 나서 수정했지만 아직 Staging Area에 추가하지 않았다면 -> Modified


## Git 설치
### Linux
```
// Fedora
$ sudo yum install git
// Ubuntu, Debian
$ sudo apt-get install git
```

### Mac
```
$ brew install git
```
혹은 git 웹사이트에서 설치 [Git](http://git-scm.com/download/mac)

## Window
git 웹사이트에서 설치 [Git](http://git-scm.com/download/mac)


## 소스코드로 설치하기
가장 최신 버전이 필요하면 소스코드로 설치하는게 좋음
바이너리 인스톨러는 업데이트가 약간 늦음

Git은 curl, lib, openssl, expat, libiconv를 필요로 함.
```
$ sudo yum install curl-devel expat-devel gettext-devel \ openssl-devel zlib-devel
$ sudo apt-get install libcurl4-gnutls-dev libexpat1-dev gettext \ libz-dev libssl-dev
```

다양한 형식의 문서를 위한 의존 패키지
```
$ sudo yum install asciidoc xmlto docbook2x
$ sudo apt-get install asciidoc xmlto docbook2x
```

최신 배포 버전  설치
```
$ tar -zxf git-1.9.1.tar.gz
$ cd git-1.9.1
$ make configure
$ ./configure --prefix=/usr
$ make all doc info
$ sudo make install install-doc install-html install-info
```


## Git 최초 설정
Git config 도구를 통해 설정 내용을 확인하고 변경 할 수 있다.
설정파일은 3 가지이다. 다음 설정들은 역순으로 우선시 된다. (프로젝트 수준 우선)

1. `/etc/gitconfig` 파일
	* 	시스템의 모든 사용자와 모든 저장소에 적용되는 설정.
	* `git config --system` 옵션으로 이 파일을 읽고 쓸 수 있다.

2. `~/.gitconfig`, `~/.config/git/config` 파일
	* 	특정 사용자에게만 적용되는 설정이다.
	* `git config --global` 옵션으로 이 파일을 읽고 쓸 수 있다.

3. `.git/config`
	* 이 파일은 Git 디렉토리에 있고 특정 저장소(혹은 현재 작업 중인 프로젝트)에만 적용된다.

### 사용자 정보
```
$ git config --global user.name "John Doe"
$ git config --global user.email johndoe@example.com
```


### 편집기
```
$ git config --global core.editor emacs
```

## 설정 확인
`git config —list`
Git은 같은 키를 여러 파일(`/etc/gitconfig`와 `~/.gitconfig` 같은) 에서 읽기때문에 같은 키가 여러 개 있을 수도 있다. 그러면 Git은 나중값을 사용한다. 










#git