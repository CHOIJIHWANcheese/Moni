# 빌드 및 실행 검증

## 테스트 목적

Gradle 빌드와 JUnit 테스트를 실행하고 앱이 시작되어 캐릭터를 표시한 뒤 정상 종료되는지 확인한다. 투명 창 설정과 주요 기능의 통합 검증 범위도 기록한다.

## 검증 환경 및 절차

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 환경: Windows 11 x64, Microsoft OpenJDK 25.0.5.1, Gradle 9.1.0, JavaFX 25
- JDK 준비: Microsoft 공식 OpenJDK 25 ZIP을 휴대형 경로에 내려받고 게시된 SHA-256과 일치함을 확인했다. 시스템 전역 설정은 변경하지 않았다.
- 절차: `gradlew clean test --no-daemon`, `gradlew build --no-daemon`, `gradlew run --no-daemon --console=plain`을 순서대로 실행했다. 앱 창을 화면에서 확인한 뒤 Alt+F4로 닫고 Gradle 프로세스 종료를 확인했다.
- 예상 결과: 컴파일/테스트/패키징이 성공하고, 앱 창과 캐릭터가 표시되며 창을 닫으면 실행 프로세스가 종료된다.

## 테스트 결과

| ID | 검증 항목 | 예상 결과 | 실제 결과 |
|---|---|---|---|
| T10-01 | Gradle `clean test` | 성공 | PASS, 34개 테스트, 실패 0, 오류 0, 건너뜀 0 |
| T10-02 | Gradle `build` | 성공 | PASS, `BUILD SUCCESSFUL` |
| T10-03 | 앱 시작 | 창 표시 | PASS, JavaFX 창 표시, 실행 로그에 `Active app: explorer.exe` 기록 |
| T10-04 | 캐릭터 표시 | 스프라이트 렌더링 | PASS, 128×160 창에 캐릭터가 보임 |
| T10-05 | 정상 종료 | 창 닫힘 및 실행 종료 | PASS, Alt+F4 후 창이 사라지고 `gradlew run`이 `BUILD SUCCESSFUL`로 종료 |
| T10-06 | 투명 창 | 바탕이 비치는 창 | 부분 확인. 코드에 `StageStyle.TRANSPARENT`와 `Color.TRANSPARENT` 설정이 있으나 캡처에서 바탕 합성 여부는 판별하지 못함. |
| T10-07 | 주요 기능 end-to-end 통합 | 걷기/수면/대화/드래그 통합 동작 | 미완료. 단위 테스트와 개별 좌표/리소스 검증은 완료했지만 실제 기능 전체를 한 세션에서 확인하지 않음. 실제 드래그는 T07 결과 참조. |

## 발견된 문제 및 결론

빌드, 전체 JUnit 테스트, 앱 시작과 정상 종료는 통과했다. 화면에서 캐릭터가 표시되는 것도 확인했다. 투명도의 바탕 합성과 전체 주요 기능 통합 동작은 확인되지 않았으므로 통과로 기록하지 않았다.