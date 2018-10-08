# EC2_MongoDB
## NoSQL(Not only SQL)
기존 데이터베이스는 ACID의 특성을 가지지만
nosql은 이중 한가지가 빠져있다.


## ACID
* **원자성(Atomicity)** -  트랜잭션과 관련된 작업들이 부분적으로 실행되다가 중단되지 않는 것을 보장하는 능력이다. 예를 들어, 자금 이체는 성공할 수도 실패할 수도 있지만 보내는 쪽에서 돈을 빼 오는 작업만 성공하고 받는 쪽에 돈을 넣는 작업을 실패해서는 안된다. 원자성은 이와 같이 중간 단계까지 실행되고 실패하는 일이 없도록 하는 것이다.

* **일관성(Consistency)** -  트랜잭션이 실행을 성공적으로 완료하면 언제나 일관성 있는 데이터베이스 상태로 유지하는 것을 의미한다. 무결성 제약이 모든 계좌는 잔고가 있어야 한다면 이를 위반하는 트랜잭션은 중단된다.

	* **고립성(Isolation)** -  트랜잭션을 수행 시 다른 트랜잭션의 연산 작업이 끼어들지 못하도록 보장하는 것을 의미한다. 이것은 트랜잭션 밖에 있는 어떤 연산도 중간 단계의 데이터를 볼 수 없음을 의미한다. 은행 관리자는 이체 작업을 하는 도중에 쿼리를 실행하더라도 특정 계좌간 이체하는 양 쪽을 볼 수 없다. 공식적으로 고립성은 트랜잭션 실행내역은 연속적이어야 함을 의미한다. 성능관련 이유로 인해 이 특성은 가장 유연성 있는 제약 조건이다. 자세한 내용은 관련 문서를 참조해야 한다.

* **지속성(Durability)**-  성공적으로 수행된 트랜잭션은 영원히 반영되어야 함을 의미한다. 시스템 문제, DB 일관성 체크 등을 하더라도 유지되어야 함을 의미한다. 전형적으로 모든 트랜잭션은 로그로 남고 시스템 장애 발생 전 상태로 되돌릴 수 있다. 트랜잭션은 로그에 모든 것이 저장된 후에만 commit 상태로 간주될 수 있다.

[ACID - 위키백과, 우리 모두의 백과사전](https://ko.wikipedia.org/wiki/ACID)

### 샤딩
데이터베이스 샤딩이란 대용량의 데이터를 처리하기 위해 테이블을 수평 분할하여 데이터를 분산 저장하고 처리하는 것이다. 이더리움에서의 샤딩은 메인 체인을 k개의 샤드로 분할한다.


## ulimit
`$ ulimit -a`
`$ sudo vi /etc/security/limits.conf`
```vim
#*               soft    fsize           unlimited
#*               hard    fsize           unlimited
#*               soft    cpu             unlimited
#*               hard    cpu             unlimited
#*               soft    as              unlimited
#*               hard    as              unlimited
#*               soft    memlock         unlimited
#*               hard    memlock         unlimited
#*               soft    nofile          64000
#*               hard    nofile          64000
#*               soft    nproc           64000
#*               hard    nproc           64000
```


## MongoDB Install
**맥**
`$ brew cask install mongodb-compass`

**ec2**
`$ sudo vi /etc/mongod.conf`에서 bindIp를 모든 주소에 대해 열어줘야됨  ->(0.0.0.0)
MongoDB 시작
`$ sudo systemctl start mongod`
Mongo DB 접속
`$ mongo --port 27000`

접속해서
```
$ use admin
$ db.createUser({
... user: "admin",
... pwd: "linxus123",
... roles: [ "root" ]
... }
... )
```

MongoDb 설정에서 security 를 바꿔줘야 ID,PW 적용이 됨
`$ sudo vi /etc/mongod.conf`


생성한 계쩡을 이용한 Db 접속
`mongo --port 27000 -u admin -p linxus123 --authenticationDatabase 'admin'`





















#Server