# Camp Connect

### 📢 캠핑장 찾기 어려우셨죠? 저희 캠프 커넥트가 연결 시켜드리겠습니다!

🎉 사이트 주소: https://bit.ly/2YYBOXm

📺 데모 영상: https://www.youtube.com/watch?v=tvhFhO4n3OU

📆 개발 기간: 10/11 ~ 10/16

👨🏻‍🤝‍👨🏻 Front: 윤진선 & 이경아

👨🏻‍🤝‍👨🏻 Back: 김태웅 & 홍재환

---

🎯 개발 목표: 

1. 서로 다른 개발환경에서의 연동(CORS) 
2. 회원가입 & Spring에서 JWT 방식의 로그인 
3. 캠핑장 카테고리 필터링 
4. 캠핑장 리뷰 CRUD 기능
5. 캠핑장 예약 기능
6. 마이페이지 예약 현황 조회

---

✔ Tech Stack

| Stack     | Front        | Back                         |
| --------- | ------------ | ---------------------------- |
| Framework | React, Redux | Spring boot, Spring Security |
| Library   | Axios, Immer |                              |
| Database  |              | MySQL                        |
| Deploy    | S3           | AWS                          |

---

✔ API

![image](https://user-images.githubusercontent.com/76515226/137482416-85a2a2a9-3e35-4ba9-a705-1ae68508e113.png)

![image](https://user-images.githubusercontent.com/76515226/137482503-46e59404-3a44-44ba-a576-c8e85e9f6bb6.png)

---

✔ ERD

![image](https://user-images.githubusercontent.com/76515226/137482671-07892edd-687b-48c8-8e27-eb050952906b.png)

---

✔ 트러블슈팅

#### 1. CORS

프론트앤드와 백앤드가 처음으로 협업하는 자리였고 각각 다른 환경에서 개발을 하다보니 CORS 문제가 발생했다.

서로 출처가 다른 웹 애플리케이션에서 자원을 공유하며 프로젝트를 진행했고 프론트엔드와 백엔드의 출처가 달라 CORS에러가 발생한 것을 확인했다

브라우저는 보안상의 이유로 교차 출처 HTTP요청을 제한하기에 이를 해결하기 위해선 백엔드에 CORS관련 설정을 해주어야 한다는 것을 배웠다.

이에 해결방법으로 현재 BackEnd 프로젝트에는 인증부분을 스프링 시큐리티로 처리하고 있었고 해당 경우 간단하게 스프링 시큐리티 설정으로 CORS허용을 할수가 있었다.

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

#### 2. JWT

토큰을 이용하게 되면 쿠키에 토큰을 담아 header에 실어서 보내게 된다, front에서는 이 토큰을 받아서 로그인 정보를 이용하여 로그인을 하고 매 권한이 필요한 요청마다 토큰을 담아서 같이 보내게 된다.

```java
// JwtTokenProvider

private String secretKey = "시크릿 키";
// 프론트와 일치시켜야 하는 토큰 유효시간
private Long toemValidTime = 24 * 60 * 60 * 1000L;

// screckey를 base64로 인코딩
@PostConstruct
protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
}

// JWT 토큰 생성
public String createToken(String userPk) {
    Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims) // 정보 저장
        .setIssuedAt(now) // 토큰 발행 시간 정보
        .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
        .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
        // signature 에 들어갈 secret값 세팅
        .compact();
}

// 토큰에서 회원 정보 추출
public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
}

// JWT 토큰에서 인증 정보 조회
public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
}

// Request의 header에서 토큰 값 가져오기 (key값을 프론트와 일치시켜야 한다)
public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
}

// 토큰의 유효성 + 만료일자 확인
public boolean validateToken(String jwtToken) {
    try {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        System.out.println(claims); // JWT 토큰(클라이언트에서 보낸)이 잘 들어오는지 검증
        return !claims.getBody().getExpiration().before(new Date()); // expire시간이 되지 않았다면 True
    } catch (Exception e) {
        return false;
    }
}
```

