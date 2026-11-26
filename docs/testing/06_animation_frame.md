# 애니메이션 프레임 검증

## 테스트 목적

스프라이트 프레임 인덱스의 정상 전환, 설정된 반복 시작 프레임, 마지막 프레임 이후 순환, 잘못된 인덱스 예외를 확인한다.

## 검증 환경 및 절차

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 환경: Windows 11 x64, Microsoft OpenJDK 25.0.5.1, Gradle 9.1.0, JavaFX 25
- 절차: 기존 `SpriteSheetAnimatorTest`를 포함한 `gradlew clean test` 실행. 이어서 저장소 밖의 임시 하네스로 `nextFrameIndex`를 직접 확인했다. 임시 하네스는 `C:\Moni_QA_reports\additional-probes`에 두었고 프로그램 및 저장소 테스트 코드는 수정하지 않았다.
- 예상 결과: 프레임이 순차 전환되고 마지막 프레임은 `loopFromFrame`으로 돌아오며, 범위를 벗어난 인덱스는 `IllegalArgumentException`을 던진다.

## 테스트 결과

| ID | 검증 항목 | 예상 결과 | 실제 결과 |
|---|---|---|---|
| T06-01 | 프레임 0의 다음 인덱스 | 1 | PASS, 1 |
| T06-02 | `loopFromFrame=1`에서 다음 인덱스 | 2 | PASS, 2 |
| T06-03 | 프레임 3 이후 순환 | 1 | PASS, 1 |
| T06-04 | 인덱스 -1 | `IllegalArgumentException` | PASS, 예외 발생 |
| T06-05 | 프레임 수 4에서 인덱스 4 | `IllegalArgumentException` | PASS, 예외 발생 |
| T06-06 | 기존 JUnit 반복 프레임 테스트 | 통과 | PASS, 1개 테스트 통과 |

## 발견된 문제 및 결론

발견된 문제 없음. 인덱스 계산과 예외 처리는 통과했다. JavaFX Timeline의 실제 시간 간격과 화면 애니메이션의 부드러움은 별도로 측정하지 않았다.