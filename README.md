Camp Connect

📆 개발 기간: 10/11 ~ 10/16

🎯 개발 목표: 

1. 서로 다른 개발환경에서의 연동(CORS) 
2. 회원가입 & Spring에서 JWT 방식의 로그인 
3. 게시판 구현(CRUD 구현, 이미지 업로드) 
4. 댓글 작성(CRUD 개념 적용)

✔ Tech Stack

| Language  | Java                         |
| --------- | ---------------------------- |
| Framework | Spring boot, Spring Security |
| Database  | MySQL, H2                    |
| Deploy    | AWS                          |

✔ API

![image](https://user-images.githubusercontent.com/76515226/137482416-85a2a2a9-3e35-4ba9-a705-1ae68508e113.png)

![image](https://user-images.githubusercontent.com/76515226/137482503-46e59404-3a44-44ba-a576-c8e85e9f6bb6.png)

✔ ERD

![image](https://user-images.githubusercontent.com/76515226/137482671-07892edd-687b-48c8-8e27-eb050952906b.png)


✔ 트러블슈팅
1. CORS

프론트앤드와 백앤드가 처음으로 협업하는 자리였고 각각 다른 환경에서 개발을 하다보니 CORS 문제가 발생했다.

서로 출처가 다른 웹 애플리케이션에서 자원을 공유하며 프로젝트를 진행했고 프론트엔드와 백엔드의 출처가 달라 CORS에러가 발생한 것을 확인했다

브라우저는 보안상의 이유로 교차 출처 HTTP요청을 제한하기에 이를 해결하기 위해선 백엔드에 CORS관련 설정을 해주어야 한다는 것을 배웠다.

이에 해결방법으로 현재 BackEnd 프로젝트에는 인증부분을 스프링 시큐리티로 처리하고 있었고 해당 경우 간단하게 스프링 시큐리티 설정으로 CORS허용을 할수가 있었다.

<br>



```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://54.180.132.5/","http://localhost:3000")
               
                .allowedMethods("POST","GET","PUT","DELETE","HEAD","OPTIONS") 
                
                .allowCredentials(true);
    }
```
WebConfig 클래스 생성 후 모든 경로에 대해 cors를 적용하기 위해 addMapping("/**")를 사용했다. 

또한 .allowedMethods을 통해 클라이언트에서 요청하는 메소드 범위(GET,POST 등등)를 정하고 클라이언트에서 쿠키를 받기 위해 allowCredentials(true)을 사용했다.

양 방향 서버에서 요청을 주고 받기 위해 .allowedOrigins에 프론트와 백엔드의 주소를 추가하며 스프링설정을 했다.

<br>

```java
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        **http.cors();**
        http.csrf().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and()
                ...(중략)
```
스프링 시큐리티에서는 WebSecurityConfigurerAdapter를 상속하는 클래스를 하나 만든 후 configure를 재정의(cors메서드 사용)하는 설정을 해주었다.
