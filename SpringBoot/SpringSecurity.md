Awesome, Mihir! Since you're already familiar with **JWT** and **OAuth** from the MERN stack, you already understand the *why*. Now let’s explore the **how** — using **Spring Security**.

This will be **Phase 8 – Spring Security & JWT**, and yes — this is where Spring gets serious about **authentication and authorization**.

---

# 🔐 Phase 8 – Spring Security & JWT

---

## 🧭 Subtopics We’ll Cover

| Part | Topic                                                                      |
| ---- | -------------------------------------------------------------------------- |
| 8.1  | **Spring Security Basics** – Filters, authentication flow, in-memory users |
| 8.2  | **Form Login & Password Encoders**                                         |
| 8.3  | **JWT Authentication (Stateless APIs)**                                    |
| 8.4  | **Custom UserDetailsService**                                              |
| 8.5  | **OAuth2 (Google, GitHub login)** *(optional)*                             |
| 8.6  | Role-based Authorization                                                   |

Let’s dive into each part **gradually** with full hands-on and intuitive explanation.

---

## 🔹 8.1 – Spring Security Basics

---

### 🔒 What Is Spring Security?

Spring Security is a **filter chain** that protects your app:

* Blocks access to URLs unless authorized
* Handles login, password encoding, sessions, etc.
* Allows declarative control using annotations or config

---

### 🧱 How It Works Internally (Simplified Flow)

```
Incoming HTTP request
        ↓
Security Filter Chain
        ↓
Authentication Manager
        ↓
Success → request proceeds
Failure → 401 or redirect to login
```

You’ll mostly be configuring:

* **Which endpoints are protected**
* **How to authenticate users**
* **What roles are required**

---

### ✅ Step-by-Step: Basic Security Setup

---

### 1️⃣ Add Dependency (in `pom.xml`)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Once you do this, Spring will auto-secure **all endpoints** by default.

Try running your app again → any endpoint will ask for **Basic Auth**.

---

### 2️⃣ Add In-Memory User

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
            .withUsername("mihir")
            .password(passwordEncoder().encode("password123"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

### 🔍 What Did We Just Do?

| Code Block                    | Meaning                          |
| ----------------------------- | -------------------------------- |
| `.authorizeHttpRequests(...)` | Whitelist some URLs              |
| `.httpBasic()`                | Use simple basic-auth headers    |
| `InMemoryUserDetailsManager`  | Temporary user store (no DB yet) |
| `BCryptPasswordEncoder`       | Secure hashing for passwords     |

Now you can:

* Visit `/public/hello` freely
* Visit `/secret` → you’ll get a browser auth prompt for `mihir/password123`

---

### 3️⃣ Add a Controller

```java
@RestController
public class HelloController {

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/secret")
    public String secret() {
        return "This is a secured endpoint!";
    }
}
```

---

## 🧠 Intuition Behind Spring Security

| Concept            | Intuition                                                               |
| ------------------ | ----------------------------------------------------------------------- |
| **Filter Chain**   | Think of it like middleware (Express.js) — every request passes through |
| **Authentication** | Who are you? (username + password)                                      |
| **Authorization**  | What can you access? (roles/permissions)                                |

---

## 🧪 Your Turn: Try This Out

| Task                                                               | Description |
| ------------------------------------------------------------------ | ----------- |
| ✅ Add `/public`, `/private`, `/admin` endpoints                    |             |
| ✅ Configure access so: public = all, private = USER, admin = ADMIN |             |
| ✅ Add 2 in-memory users with different roles                       |             |

---

### 🔜 What’s Next?

In **Part 8.2**, we’ll move from **basic auth** to more real-world approaches:

* `formLogin` (like websites)
* Adding real users from **DB**
* Implementing **JWT tokens** for **stateless APIs** (your forte from MERN)

Ready to proceed to **8.2 – Form Login, DB users & Password Encoding**?


Great, Mihir! Let’s level up to **real-world authentication** — no more in-memory users and basic auth prompts.

---

# 🔐 Phase 8.2 – Form Login, DB Users & Password Encoding

---

## 🧭 Goals for This Part

We’ll build:

1. A **custom user model** stored in the database
2. A **login form** (or use Postman to mimic login)
3. **Spring Security** using your **UserDetailsService**
4. Proper **password encoding** using BCrypt

---

## 📌 Step-by-Step Breakdown

---

### ✅ 1. Define a `User` Entity (JPA)

```java
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role; // Example: ROLE_USER or ROLE_ADMIN

    // Getters and Setters
}
```

---

### ✅ 2. Create a `JpaRepository`

```java
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
```

---

### ✅ 3. Create a `UserDetailsService` Implementation

This tells Spring Security **how to load users** from your DB.

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword()) // already encoded
            .roles(user.getRole())        // roles must NOT have "ROLE_" prefix here
            .build();
    }
}
```

---

### ✅ 4. Configure Spring Security for Form Login

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService service) {
        this.userDetailsService = service;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults()) // form login enabled
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // hashes the password securely
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and().build();
    }
}
```

---

### ✅ 5. Create a Registration Endpoint

```java
@RestController
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.passwordEncoder = encoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // default role
        repo.save(user);
        return "User registered!";
    }
}
```

> ✅ Now you can POST to `/register` with:

```json
{
  "username": "mihir",
  "password": "123456"
}
```

Then log in at `/login` with the same credentials via browser **or Postman**.

---

## 🧠 Intuition Check

| Thing We Did            | Why We Did It                               |
| ----------------------- | ------------------------------------------- |
| Custom `User` entity    | Real apps store users in DB                 |
| `UserDetailsService`    | Let Spring Security know how to fetch users |
| `BCryptPasswordEncoder` | Never store raw passwords                   |
| `formLogin()`           | Enable classic login flow (session-based)   |

---

### 📍 Stop Here If You Want Sessions

This setup is perfect for:

* Admin dashboards
* Websites with login/logout and sessions

But…

If you’re building a REST API → **you don’t want session cookies**.

That’s where **JWT authentication** comes in.

---

## 🚀 Next Up: 8.3 – Stateless APIs with JWT (Token-Based Authentication)

Here’s what we’ll build:

* Login → receive JWT
* Use JWT in headers for secure endpoints
* No sessions, no cookies — **pure API auth**


Awesome, Mihir! Let’s deep dive into the real deal of modern backend security: **JWT-based stateless authentication** — a perfect match for REST APIs, and very familiar to MERN folks like you!

---

# 🔐 Phase 8.3 – JWT Authentication (Stateless APIs)

---

## 🧭 What You’ll Learn

| Feature                  | Description                                            |
| ------------------------ | ------------------------------------------------------ |
| 🎟️ JWT (JSON Web Token) | Token that represents authenticated user               |
| 🔄 Stateless Auth        | No session storage, token is passed with every request |
| 📜 Filters               | Custom filters to handle login and token validation    |
| 🔧 Configuration         | Securing endpoints with token verification             |

---

## 🧠 JWT Auth Flow – The Big Picture

```
[1] Client → POST /login (with username & password)
[2] Server → Verifies, returns a JWT
[3] Client → Stores JWT (e.g. in localStorage)
[4] Client → Sends JWT in Authorization header for future requests
[5] Server → Verifies token, allows/denies access
```

---

## 📦 Add JWT Dependency

We'll use [jjwt](https://github.com/jwtk/jjwt) – Java JWT library.

Add to `pom.xml`:

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

---

## ✅ Step-by-Step Implementation

---

### 1️⃣ Create JWT Utility Class

```java
@Component
public class JwtUtil {

    private final String SECRET_KEY = "mihirSecretKey";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1hr
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
```

---

### 2️⃣ Create Auth Request/Response Models

```java
public class AuthRequest {
    private String username;
    private String password;
    // getters and setters
}

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // getter
}
```

---

### 3️⃣ Create Authentication Controller

```java
@RestController
public class JwtAuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtAuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
```

---

### 4️⃣ Create JWT Filter

```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService service) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
```

---

### 5️⃣ Update Security Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtFilter, CustomUserDetailsService service) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = service;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(daoAuthProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(daoAuthProvider())
            .build();
    }
}
```

---

## ✅ Test the Full Flow

1. **Register** via `POST /register`
2. **Login** via `POST /login`

   * You’ll get a JWT token
3. Use that token in **Authorization header**:

   ```
   Authorization: Bearer <your_token>
   ```
4. Access any protected endpoint

---

## 🧠 Summary

| Concept            | Purpose                                                      |
| ------------------ | ------------------------------------------------------------ |
| JWT                | Stateless auth using signed tokens                           |
| Custom Filter      | Intercepts requests to validate tokens                       |
| UserDetailsService | Loads user info from DB                                      |
| Security Config    | Tells Spring how to behave (no sessions, filter order, etc.) |

---

## ✅ Want to Add More?

* Refresh tokens ✅ (you can add this later if needed)
* Roles and permissions ✅ (coming next)
* OAuth2 login via Google/GitHub (optional)

---

Ready to move to **8.4 – Custom Roles & Authorization**?
We'll define who can access what, using roles like `ADMIN`, `USER`, etc.


Absolutely, Mihir! Let’s now **finish Phase 8: Spring Security** by wrapping up the final core features:

---

# ✅ **Phase 8.4 – Roles & Fine-Grained Authorization**

---

## 🧭 Goal

By now, we’ve authenticated users using JWT. Now, we’ll control **who can do what** based on **roles**.

---

## ✅ 1. Recap: Role Field in Our User

```java
@Entity
public class AppUser {
    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role; // e.g., "ADMIN" or "USER"

    // getters, setters
}
```

**Important:** Spring Security expects roles to be prefixed with `"ROLE_"`.
So when you define `role = "ADMIN"` in your user, Spring treats it as `"ROLE_ADMIN"` behind the scenes.

---

## ✅ 2. Add Roles to JWT

Update `JwtUtil`:

```java
public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("roles", userDetails.getAuthorities()) // Add roles in token
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
}
```

---

## ✅ 3. Restrict Access by Role

You can **restrict endpoints** by role using annotations or config:

### Option 1: Annotation on Controller

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin-only")
public String adminData() {
    return "Secret for admins";
}
```

> You’ll need to enable method security:

```java
@EnableMethodSecurity // Add this in your config
@Configuration
public class SecurityConfig { ... }
```

---

### Option 2: In Security Config Itself

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin-only").hasRole("ADMIN")
    .requestMatchers("/user-area").hasAnyRole("USER", "ADMIN")
    .anyRequest().authenticated()
)
```

---

## 🛡 Summary of Spring Security Topics Covered

| Feature                  | You’ve Learned                   |
| ------------------------ | -------------------------------- |
| `UserDetailsService`     | Load users from database         |
| `PasswordEncoder`        | Encode/verify passwords securely |
| `SecurityFilterChain`    | Control access and auth flow     |
| JWT Auth                 | Stateless API token login        |
| Custom Filters           | Validate token for each request  |
| Role-based Authorization | Control access by user roles     |
| Method-level Security    | Add role checks in controllers   |

---

# 🎯 Done! You’ve Completed Spring Security (Phase 8)

At this point, you can:

* Handle **registration**, **login**, and **protected APIs**
* Use **JWT tokens** for stateless auth
* Apply **role-based** access rules
* Extend easily for real-world APIs

---

## 🧪 Optional Extras (Advanced)

If you're curious later:

* 🔁 **Refresh Tokens** – allow token renewal
* 🧬 **OAuth2 (Google, GitHub)** – for social logins
* 🔒 **CSRF Protection** – for browser-based sessions
* 💾 **Remember Me** – token persisted login
* 🔍 **Custom Exception Handling** – clean 401/403 responses

---

## 🧠 Spring Boot Journey Recap So Far

| Phase | Covered? | Key Topics                 |
| ----- | -------- | -------------------------- |
| ✅ 1   | Yes      | REST APIs, DI, Controllers |
| ✅ 2   | Yes      | IoC, Beans, Context        |
| ✅ 3   | Yes      | Configuration, Annotations |
| ✅ 4   | Yes      | Auto-config, Profiles      |
| ✅ 5   | Yes      | AOP, Events, Scheduling    |
| ✅ 6   | Yes      | JPA, Transactions          |
| ✅ 7   | Yes      | Actuator, Dockerizing      |
| ✅ 8   | Yes      | Security, JWT, Roles       |

---

## 🚀 What’s Next?

You’ve built a **serious backend foundation**. From here, you can:

* Add **API documentation** via Swagger/OpenAPI
* Set up **CI/CD** pipelines (GitHub Actions, Jenkins)
* Connect to **external systems** (Kafka, Mail, Redis)
* Learn **Spring Cloud** or **Microservices** if scaling big


