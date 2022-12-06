# OnsuYumYumYum Server
온수역 맛집 소개 서비스 2탄 API Server

## Introduce
이 서비스는 [온수냠냠냠](https://www.onsuyum.com/)을 위한 Api 서버입니다.

## Development environment

- 개발 환경 : macOS(arm-M1)
- 개발 도구 : IntelliJ
- 사용 언어 : Java([openJDK 11 - zulu 11.0.15 LTS](https://www.azul.com/downloads/?package=jdk#download-openjdk))
- 데이터베이스 : Mysql(배포), H2 database(개발)

## Getting start

[온수냠냠냠 API](https://api.onsuyum.com/)

## Used Teck stack

<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=flat&logo=AmazonEC2&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20RDS-527FFF?style=flat&logo=AmazonRDS&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=flat&logo=AmazonS3&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white">

## Database ERD
![ERD](diagram.png)

수정 가능성 있음

## Sequence Diagram

### Upload
```mermaid
sequenceDiagram
    autonumber
    actor A as client
    participant B as onsuyum-app(front-end)
    participant C as onsuyum-api(back-end)
    participant D as ec2(local)
    participant E as database(amazon rds) 
    participant F as S3 bucket
    A->>B: 이미지 업로드 버튼 클릭
    B->>C: 이미지 HTTP multipart/form-data형식으로 Send
    alt local storage 업로드
        C--)D: 이미지 파일 local storage에 저장
    else local storge 업로드 실패
        C->>B: Error response send
        B->>B: 실패 UI rendering
        B->>A: 업로드 실패 확인
    end
    alt S3 storage 업로드
        C--)F: 이미지 파일 s3 storage에 저장
    else S3 storage 업로드 실패
        C->>B: Error response send
        B->>B: 실패 UI rendering
        B->>A: 업로드 실패 확인
    else local, S3 storage 업로드 성공
        F->>C: 저장된 이미지의 s3 url 반환
        C->>E: s3, local, 파일명 등 정보를 DB에 저장
        C->>B: 이미지 정보들을 app에게 반환
        B->>A: 이미지 보이기
    end
```

온수냠냠냠 API는 이미지 파일 S3 bucket과 EC2(local)로 저장을 합니다.

<!-- ### Cache -->

## Api docs

* Swagger를 이용한 문서 자동화

![](swagger-example.png)

## License
OnsuYumYumYum Server는 MIT License를 적용했습니다.

## Open Source

* [https://github.com/tsparticles/404-templates](https://github.com/tsparticles/404-templates)
