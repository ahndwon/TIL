# Ch2_Git_Basic
## Git 저장소 만들기
###  기존 디렉토리를 Git 저장소로 만들기
`$ git init`
.git 이라는 하위 디렉토리를 만든다. 
.git 디렉토리에는 저장소에 필요한 뼈대 파일(skeleton)이 들어있다.

Git이 파일을 관리하게 하려면 저장소에 파일을 추가하고 커밋해야함
```
$ git add *.c
$ git add LICENSE
$ git commit -m 'initial project version'
```


### 기존 저장소 Clone
`$ git clone [url]`
이 명령은  디렉토리를 만들고 그 안에 .git 디렉토리를 만든다.
디렉토리로 이동하면  Checkout으로 생성한

디렉토리 이름을 지정하고 싶으면
`$ git clone [url] [directory] `

Git은 https 프로토콜 뿐만 아니라 `git://`  , ssh  `user@server:path/to/repo.git`도 사용 가능


### 수정하고 저장소에 저장하기
워킹 디렉토리의 모든 파일은 두가지로 나뉜다
* Tracked(관리대상) - 이미 스냅샷에 포함돼 있던 파일
	* Unmodified(수정하지 않음)	
	* Modified(수정함)
	* Staged(커밋으로 저장소에 기록할)
* Untracked(관리대상 X) - 스냅샷에도 Staging Area에도 포함되지 않은 파일

![](Ch2_Git_Basic/F594A849-4E1F-4C47-BC18-E3C8DCD89D96.png)
[파일의 라이프사이클](https://tlsdmstn56.github.io/assets/pro_git/ch2-figure-2-1.png)

### 파일의 상태 확인하기
다음 명령어로 파일 상태 확인 가능
Tracked, Untracked 이든 상관없이 상태를 보여준다.
```
$ git status
```


### 파일 추가하기
```
$ git add README

// 추가됬는지 확인
$ git status
```


### Modified 상태의 파일을 Stage
이미 Tracked 상태인 파일을 수정하는 법
Tracked된 상태이지만 Staged 상태가 아닐 경우 `git add` 를 해주면 된Staged 된 상태인 것을 수정하면 수정하기 전 상태만 Staged 이고 수정된 후는 Unstaged 상태다. -> git add 명령을 실행하면 파일을 바로  Staged 상태로 만들고 git commit 명령을 실행하면 git add를 한 시점의 파일을 커밋한다.


### 파일 상태를 짤막하게 확인하기
`$ git status -s`
혹은
`$ git status --short`

* ?? - 아직 추적하지 않는 새 파일은 앞에 ??표시가 붙는다.
* A - Staged 상태로 추가한 파일 중 새로 생성한 파일
* M - 수정한 파일
* MM - 변경하고 Stage 상태이면서 Unstaged 상태인 파일


### 파일 무시하기
Git이 관리할 필요가 없는 파일은 .gitignore 파일을 만들고 그 안에 무시할 파일 패턴을 적으면 된다.

`$ cat .gitignore`

**.gitignore 입력 패턴**
* 아무것도 없는 라인이나, ‘#'로 시작하는 라인은 무시한다.
* 표준 Glob 패턴을 사용한다.
* 슬래시(/)로 시작하면 하위 디렉토리에 적용되지(Recursivity) 않는다.
* 디렉토리는 슬래시(/)를 끝에 사용하는 것으로 표현한다.
* 느낌표(!)로 시작하는 패턴의 파일은 무시하지 않는다. 

**Glob 패턴**
* 정규표현식을 단순하게 만든 것

```
# 확장자가 .a인 파일 무시 
*.a 

# 윗 라인에서 확장자가 .a인 파일은 무시하게 했지만 lib.a는 무시하지 않음 
!lib.a 

# 현재 디렉토리에 있는 TODO파일은 무시하고 subdir/TODO처럼 하위디렉토리에 있는 파일은
 /TODO 

# build/ 디렉토리에 있는 모든 파일은 무시 
build/ 

# doc/notes.txt 파일은 무시하고 doc/server/arch.txt 파일은 무시하지 않음 
doc/*.txt 

# doc 디렉토리 아래의 모든 .txt 파일을 무시 
doc/**/*.txt 

```


### Staged와 Unstaged 상태의 변경 내용을 보기
단순히 파일이 변경됐다는 사실이 아니라 어떤 내용이 변경됐는지 살펴보기엔 git status 명령이 아니라 git diff 명령을 사용해야 한다. 

* git status - 수정했지만, 아직  Staged 파일이 아닌것과 어떤 파일이 Staged 상태인지 볼려고 할때
* git diff - 어떤 라인을 추가했고 삭제했는지 궁금할때

Git status 를 했을때
```
$ git status
On branch master Changes to be committed: 
      (use "git reset HEAD <file>..." to unstage)
        new file:   README
    Changes not staged for commit:
      (use "git add <file>..." to update what will be committed)
      (use "git checkout -- <file>..." to discard changes in working directory)
        modified:   benchmarks.rb
```


Git diff 명령을 실행하면 수정했지만 아직 staged 상태가 아닌 파일을 비교해 볼 수 있다.
**워킹 데릭토리에 있는 것과 Staging Area에 있는 것을 비교함.** -> 수정하고 아직 Stage하지 않은 것을 보여줌

```
$ git diff
diff --git a/benchmarks.rb b/benchmarks.rb index 3cb747f..e445e28 100644
--- a/benchmarks.rb
+++ b/benchmarks.rb
@@ -36,6 +36,10 @@ def main 
+ + + + 
  @commit.parents[0].parents[0].parents[0]
end
run_code(x, 'commits 1') do
  git.commits.size
end 
run_code(x, 'commits 2') do
  log = git.commits('master', 15)
  log.size

```


만약 커밋하려고 Staging Area에 넣은 파일의 변경 부분을 보고 싶으면 `git diff -- cached` 옵션을 사용한다. 이 명령은 저장소에 커밋한 것과 Staging Area에 있는 것을 비교한다.


`git diff --cached`
`git diff --staged`
둘다 동일


### 변경 사항 커밋하기
Unstaged 상태의 파일은 커밋되지 않는다.
Git은 생성하거나 수정하고 나서 git add 명령으로 추가하지 않은 파일은 커밋하지 않는다. 파일은 여전히 Modified 상태로 남아 있다. 커밋하기 전에 git status 명령으로 모든 것이 Staged 상태인지 확인할 수 있다. 그 후에 git commit을 실행하여 커밋한다.

`git commit` 을 하면 지정된 편집기가 열리면서 커밋 메시지를 작성해야 한다.

`git commit -v` - 편집기에 diff 메시지도 추가된다.
내용을 저장하고 편집기를 종료하면 Git은 입력된 내용으로 새 커밋을 완성한다.

`git commit -m "message"` - 메시지를 인라인으로 첨부


### Staging Area 생략하기
Staging Area는 커밋할 파일을 정리한다는 점에서 매우 유용하지만 복잡하기만 하고 필요하지 않은 때도 있다.

Git commit 명령을 실행할 때 -a 옵션을 추가하면 git은 Tracked 상태의 파일을 자동으로 Staging Area 넣으므로 git add 명령을 생략 가능

`$ git commit -a -m 'message'`

### 파일 삭제
Git 에서 `git rm`으로 Tracked 상태의 파일 을 삭제하면 Staging Area에서 삭제된다. 삭제하고 커밋을 해야 반영된다. 이 명령은 워킹 디렉토리에 있는 파일도 삭제하기 때문에 실제로 지워진다.

만약 파일을 그냥 삭제하고 git status로 상태를 확인하면 “Changes not staged for commit” -> 즉 Unstaged가 된다. -> git rm 을 해주면 삭제한 파일은 Staged 됨

Staging Area는 Index라고도 한다.
이미 파일을 수정했거나  Index(Staging Area)에 추가한 경우 -f 옵션을 통해 강제로 삭제해야 한다. -> 실수로 데치터 삭제를 못하게 하는 안전장치 -> 한번도 커밋하지 않은 데이터는 Git으로 복구할 수 없다.

#### Staging Area에서만 파일 삭제
.gitignore에 파일을 등록하는 것을 뺴먹었을 때 사용
 혹은 대용량 로그 파일이나 컴파일된 파일을 실수로 추가했을 때 사용

Staging Area에서만 파일을 제거하고 워킹 디렉토리에 있는 파일은 지우지 않는다.
`$ git rm --cached README`

여러 개의 파일이나 디렉토리를 한꺼번에 삭제 가능
`$ git rm log/\*.log`.  -  log/ 디렉토리에 있는 .log파일 모두 삭제
*앞에 \을 사용해야 한다. 파일명 확장 기능은 셀에만 있는 것이 아니라 Git 자체에도 있기 때`$ git rm \*~`  - ~로 끝나느 파일 모두 삭제
### 파일 이름 변경하기
Git은 파일 이름의 변경이나 파일의 이동을 명시적으로 관리하지 않는다.
Git의 mv 명령어로 파일을 움직이면 기존 파일을 git rm 하고 새로운 위치의 파일을 git add 한다.

파일 이동 명령어
`$ git mv file_from file_to`

**`git mv README.md README`의 내부적 동작**
```
$ mv README.md README
$ git rm README.md
$ git add README
```



### 커밋 히스토리 조회하기
`git log`를 통해 히스토리를 조회할 수 있다.
저장소의 커밋 히스토리를 시간순으로 보여줌 -> 가장 최근의 커밋이 먼저 나온다.

각 커밋의 SHA-1 체크섬, 저자 이름, 저자 이메일, 커밋 날짜, 커밋 메시지 표시함.

**옵션**
* -p : 각 커밋의 diff 결과를 보여준다. 
* -2 : 최근 두 개의 결과만 보여준다.
* —stat : 각 커밋의 통계 정보를 조회
* —graph : 브랜치와 머지 히스토리를 보여주는 아스키 그래프를 출력함
* —pretty : 히스토리 내용을 보여줄 때 기본 형식 이외에 여러 가지 중에 하나를 선택 가능한 옵션 
	* oneline : 각 커밋을 한 라인으로 보여줌
	* short
	* full
	* fuller
	* format : 사용자가 원하는 포맷으로 결과를 출력하게 해줌
```
$ git log --pretty=format:"%h - %an, %ar : %s"
ca82a6d - Scott Chacon, 6 years ago : changed the version number 085bb3b - Scott Chacon, 6 years ago : removed unnecessary test a11bef0 - Scott Chacon, 6 years ago : first commit 
```

**포맷 옵션들**
![](Ch2_Git_Basic/17B74E82-F46D-4888-B5B8-A44A71451A59.png)
[format option](http://2.bp.blogspot.com/-4n_S2jl1iRE/VojNMn02xPI/AAAAAAAAAQQ/Q7P3-RPnGq8/s1600/git_pretty_formats.png)


**Author** - 파일을 실제로 작성하거나 변경한 사람
**Committer** - 커밋을 한 사람

Example - 내가 어떤 프로젝트에 패치를 보냈고 그 프로젝트의 담당자가 패치를 적용했다면 내가 저자고 그 담당자가 커미터다. 

![](Ch2_Git_Basic/C2013ECC-3932-438B-864B-630D8338617D.png)


### 조회 제한조건
Git log의 조회 범위를 제한할 수 있다.
—since 나 —until로 시간을 기준으로도 조회할 수 있고
-<n>으로 최근 몇개만 조회할 수도 있다.

![](Ch2_Git_Basic/B11BD70C-CBE0-43E0-A39C-626BD3B3817A.png)



### 되돌리기
실수를 해서 복구하고 싶은 경우 되돌리기를 사용하면 된다.
하지만 되돌리긴 것을 복구 할 수는 없다.

**완료한 커밋 수정**
너무 일찍 커밋했거나 어떤 파일을 빼먹었을 때 그리고 커밋 메시지를 작못 적었을 때
`$ git commit --amend`
위 명령어는 Staging Area를 사용하여 커밋한다.
마지막 커밋과 수정한 것이 없다면 커밋 메시지만 수정하면됨

커밋을 했는데 Stage하는 것을 빠뜨린 파일은 다음과 같이 추가
```
$ git commit -m 'initial commit'
$ git add forgotten_file
$ git commit --amend
// 두 번째 커밋이 첫 번째 커밋을 덮어씀
```



### 파일 상태를 Unstage로 변경
이미 Staged 상태인 파일을 Unstaged로 바꾸기 위해선
`$ git reset <file>`을 해주면 된다.


### Modified 파일 되돌리기
수정한 파일 되돌리기
원래 파일을 덮어쓰기 때문에 수정한 내용 전부 사라짐
`$ git checkout -- <file>`


### 리모트 저장소 
리모트 저장소 - 인터넷이나 네트워크 어딘가에 있는 저장소
다른 사람들과 함께 일하는 것은 리모트 저장소를 관리하면서 데이터를 거기에 Push하고 Pull 하는 것.
리모트 저장소 관리 -> 저장소를 추가, 삭제, 브랜치를 관리, 추적을 의미


### 리모트 저장소 확인하기
`$ git remote`로 현재 프로젝트에 등록된 리모트 저장소 확인 가능
저장소를 Clone하면 origin이 자동으로 등록됨
-v 옵션을 추가하면 url도 출력됨


### 리모트 저장소 추가하기
`$ git remote add [단축이름] [url]`


### 리모트 저장소를 Pull 하거나 Fetch 하기
Fetch
`$ git fetch [remote-name]`
로컬에는 없지만, 리모트 저장소에 있는 데이터를 모두 가져옴.
리모트 저장소의 모든 브랜치를 로컬에서 접근할 수 있어서 언제든 Merge를 하거나 내용을 살펴볼 수 있다.
**자동으로 Merge가 되지 않음**  -> 수동으로 해야함.

`$ git pull` -> 로컬로 데이터를 가져오고 merge도 자동으루 수행


### 리모트 저장소에 Push
Clone한 리모트 저장소에 쓰기 권한이 있고, Clone하고 난 이후 아무도 Upstream 저장소에 Push 하지 않았을 때만 사용 가능.
`$ git push [리모트 저장소 이름] [브랜치 이름]`


### 리모트 저장소 살펴보기
`$ git remote show [리모트 저장소 이름]`
리모트 저장소의 URL과 추적하는 브랜치를 출력함. 
`git pull`을 실행할 때 master 브랜치와 Merge 할 브랜치가 무엇인지 보여줌.


### 리모트 저장소 이름 수정, 저장소 삭제
`$ git remote rename [기존 이름] [새 이름]`
리모트 저장소의 브랜치 이름도 바뀐다.

리모트 저장소 삭제
```
$ git remote rm paul
$ git remote
```


## 태그
### 태그 조회
`$ git tag` - 알파벳 순서로 태그를 보여줌
`$ git tag -l [패턴]` - 검색 패턴을 사용하여 태그 검색 가능

```
$ git tag -l 'v1.8.5*'
v1.8.5
v1.8.5-rc0
v1.8.5-rc1 
v1.8.5-rc2
v1.8.5-rc3
v1.8.5.1
```


### 태그 붙이기
**태그 두 종류**
* Lightweight 
	* 단순히 특정 커밋에 대한 포인터. 브랜치처럼 가리키는 지점을 최신 컷으로 이동시키지 않음 
* Annotated
	* Git 데이터베이스에 태그를 만든 사람의 이름
	* 이메일과 태그를 만든 날짜, 그리고 태그 메시지도 저장한다.
	* GPU (GNU Privacy Guard)로 서명할 수도 있다.
	* 모든 정보를 저장해둬야 할 때에만 Annotated 태그를 추천한다. 
	* 다른 정보를 저장하지 않는 단순한 태그는 Lightweight 가 낫다.


### Annotated 태그
`$ git tag -a [tagname] -m [commit message]`

Git show
`$ git show [tagname]`


### Lightweight Tags
Lightweight - 기본적으로 파일에 커밋 체크섬을 저장


### 나중에 태그하기
```
$ git log --pretty=oneline
9fceb02d0ae598e95dc970b74767f19372d61af8 updated rakefile 

$ git tag -a v1.2 9fceb02
```
특정 커밋에 태그하기 위해 명령의 끝네 커밋 체크섬을 명시해야함 (체크섬은 전부 말고 앞부분만 사용해도 됨)


### 태그 공유하기
Git push 명령은 자동으로 리모트 서버에 태그를 전송하지 않음
태그를 만들었으면 서버에 별도로 Push를 해야함.
`$ git push [remote] [tagname]`


### 태그 Checkout
태그는 브랜치와 달리 가리키는 커밋을 바꿀 수 없는 이름 -> Checkout해서 사용  불가
태그가 가리키는 특정 커밋 기반의 브랜치를 만들어 작업하려면 다음처럼 해야 한다.
`$ git checkout -b [branch] [tagname]`
이렇게 브랜치를 만든 후에 커밋하면 브랜치는 업데이트 되지만 태그가 가리키는 커밋이 변하지 않으므로 두 내용이 가리키는 커밋이 다르다.


### Git Alias
`git config` 에 각 명령의  Alias를 추가해주면 명령을 단축해서 실행할 수 있다.

**예**
```
$ git config --global alias.co checkout 
$ git config --global alias.br branch 
$ git config --global alias.ci commit 
$ git config --global alias.st status 
$ git config --global alias.unstage 'reset HEAD --' 
$ git config --global alias.last 'log -1 HEAD' 

// 외부 명령
$ git config --global alias.visual '!gitk' 


$ git unstage fileA
$ git reset HEAD fileA
```










#git/progit