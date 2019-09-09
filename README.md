# Ktest
 Ktest는 Java 언어 기반의 단위 테스트 라이브러리 입니다.

# About Ktest
## 1. Exception Assertions
 Fatal Assert (throw Exception) , Nonfatal Assert 두가지 Assertion을 지원합니다.   

 > 참고 :
 > > Assert : Fatal Assert, Expect : Nonefatal Assert

## 2. TestBed
 Test 환경을 설정, 해재할 수 있는 TestBed를 지원합니다. 이때 하나의 TestBed를 다수의 Test가 참조할 수 있습니다.
 
 > 참고 :
 > > TestBed는 한 개 이상의 Test가 같은 Test 환경을 갖고 있을 경우 사용하는 것을 추천합니다.
 > > (반대로 Test 환경이 각각 다를 경우 @Before, @After를 사용하는 것을 추천합니다.)
 
## 3. @Before/@After
 단일 테스트 전후에 실행되는 메소드를 지정할 수 있도록 @Before, @After Annotation을 지원합니다.
 
 > 참고 :
 > > @Before, @After의 대상 Test 메소드가 타 파일에 있을 경우 지원이 안됩니다.
 > > 대상 Test에 TestBed와 @Before,@After가 전부 지정되어 있을 시 TestBed -> @Before -> @After -> TestBed 순으로 실행됩니다.

## 3. Logger
 Ktest의 log 출력 형식에 맞게 출력할 수 있도록 Logger를 지원합니다.

 
## 2. 라이센스 및 남기는 말

Ktest는 [Apache License 2.0](./LICENSE.txt) 라이센스를 이용합니다, 여러분의 적극적인 이슈, 기능 피드백을 기대합니다.

```
SoYeon, Kim
+821068932337
epals8962@realtimetech.co.kr
```
