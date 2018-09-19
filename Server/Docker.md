# Docker 적용

`yum install docker`\

**Docker daemon이 안 돌아갈 경우**
`sudo service docker start`

**Sudo 반복 안하기**
`sudo usermod -aG docker $USER`

## # 숙제
Docker 이미지를 Docker hub에 업로드해서,
인스턴스에서 실행할 수 있도록 하기

현재에 이미지를 확인하는 명령
`$ docker images`

이미지를 만드는 명령
`$ docker build -t ahndwon/hello-server .`
`					account/project-name <dockerfile path>`

이미지로부터 컨테이너를 만들어서 실행하는 명령
백그라운드에 돌리기 위해 -d 옵션 추가
```
$ docker run --name hello-server \
				-p 3000:3000 -d \
				ahndwon/hello-server
```

만들어진 컨테이너를 확인하는 명령
`docker ps -a`

종료된 컨테이너를 삭제하는 방법
`docker rm  <container-id or name>`

컨테이너를 정지하는 방법
`docker stop <container-id or name>`

## 도커 실행 스크립트 작성
서브라임 사용해서 작성
`subl Dockerfile`

vi 사용해서 작성
`vi Dockerfile`

# ----------------------------
Docker Image 지정한다.

DockerHub
`FROM node:10.10.0-alpine`
`mkdir -p /usr/src/app`
`RUN mkdir -p /usr/src/app`


 cd /usr/src/app
`WORKDIR /usr/src/app`

package.json 파일을 이미지에 복사
`COPY package.json /usr/src/app`

npm install
`RUN npm install`
`COPY . /usr/src/app`

# ----------------------------
 CMD:인스턴스가 생성된 후 수행할 명령

# npm run publish
`EXPOSE 3000`
`CMD [ "npm" , "run", "publish" ]`
#Server