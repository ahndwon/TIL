# Linux 명령어

하드 디스크(비 휘발성) vs 메모리(휘발성)
    파일을 효과적으로 저장하기 위해서 구조화된 형태 - 파일 시스템
    파일 시스템은 OS의 종류에 따라 다르다.
    Windows: NTFS, FAT
    macOS: HFS, APFS, NTFS(read)
    Linux: ext2, 3, 4, xfs, ...
           NTFS, FAT, HFS  

Unix - Root File System
 _dev_sda
 _dev_sdb
              /
 /data /etc /usr /sbin /bin /proc /dev ..
  /sdb           /sda

Windows
   C:\
   D:\


prompt
` # (root permission) = rooting`
` $(user permission) `


* su    : Switch User
* sudo  : 사용자를 전환하지 않고, 명령을 실행할 수 있다.
* file  : 파일의 종류를 확인할 수 있다.
* chmod : 파일의 권한을 변경한다.
   => 파일의 권한은 파일의 소유자만 변경 가능하다.
* cd: 현재 작업 디렉토리를 변경한다.
* pwd: 현재 작업 디렉토리를 확인한다.
* mount: 현재 설치되어 있는 하드 디스크의 종류를 확인할 수 있다.
* du: disk usage
* df: 디스크의 남은 사이즈를 확인할 수 있다.
* cat: 파일의 내용을 확인한다.
* more: 데이터를 페이지 단위로 처리해준다.
* tail: 파일의 뒷부분을 보는 명령
 ex) tail -f logfile
  :  파일이 실시간으로 업데이트 되는 것을 확인할 수 있다.

* head: 파일의 앞부분을 보는 명령

### Hard link
 $ ln <일반 파일> <링크 파일 명>
 : 동일한 데이터를 가르킨다.
   둘 중 하나의 내용을 변경하면, 내용이 둘 다 변경되어 있다.

 한계)
	1. 디렉토리에 대한 링크는 생성할 수 없다.
     => 모든 사용자는 디렉토리에 대한 하드 링크는 만들 수 없지만, 시스템은 두 개의 하드링크를
        디렉토리에 대해 생성한다. (., ..)
	2. 다른 파일 시스템에서는 하드링크를 연결할 수 없다.
      /     - _dev_sda1
      _data - /dev_sdb1

###  Symbolic link
   : 같은 데이터를 가르키는 것이 아니라, 파일의 경로를 저장한다.
   ln -s _data_hello hello





stdin: 키보드
stdout: 표준 출력 장치
stderr: 표준 에러 장치

Redirection
 $ ./a.out > file
 a.out의 표준 출력 결과를 file에 저장한다.
 $ node app.js > stdout 2> stderr
 표준 출력은 stdout에 저장하고, 표준 에러는 stderr에 저장한다.
 $ node app.js >& all
   : 표준 출력과 표준 에러를 all에 저장한다.

 $ node app.js >& _dev_null
   : 모든 출력을 무시하겠다.



숨김 파일: .으로 시작하는 모든 파일은 숨김 파일이다.

drwxr-xr-x 26 root root 4096 11월  9 14:12 linux
디렉토리
 directory entry
   파일 이름 / ID(inode 번호)
   r: ls
   w: 파일 생성 / 삭제
   x: cd

## 파일의 종류(7가지)
* d: 디렉토리
* -: 일반 파일
* l: link
* s: socket
* c: 문자 디바이스
* b: 블록 디바이스
* p: 파이프

    user   group  other
    {rw-}  {r--}  {r--}
2진수 100 000 000
8진수   2   0   0

chmod u-w aaa






### 소유자 권한 / 그룹 권한 / Other 권한
* read: 읽기
* write: 쓰기
* excute: 실행


Terminal - Client
  => 키보드

### Shell
  환경 변수
   => OS 셋팅 값
   SHELL, PATH, encoding

ourguide@hostname
_etc_hostname



sh
bash
zsh

환경 변수 전체를 확인하는 명령
 $ env


Linux
  바퀴를 재발명할 필요가 없다.

OS
  Windows  
Unix
  Mac      => GUI
           => Terminal
              CLI

  Linux    => GUI
              X Window
							* Gnome
							* KDE
           => Terminal
              CLI


Unix


#공통