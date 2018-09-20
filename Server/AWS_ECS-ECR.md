# AWS ECS - ECR
## ECR 설정하기
1. AWS ECS에 들어가서 레퍼지토리 생성
2. IAM에 들어가서 사용자 없으면 생성하고 권한 2개 추가
	- AmazonEC2ContainerRegistryFullAccess
	- AmazonEC2ContainerServiceforEC2Role
3. ec2 instance에 AWS CLI 설치
`sudo yum install awscli`

4. 로그인할 User의 액세스 키를 넣어준다
`aws configure`

5. Ecr에 로그인
`aws ecr get-login --no-include-email`

6. 위에서 나온 `docker login -u AWS -p ~~~~~~~` 를 복사해서 다시 입력
->  로그인 성공



## ECR 에 이미지 올리기
올릴 이미지의 레포지토리 정보를 ecr repository url로 변경한다
`docker tag image:latest XYZXYZXYZXYZ.dkr.ecr.us-east-1.amazonaws.com/repo-test:latest`

이미지 ecr에 푸쉬
`docker push 'repostory':latest`

## ECR에 존재하는 이미지 받기
`docker pull 'repostory':latest`



참고 : [Amazon ECR와 AWS CLI 함께 사용하기 - Amazon ECR](https://docs.aws.amazon.com/ko_kr/AmazonECR/latest/userguide/ECR_AWSCLI.html#AWSCLI_get-login)
[AWS EC2 Container Registry(ECR) 어렵지 않아요](http://bluese05.tistory.com/51?category=559701)
#Server