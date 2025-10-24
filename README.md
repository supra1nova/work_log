# work_log / ckeditor 및 파일 업로드 기술 검증
JAVA 17 / SpringBoot 2.7.17 / MAVEN / MyBatis / MariaDB / JSP

---

## 📋 개요 및 목표

- 업무 일지 개편 위한 ckeditor 적용 기술 검증용 프로젝트
- Spring Security를 이용한 간단한 인증 구현
- 서버 내 파일 업로드 구현 (실제 프로젝트 적용 시 저장소 이동 구현 필요하나 기술 구현 단계는 생략)
- 필요시 calendar까지 기술검증 진행

---

## 📋 사용 도구 및 라이브러리

- spring-boot-starter-web
- spring-boot-devtools
- spring-boot-starter-security
- spring-boot-starter-validation
- mariadb
- log4j2
- lombok
- mybatis
- common-lang3

---

## ⚠️ 구동 위한 사전 필요 사항

- 구동 환경 내 mariadb 설치 및 DB 스키마/유저 생성 필수
    ```java
        spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
        spring.datasource.url=jdbc:log4jdbc:mariadb://localhost:3306/worklog
        spring.datasource.username=worklog
        spring.datasource.password=worklog
    ```
- application.properties 를 .gitignore 해제 후 push 했음에 따라 바로 구동 가능

---

### 데이터 구성 및 흐름

- ddl 을 src/main/resources/db/schema.sql 에 구성했고, 동일한 경로의 data.sql 파일에는 테스트용 dml을 구성했습니다.
- testId1, testId2, testId3은 일반 유저로 암호는 1234 입니다.
- testId4는 매니저로 암호는 1234 입니다.
- testId4는 직원 관리가 가능하며 신규 직원을 추가/수정 등의 관리를 할 수 있습니다.
- 업무일지 캘린더는 /work-log-calendar/add/[년도-월]을 통해 매니저만 생성가능하며 입력하기 전까지는 업무일지 메뉴에 진입이 불가 합니다.
- 직원은 업무일지 메뉴가 생성되면 원하는 날짜에 업무일지를 작성할 수 있습니다.
