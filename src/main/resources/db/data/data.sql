/* TB_MEMBER DDL */
INSERT INTO TB_MEMBER(
    MEMBER_ID
  , MEMBER_PASSWORD
  , MEMBER_NAME
  , MEMBER_EMAIL
  , MEMBER_REG_ID
  , ROLE
)
VALUES (
    'testId1'
  , '$2a$10$liYc6ycYDYzrVPpYH9B21.jHrTvwswIhvATjMucCPDYrXjxI/4eOi'
  , '이순신'
  , 'testId1@test.com'
  , MEMBER_ID
  , 'STAFF'
)
;

INSERT INTO TB_MEMBER(
    MEMBER_ID
  , MEMBER_PASSWORD
  , MEMBER_NAME
  , MEMBER_EMAIL
  , MEMBER_REG_ID
  , ROLE
)
VALUES (
    'testId2'
  , '$2a$10$liYc6ycYDYzrVPpYH9B21.jHrTvwswIhvATjMucCPDYrXjxI/4eOi'
  , '전우치'
  , 'testId2@test.com'
  , MEMBER_ID
  , 'STAFF'
)
;

INSERT INTO TB_MEMBER(
    MEMBER_ID
  , MEMBER_PASSWORD
  , MEMBER_NAME
  , MEMBER_EMAIL
  , MEMBER_REG_ID
  , ROLE
)
VALUES (
    'testId3'
  , '$2a$10$liYc6ycYDYzrVPpYH9B21.jHrTvwswIhvATjMucCPDYrXjxI/4eOi'
  , '이성계'
  , 'testId3@test.com'
  , MEMBER_ID
  , 'STAFF'
)
;

INSERT INTO TB_MEMBER(
    MEMBER_ID
  , MEMBER_PASSWORD
  , MEMBER_NAME
  , MEMBER_EMAIL
  , MEMBER_REG_ID
  , ROLE
)
VALUES (
    'testId4'
  , '$2a$10$liYc6ycYDYzrVPpYH9B21.jHrTvwswIhvATjMucCPDYrXjxI/4eOi'
  , '관리자'
  , 'testId4@test.com'
  , MEMBER_ID
  , 'MANAGER'
)
;
