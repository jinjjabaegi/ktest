# Ktest
 Ktest는 Java 언어 기반의 단위 테스트 라이브러리 입니다.

# About Ktest
## 1. Exception Assertions
 Fatal Assert (throw Exception) , Nonfatal Assert 두가지 Assertion을 지원합니다.   

 > 참고 :
 > > Assert : Fatal Assert, Expect : Nonefatal Assert

## 2. TestBed
 Test 환경을 설정,해재할 수 있는 TestBed를 지원합니다. 이때 하나의 TestBed를 다수의 Test가 참조할 수 있으며, 같은 TestBed를 참조할 경우 TestBed를 통해 만들어진 환경을 공유할 수 있습니다.

 > 참고 :
 > > Test는 TestBed 없이도 환경을 설정, 해재할 수 있는 독립적인 객체입니다.

## 3. Logger
 Ktest의 log 출력 형식에 맞게 출력할 수 있도록 Logger를 지원합니다.

 > 참고 :
 > > LogType은 `TEMP`를 사용하는 것을 추천합니다.

 
 ## 2. 라이센스 및 남기는 말

Ktest는 [Apache License 2.0](./LICENSE.txt) 라이센스를 이용합니다, 여러분의 적극적인 이슈, 기능 피드백을 기대합니다.

```
SoYeon, Kim
+821068932337
epals8962@realtimetech.co.kr
```