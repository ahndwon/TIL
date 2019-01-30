# Ch3_Git_Branch
## 브랜치란?
Git -> commit -> 현 Staging Area에 있는 데이터의 스냅샷에 대한 포인터, 메타데이터, 커밋 개체를 저장

* Git 브랜치 - 커밋 사이를 가볍게 이동할 수 있는 어떤 포인터
* 어떤 한 커밋을 가리키는 40글자의 SHA-1 체크섬 파일에 불과 
* Git은 기본적으로 master 브랜치를 만듬 -> 최초 커밋시 master 브랜치 생성
* 커밋을 만들 때 마다 브랜치가 자동으로 가장 마지막 커밋을 가리킨다.


### branch 생성
새로 만든 브랜치도 지금 작업하고 있던 마지막 커밋을 가리킴
`$ git branch [branch_name]`
 브랜치를 만들기만 하고 브랜치를 옮기진 않는다.


### Git HEAD 
지금 작업하는 로컬 브랜치를 가리키는 특수 포인터

Git branch 명령은 브랜치를 만들기만 하고 브랜치를 옮기진 않는다.

git log 명령에 —decorate 옵션을 사용하면 쉽게 브랜치가 어떤 커밋을 가리키는지도 확인할 수 있다. 
`$ git log --oneline --decorate`


### 브랜치 이동하기
$ git checkout 명령으로 다른 브랜치로 이동할 수 있다.
`$ git checkout testing`

### 브랜치를 이동하면 워킹 디렉토리의 파일이 변경된다
브랜치를 이동하면 워킹 디렉토리의 파일이 변경된다는 점을 기억해두어야 한다. 이 전에 작업했던 브랜치로 이동하면 워킹 디렉토리의 파일은 그 브랜치에서 가장 마지 막으로 했던 작업 내용으로 변경된다. 파일 변경시 문제가 있어 브랜치를 이동시키 는게 불가능한 경우 Git은 브랜치 이동 명령을 수행하지 않는다. 

$ git log로 현재 브랜치가 가리키고 있는 히스토리가 무엇이고 어떻게 갈라져 나왔는지 볼 수 있다.
`$ git log —oneline —decorate —graph —all `

다른 버전 관리 도구는 브랜치를 만들어야 하면 프로젝트를 통째로 복사해야 한다.


## 브랜치를 사용하는 상황
1. 작업중인 웹사이트가 있다.
2. 새로운 이슈를 처리할 새 Branch를 하나 생성한다.
3. 새로 만든 Branch에서 작업을 진행한다.

이때 중요한 문제가 생겨서 그것을 해결하는 Hotfix를 먼저 만들어야 한다.
그러면 아래와 같이 할 수 있다.

1. 새로운 이슈를 처리하기 이전의 운영(Production) 브랜치로 이동한다.
2. Hotfix 브랜치를 새로 하나 생성한다.
3. 수정한 Hotfix 테스트를 마치고 운영 브랜치로 Merge한다.
4. 다시 작업하던 브랜치로 옮겨가서 하던 일 진행한다.

### 브랜치의 기초
* 브랜치를 새로 만들면서 Checkout까지 한번에
`$ git checkout -b [branch_name]`
위 명령은 아래를 줄여놓은것
```
$ git branch iss53
$ git checkout iss53
```


아직 커밋하지 않은 파 일이 Checkout 할 브랜치와 충돌 나면 브랜치를 변경할 수 없다. 브랜치를 변경할 때에는 워킹 디렉토리를 정리하는 것이 좋다. 

```
$ git checkout -b hotfix
Switched to a new branch 'hotfix'
$ vim index.html // 파일 수정
$ git commit -a -m 'fixed the broken email address' 

// 수정사항을 master 브랜치에 반영
$ git checkout master
$ git merge hotfix

// 더 이상 필요가 없어진 branch 삭제
$ git branch -d hotfix
```


### Fast-Forward Merge
A 브랜치에서 다른 B 브랜치를 Merge 할 때 B가 A 이후의 커밋을 가리키고 있으면 A가B의 커밋을 가리키게함


### Recursive Strategy
```
$ git checkout master
Switched to branch 'master'
$ git merge iss53
Merge made by the 'recursive' strategy. README | 1 +
1 file changed, 1 insertion(+) 
```

![](Ch3_Git_Branch/page88image20965536.png) 

현재 브랜치가 가리키는 커밋이 Merge 할 브랜치의 조상이 아니므로 Git은 *Fast-forward*로 Merge 하지 않는다.
이 경우에는 Git은 각 브랜치가 가리키는 커밋 두개와 공통 조상 하나를 사용하여 3-way Merge를 한다. 


![](Ch3_Git_Branch/page88image20968448.png) 

Git은 Merge 하는데 필요한 최적의 공통 조상을 자동으로 찾음.


## 충돌의 기초
Merge 하는 두 브랜치에서 같은 파일의 한 부분을 동시에 수정했으면 3-way Merge가 실패한다. -> 충돌 발생

`$ git mergetool`을 치면 사용가능한 tool들이 나열됨


`$ git branch` - 브랜치의 목록을 보여줌
`$ git branch -v` -  브랜치마다 마지막 커밋 메시지도 보여줌
`$ git branch --merged` - 이미 merge한 브랜치 목록만 보여줌
`$ git branch --no-merged` - merge하지 않은 브랜치 목록만 보여줌

**아직 merge 하지 않은 커밋을 담고 있는 브랜치는 삭제가 안됨**



## Branch Workflow
### Long-Running 브랜치
![](Ch3_Git_Branch/page93image20536848.png) 

개발 브랜치는 공격적으로 히스토리를 만들어 나아가고 안정 브랜치는 이미 만든 히스토리를 뒤따르며 나아감

* master 브랜치 - 배포했거나 배포할 코드만 master 브랜치에 Merge해서 안정 버전의 코드만 master 브랜치에 둠
* develop 브랜치 - 개발을 진행하고 안정화하는 브랜치는 develop이나 next라는 이름으로 추가로 만들어서 사용 -> 안정화되면 master 브랜치에 merge
* 규모가 크고 복잡한 프로젝트일수록 유용성이 큼



### 토픽 브랜치
* 어떤 한 가지 주제나 작업을 위해 만든 짧은 호흡의 브랜치.
* 보통 주제별로 브랜치를 만들고 각각은 독립돼 있기 때문에 매우 쉽게 컨텍스트 사이 오갈 수 있다.


### 리모트 브랜치
* 리모트 Refs - 리모트 저장소에 있는 포인터인 레퍼런스 , 리모트 저장소에 있는 브랜치, 태그 등등


$ git ls-remote로 모든 리모트 Refs 조회 가능
$ git remote show  - 모든 리모트 브랜치와 그 정보를 보여줌

**리모트 트래킹 브랜치** - 리모트 브랜치를 추적하는 브랜치
로컬에 있지만 움직일 수 없다. -> 리모트 브랜치를 따라서 자동을 움직임
**일종의 북마크** , 리모트 저장소에 마지막으로 연결했던 순간에 브랜치가 무슨 커밋을 가리키고 있었는지 나타냄

`$ git fetch origin` - 현재 로컬의 저장소가 갖고 있지 않은 새로운 정보가 있으면 모두 내려받고, 받은 데이터를 로컬 저장소에 업데이트하고 나서, origin/master 포인터의 위치를 최신 커밋으로 이동 


### Push하기
로컬의 브랜치를 서버로 전송하려면 쓰기 권한이 있는 리모트 저장소에 Push 해야함.
로컬 저장소의 브랜치는 자동으로 리모트 저장소로 전송되지 않음.

`$ git fetch origin` - 브랜치가 생기는 것이 아니라 그저 수정 못 하는 origin/serverfix 브랜치 포인터가 생기는 것 

`git merge origin/serverfix`  - 새로 받은 브랜치의 내용을 Merge

`$ git checkout -b serverfix origin/serverfix` - Merge 하지 않고 리모트 트래킹 브랜치에서 시작하는 새 브 랜치를 만들려면 아래와 같은 명령을 사용한다. 


### 브랜치 추적
 리모트 트래킹 브랜치를 로컬 브랜치로 Checkout -> **트래킹 브랜치 생성됨(Upstream 브랜치)**

간단히 트래킹 브랜치  생성
`$ git checkout -b [branch] [remotename]/[branch]`

이미 로컬에 존재하는 브랜치가 리모트의 특정 브랜치를 추적
`$ git branch -u origin/serverfix`

현재 추적 브랜치 설정 보기
`$ git branch -vv`


### Pull 하기
$ git pull -> git fetch + git merge


### 리모트 브랜치 삭제
push하면서 리모트 브랜치 삭제
`$ git push origin --delete serverfix`





## Rebase
Git 에서 브랜치 합치는 방법 두가지
1. Merge
2. Rebase

### Rebase의 기초
**Rebase** - 변경 사항을 Patch로 만들고 이를 적용시키는 방법
Rebase 명령으로 한 브랜치에서 변경된 사항을 다른 브랜치에 적용 가능

![](Ch3_Git_Branch/page107image20518176.png) 

![](Ch3_Git_Branch/page108image20629952.png) 

![](Ch3_Git_Branch/page108image20637232.png) 



### Merge와 Rebase의 차이
* Merge 이든 Rebase 든 둘 다 합치는 관점에서는 서로 다 를 게 없다. 
* 하지만, Rebase가 좀 더 깨끗한 히스토리를 만든다. Rebase 한 브 랜치의 Log를 살펴보면 히스토리가 선형이다. 
* 일을 병렬로 동시에 진행해도 Rebase 하고 나면 모든 작업이 차례대로 수행된 것처럼 보인다. 

* Rebase를 하든지, Merge를 하든지 최종 결과물은 같고 커밋 히스토리만 다 르다는 것이 중요하다. 
* Rebase 의 경우는 브랜치의 변경사항을 순서대로 다른 브랜치에 적용하면서 합치고 Merge 의 경우는 두 브랜치의 최종결과만을 가 지고 합친다. 



**Merge시 결과**
![](Ch3_Git_Branch/page88image20968448.png) 

**Rebase시 결과**
![](Ch3_Git_Branch/page108image20637232.png)


Rebase 하고 나면 프로젝트 관리자는 어떠한 통합작업도 필요 없다. 그냥 master 브랜치를 Fast-forward 시키면 된다. 


### Rebase 활용
![](Ch3_Git_Branch/page109image20659648.png) 

테스트가 덜 된 server 브랜치는 그대로 두고 client 브랜치만 master로 합치려는 상황
server와는 아무 관련이 없는 client 커밋은 C8, C9
—onto 옵션 사용

`$ git rebase --onto master server client`
![](Ch3_Git_Branch/page110image20970528.png) 

`$ git rebase master server `
![](Ch3_Git_Branch/page111image20542256.png) 

```
$ git checkout master
$ git merge server
$ git branch -d client
$ git branch -d server
```

![](Ch3_Git_Branch/page111image20535600.png) 


### Rebase의 위험성
**이미 공개 저장소에 Push한 커밋을 Rebase하지 마라**

Rebase는 기존의 커밋을 그대로 사용하는 것이 아니라 내용은 같지만 다른 커밋을 새로 만듬. 
새 커밋을 서버에 Push -> 동료가 Pull 해서 작업 -> 내가 git rebase로 push -> 동료 Merge하고 Push -> 내가 Pull -> 코드 충돌

`$ git pull —rebase ` 로 미연에 방지 가능


### Rebase vs Merge
**히스토리**
	* 작업한 내용의 기록
	* 프로젝트가 어떻게 진행되었나에 대한 이야기 

로컬 브랜치에서 작업할 때는 히스토리를 정리하기 위해서 Rebase 할 수도 있지만, Push로 리모트에든 밖으로 내보낸 커밋에 대해서는 **절대 Rebase 하지 말아야 함**!







#git/progit