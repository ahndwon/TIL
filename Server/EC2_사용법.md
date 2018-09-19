# EC2 생성
route53에 도메인 추가
C name과 A address 추가
(A address -> name에 www 추가


AWS에서 EC2에 인스턴스 생성
(데이터 설정에서 종료시 삭제를 취소해야 데이터베이스로 사용 가능)

생성하면 pem 키가 주어짐

## 터미널에서 접속
`ssh -i pemkeyname.pem ec2-user@13.209.76.196` 로 ec2을 터미널에서 접속


ec2엔 git이 없으므로
`sudo yum install git` 

node도 없으므로 node 사이트가서 파일 주소 복사
->  `wget 파일 주소`

`tar xvf로 파일 압축 해제 `

`echo $PWD`
위 명령어를 친 값을 복사하고 (/home/ec2-user)
`vi ~/.bash_profile`에 가서 
`export PATH=/home/ec2-user/node-v10.10.0-linux-x64/bin:$PATH`. 추가한다.
추가한 후 적용을 하기위해 종료함 
`exit`
아니면 
`source ~/.bash_profile` 로 적용
재접속후
`node`로 적용되었는지 확인

## Https 인증서 추가
AWS에서 SSL

## 로드밸런서 추가
AWS에서 EC2에 로드 밸런서 생성
-> ec2의 인바운드 규칙에 VPC의 IPv4 CIDR 주소를 모든 TCP에 대해 열어주어야 한다. (VPC안에 모든 인스턴스들이 서로에 대해 열려있게 해주기 위함)

대상 그룹에 인스턴스를 추가해줌

## NodeJS 프로젝트 다운
Git clone으로 프로젝트 받은 후 필요한 노드 모듈 설치
`npm install`

## NodeJs 프로젝트 생성
`npm init`
`npm -i express-generator`
`npx --view pug`
`npm start`



## 인스턴스 중지
인스턴스를 중지할 경우 IP가 변함
재부팅은 변하지 않음
중지해도 안바꾸게 하기 위해선 aws에서 탄력적 IP를 설정해줘야 한다 ( 추가 요금 지불 )

## pm2 설치
`npm i pm2 -g`

실행
`pm2 start npm —- run publish`

삭제
`pm2 delete npm`

`pm2 save`

`pm2 startup`



## 로드밸런서 리스너에 규칙 추가




#Server/ec2