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








#git/progit