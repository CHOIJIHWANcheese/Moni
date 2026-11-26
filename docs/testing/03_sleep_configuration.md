# 수면 설정값 검증

## 검증 환경

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 대상: `src/main/java/com/moni/app/` 내 sleep/SleepConfiguration.java
- 환경: Linux, OpenJDK 21.0.12.1
- 방법: 기준 커밋의 Java 소스 9개를 별도 작업 공간에 복사하고 Git blob 해시가 원본과 동일함을 확인한 뒤, 독립 검증 하네스로 컴파일 및 실행. AI 도구를 활용한 검증이며 사용자 PC에서 직접 실행한 결과는 아님.
- 명령: `javac -d out */*.java` 및 `java -cp out com.moni.app.interaction.QaHarness`
- 기존 프로그램 코드 및 테스트 코드 변경 없음.

## 테스트 결과

| ID | 입력 및 조건 | 기대 결과 | 실제 결과 |
|---|---|---|---|
| C01 | 인수 없음 | 10분 | PASS |
| C02 | --sleep-after-seconds=5 | 5초 | PASS |
| C03 | --sleep-after-seconds=0 | IllegalArgumentException | PASS |
| C04 | --sleep-after-seconds=abc | IllegalArgumentException | PASS |
| C05 | 5초 및 6초 인수 중복 | IllegalArgumentException | PASS |

실행 출력: C01 PASS, C02 PASS, C03 PASS, C04 PASS, C05 PASS.

## 결론 및 제한 사항

5개 검증 통과, 실패 0개. 기본값, 사용자 지정값, 잘못된 설정 입력의 처리를 검증했다. Gradle 실행 인수 전달 및 UI 연동은 미검증.

전체 Gradle/JUnit 테스트 및 Windows/JDK 25/JavaFX 실행은 수행하지 않았다.
