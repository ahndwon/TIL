# Ch5_distributed_environment
## 분산 환경에서의 Workflow
**분산형(git)**
* 구조가 유연 -> 여러 개발자가 함께 작업하기 좋음
* 각 개발자의 저장소가 하나의 노드 & 중앙 저장소
* 모든 개발자는 다른 개발자의 저장소에 일한 내용을 전송 가능
* 또는 자신의 저장소 위치를 다른 개발자들에게 공개 가능
* 프로젝트나 팀이 코드를 운영할 떄 다양한 Workflow를 만들 수 있도록 함.


**중앙집중형**
* 각 개발자는 중앙 저장소를 중심으로 하는 하나의 노드일 뿐.
* 보통 중앙집중식 협업 모델 한가지 방식 만 존재 -> 변경 사항이 모두 중앙 저장소에 집중됨


### Integration-Manager Workflow
Git을 사용하면 리모트 저장소 여러 개 운영 가능
다른 개발자에겐 읽기 권한만 주어지는 Workflow도 가능

**Workflow**
1. 프로젝트 Integration-Manager는 프로젝트 메인저장소에 Push를 한다. 

2. 프로젝트 기여자는 메인저장소를 Clone하고 수정한다. 

3. 기여자는 자신의 저장소에 Push 하고 Integration-Manager가 접근할 수 있도록 공개해 놓는다. 

4. 기여자는 Integration-Manager에게 변경사항을 적용해 줄 것을 E-mail 같은 것으로 요청한다. 

5. Integration-Manager는 기여자의 저장소를 리모트 저장소로 등록하고 수정사항을 Merge 하여 테스트한다. 

6. Integration-Manager는 Merge한 사항을 메인저장소에 Push한다.

이 방식은 Github이나 GitLab 같은 Hub 사이트를 통해 주로 사용하는 방식이다.
프로젝트를 Fork하고 수정사항을 반영하여 다시 모두에게 공개하기 좋은 구조임

**장점**
* 기여자와 Integration-Manager가 각자의 사정에 맞춰 프로젝트를 유지할 수 있다는 점.
* 기여자는 자신의 저장소와 브랜치에서 수정 작업을 계속해 나갈 수 있고 수정사항이 프로젝트에 반영되도록 기다릴 필요가 없다.
* 관리자는 기여자가 Push 한 커밋을 적절한 시점에 Merge함


### Dictator and Lieutenants Workflow
* 저장소를 여러개 운영하는 방식을 변형한 구조
* 보통 수백 명의 개발자가 참여하는 아주 큰 프로젝트를 운영 시 사용 (ex : Linux 커널 프로젝트)
* Lieutenants - 여러 Integration-Manager가 저장소에서 자신이 맡은 부분
	* 모든 Lieutenant는 최종 관리자(Dictator) 아래에 있다
* 공식 저장소 - 최종 관리자가 관리하는 저장소


**Workflow**
1. 개발자는 코드를 수정하고 master브랜치를 기준으로 자신의 토픽 브랜치를 Rebase 한다. 여기서 master 브랜치란 Dictator의 브랜치를 말한다. 

2. Lieutenant들은 개발자들의 수정사항을 자신이 관리하는 master 브랜치에 Merge 한다. 

3. Dictator는 Lieutenant의 master 브랜치를 자신의 master 브랜치로 Merge 한다. 

4. Dictator는 Merge 한 자신의 master 브랜치를 Push 하며 다른 모든 개발자는 Dictator의 master 브랜치를 기준으로 Rebase 한다. 


## 프로젝트에 기여하기
프로젝트에 기여하는 방식은 매우 다양하기에 설명하기 어려움.

**프로젝트 기여 변수**
1. 활발히 활동하는 개발자의 수
	* 얼마나 많은 개발자가 얼마나 자주 코드를 쏟아 내는가
	* 개발자가 많아질수록 코드를 깔끔하게 적용하거나 Merge하기 어려워짐
2. 프로젝트에서 선택한 저장소 운영 방식(Workflow)
3. 접근 권한
	* 쓰기 권한 , 읽기 권한 유무에 따라 프로젝트 기여 방식이 매우 달라짐
	

### 커밋 가이드라인
Git 프로젝트의 *Documentation/SubmittingPatches* 문서를 참고 

1. 공백문자를 깨끗하게 정리하고 커밋
`git diff --check`로 공백 문자에 대한 오류를 확인할 수 있음.

2. 각 커밋은 논리적으로 구분되는 Changeset 
	* 최대한 수정사항을 하나의 주제로 요약할 수 있어야 함
	* 여러가지 이슈를 한꺼번에 수정해도 Staging Area를 이용하여 한 커밋에 하나의 이슈만 담기도록 해야 한다.
	* 작업 내용을 분할한다
	* 각 커밋마다 적절한 메시지를 작성
	* 같은 파일의 다른 부분을 수정하는 경우 -> `git add -patch` 명령으로 한 부분씩 나누어 Staging Area에 저장한다.

3. 커밋 메시지
	* 커밋 메시지의 첫 라인은 50자가 넘지 않아야 하고 해당 커밋을 요약해야 한다
	* 다음 한 라인을 비우고 다음 라인부터 커밋을 자세히 설명한다.
	* 현재형 표현이나 명령문을 사용하는것이 좋다.


### 비공개 소규모 팀
두세 명으로 이루어진 비공개 프로젝트
비공개 - 소스코드가 공개되지 않은 것을 말하는 것 (외부에서 접근불가는 아님)
보통 Subversion 같은 중앙집중형 버전 관리 시스템에서 사용하던 방식 사용

**가장 큰 차이점**
서버가 아닌 클라이언트 쪽에서 Merge함


누가 리모트 저장소에 push를 한 상태이면 다른 사용자가 자신의 변경점을 push하기 위해선 리모트 저장소를 fetch하고 merge 한후 그것을 push 해야 한다.

**예**
![](Ch5_distributed_environment/9D0FA15D-B798-40FD-A128-871551E7DB4B.png)

`$ git log —no-merges issue54..origin/master`
히스토리를 검색할 때 뒤의 브랜치 (origin/master)에 속한 커밋 중 앞의 브랜치(issue54)에 속하지 않은 커밋을 검색하는 문법

위의 상황에서 origin/master를 최신 커밋과 merge를 하는 방법
1. `git checkout master`
2. `git merge issue54`
3. `git merge origin/master`
4. `git push origin master`


### 비공개 대규모 팀
팀을 여러 개의 작은 팀으로 나눔
Integration-manager Workflow를 선택하는 것이 좋음
작은 팀이 수행한 결과물을 Integration-Manager가 Merge하고 공유 저장소의 master 브랜치를 업데이트 한다.
팀마다 브랜치를 하나씩 만들고 Integration-Manager는 그 브랜치를 Pull해서 Merge 한다.


Push 할려는 브랜치의 이름이 서버 브랜치와 이름이 다를때
`$ git push -u origin 로컬브랜치이름:서버브랜치이름`



### 공개 프로젝트 Fork
비공개 팀 운영과 공개 팀 운영은 약간 다름
공개 팀은 모든 개발자가 공유 저장소에 직접적 쓰기 권한을 가지지 않음 -> 프로젝트의 관리자가 몇 가지 일을 더 해야한다. - **Fork를 사용**

**Fork를 통한 기여 방법**
```
$ git clone (url)
$ cd project
$ git checkout -b featureA
# (work)
$ git commit
# (work)
$ git commit
$ git remote add myfork (url)
$ git push -u myfork featureA
```

Fork 한 저장소에 Push하고 나면 프로젝트 관리자에게 이 내용을 알리는 **Pull Request** 를 통해 적용하도록 요청할 수 있다.
`git request-pull base브랜치명 저장소url` 명령으로 이메일을 수동으로 만들 수 있다.

다른 주제의 일을 할거면 주 저장소의 master 브랜치로부터 만들어야 한다.
```
$ git checkout -b featureB origin/master
# (work)
$ git commit
$ git push myfork featureB 
# (email maintainer)
$ git fetch origin 
```

![](Ch5_distributed_environment/42D79429-E3D6-44F9-9861-F9E87604BCD1.png)


```
$ git checkout featureA
$ git rebase origin/master
$ git push -f myfork featureA 
```


Git merge —squash 
—squash 옵션 -> 현재 브런치에 Merge 할 때 해당 브랜치의 커밋을 모두 하나의 커밋으로 합쳐서 Merge 한다. (Merge 커밋은 만들어지지 않음)



### 대규모 공개 프로젝트와 이메일을 통한 관리
오래된 대규모 프로젝트는 대부분 메일링리스트를 통해서 Patch를 받아들인다.

`git format-patch` 명령으로 메일링리스트에 보낼 mbox 형식의 파일을 생성
각 커밋은 하나씩 메일 메시지로 생성되는데 
커밋 메시지의 첫 번째 라인이 제목이 되고 Merge 메시지 내용과 Patch 자체가 메일 메시지의 본문이 됨

```
$ git format-patch -M origin/master
0001-add-limit-to-log-function.patch 
0002-changed-log-output-to-30-from-25.patch 
```
Format-patch 명령을 실행하면 생성한 파일 이름을 보여줌
-m 옵션 : 이름이 변경된 파일이 있는지 살펴보라는 옵션


메일을 보내려면 ~/.gitconfig에 이메일을 설정

**[imap]**
     folder = “[Gmail]/Drafts”
     host = imaps://imap.gmail.com
     user = user@gmail.com
     pass = p4ssw0rd
     port = 993
     sslverify = false

`$ git imap-send` 명령으로 Patch 파일을 IMAP 서버의 Draft 폴더에 이메일로 보낼 수 있다.

**[sendemail]**
     smtpencryption = tls
     smtpserver = smtp.gmail.com
     smtpuser = user@gmail.com
     smtpserverport = 587
`$ git send-email` 명령으로 패치 보낼 수 있음.


## 프로젝트 관리자
**프로젝트 운영**
1. Format-patch 명령으로 생성한 patch를 이메일로 받아서 프로젝트에 patch를 적용
2. 프로젝트의 다른 리모트 저장소로부터 변경 내용을 merge

### 토픽 브랜치
메인 브랜치에 통합하기 전에 임시로 토픽 브랜치를 하나 만들고 거기에 통합해 보고 나서 다시 메인 브랜치에 통합하는 것이 좋음 -> Patch를 더 효과적으로 적용 가능

**토픽 브랜치 만드는법**
`$ git branch sc/ruby_client master`

브랜치를 만들고 바로 Checkout
`$ git checkout -b sc/ruby_client master`


### Patch 적용
1. `git apply` 사용
`$ git apply /tmp/patch-ruby-client.patch` -> 현재 디렉토리의 파일들을 Patch 파일 내용에 따라 변경한다.

Git apply는 “모두 적용, 아니면 모두 취소” 모델 사용 -> Patch를 적용하는 데 실패하면 Patch를 적용하기 이전 상태로 전부 되돌려 놓음
Patch 명령은 여러 파일에 적용하다가 중간에 실패하면 그대로 중단

2. **AM 명령 사용**
format-patch 명령으로 만든 Patch 파일은 기여자의 정보와 커밋 정보가 포함되어 있기 때문이다. 
그래서 기여자가 diff보다 format-patch 를 사용하도록 권해야 한다. 
git apply는 기존의 Patch파일에만 사용한다. 

받은 메일이 git send-email로 만든 메일이면 mbox형식으로 저장하고 이 mbox 파일을 `git am` 명령으로 적용함.

성공적으로 Patch 하지 못하면 git은 Merge 나 Rebase의 경우처럼 문제를 일으킨 파일에 충돌 표시를 해 놓는다. Merge나 Rebase처럼 Patch도 충돌 해결 가능

**Patch 충돌 해결**
```
$ (fix the file)
$ git add ticgit.gemspec
$ git am --resolved
```

**3-way patch**
같은 프로젝트의 커밋이면 3-way 옵션으로 기본 옵션보다 훨씬 똑똑하게 충돌 상황 해결 가능
`$ git am -3 name.patch`


### 리모트 브랜치로부터 통합하기
프로젝트 기여자가 자신의 저장소를 만들고 커밋을 몇 번 하고 저장소의 URL과 변경 내용을 메일로 보내왔다면 URL을 리모트 저장소로 등록하고 Merge 가능

하지만 기여하는 사람이 간단한 Patch를 만들어내면 이메일로 Patch 파일을 받는 것이 나음.

**예**
```
$ git remote add jessica git://github.com/jessica/myproject.git
$ git fetch jessica
$ git checkout -b rubyclient jessica/ruby-client 
```

리모트 저장소로 등록하지 않고도 merge 가능하다.
`$ git pull https://github.com/onetimeguy/project`


### 무슨 내용인지 확인
 Merge 할지 말지 결정하기 위해 미리 검토 가능

—not 옵션 사용해서 히스토리에서 master 브랜치를 제외한 커밋들만 확인한다
`$ git log contrib --not master`

` $ git diff master ` 이걸로 diff 내용을 확인할 수도 있지만 잘못된 것을 보여줄 수도 있다 -> 토픽 브랜치에서 작업하는 동안 master 브랜치에 새로운 커밋이 추가될 경우

공통 조상인 커밋을 찾고 이 조상 커밋에서 변경된 내용 살피기
```
$ git merge-base contrib master
36c7dba2c95e6bbb78dfa822519ecfec6e1ca649 
$ git diff 36c7db
```

위는 불편하므로 아래 사용
…으로 두 브랜치의 공통 조상과 브랜치의 마지막 커밋을 비교
`$ git diff master...contrib`


### 기여물 통합하기
기여물을 토픽 브랜치에 적용하고 Long-Running 브랜치나 master 브랜치로 통합하기

#### **Merge 하는 Workflow**

![](Ch5_distributed_environment/D936A089-36C8-4675-B12D-F7F2C3E97546.png)

개발자가 언정 버전이 필요하면 master 브랜치를 빌드하고
최신 버전이 필요하면 develop 브랜치를 checkout하여 빌드하면 됨


#### 대규모 **MERGE WORKFLOW**
기여자가 새로운 기능을 제안하면 관리자는 자신의 저장소에 토픽 브랜치를 계속 테스타하고 안정화되면 next로 merge하고 저장소에 push

![](Ch5_distributed_environment/49DC8A5D-DE1E-454C-A57E-15FB979B2469.png)

토픽 브랜치가 좀 더 개선되야 하면 next가 아니라 pu에 Merge.
-> 검증을 맞치면 next로 이동  -> next를 기반으로 pu를 다시 만듬 -> 토픽 브랜치가 master 브랜치로 merge되면 저장소에서 삭제

#### Rebase와 Cherry-Pick Workflow
히스토리를 평평하게 관리하는데 적합
토픽 브랜치에서 작업 -> maseter 브랜치를 rebase -> 커밋 재생성 -> 문제가 없으면 master 브랜치 fast-forward -> 평평한 히스토리

**Cherry-Pick**
한 브랜치에서 다른 브랜치로 작업한 내용을 옮기는 또 다른 방식
토픽 브랜치에 있는 커밋 중에서 하나만 고르거나 토픽 브랜치에 커밋이 하나밖에 없을 때 Rebase보다 유용

Cherry-pick 실행 전
![](Ch5_distributed_environment/7E31C427-39B0-4FE6-A4D9-2C71685944ED.png)
실행 후
![](Ch5_distributed_environment/9D68479D-553D-49E5-BB8D-F4387B749B27.png)


**RERERE**
Reuse recorded resolution (충돌 해결방법 재사용)
Rerere 기능이 활성화 돼 있으면 Merge가 성공할 때마다 그 이전과 이후 상태를 저장해둠.

`$ git config —global rerere.enabled true `


### 릴리즈 버전에 태그 달기
`$ git tag -s v1.5 -m 'my signed 1.5 tag`








#git/progit