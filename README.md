# SPRING ADVANCED
\<br\>
## 💡 주요 개선 사항 및 리팩토링

### Lv 0. 프로젝트 세팅 - 에러 분석

* **문제**: `FilterConfig`와 `JwtUtil` 간의 **순환 참조**로 인한 애플리케이션 실행 실패
* **해결**: `JwtFilter`를 Spring Bean으로 직접 등록하고 `FilterRegistrationBean`에서 해당 Bean을 주입받도록 구조를 변경

### Lv 1. ArgumentResolver

* **문제**: `@Auth` 어노테이션을 통해 인증된 사용자 정보를 받아오는 `AuthUserArgumentResolver`가 동작하지 않음
* **해결**: `WebConfig`에 `AuthUserArgumentResolver`를 등록하여 정상적으로 기능하도록 수정.

### Lv 2. 코드 개선

1.  **Early Return**: 불필요한 `passwordEncoder` 연산을 방지하기 위해 이메일 중복 검사를 로직의 가장 앞으로 이동
2.  **불필요한 if-else 제거**: `WeatherClient`의 `if-else` 구조를 제거하고, 예외 상황에서 즉시 `throw` 하도록 변경
3.  **Validation 로직 이동**: `UserService`에 있던 비밀번호 유효성 검사 로직을 `UserChangePasswordRequest` DTO로 이동시켜 `@Pattern` 어노테이션으로 처리

### Lv 3. N+1 문제 해결

* **문제**: 할 일 목록 조회 시 각 할 일에 연관된 사용자를 조회하기 위해 N+1 쿼리가 발생하는 문제
* **해결**: JPQL `fetch join`으로 작성되어 있던 코드를 `@EntityGraph` 수정하여 N+1 문제를 해결

### Lv 5. API 로깅

* **요구사항**: 관리자 API 호출 시 접근 로그를 기록
* **해결**: AOP `@Around` 어노테이션을 사용하여 `AdminApiLoggingAspect`를 구현
