# Spring Security

## Description
* Spring Security is a powerful authentication and authorization framework, which help us
to keep our REST APIs
secured and accessible only by authenticated and authorized calls.
* There are many forms of Spring Security application, including XML configurations using powerful
libraries such as **JWT (JSON Web Token)**.
* To find out more about JWT, you can visit the website at https://jwt.io/

## Steps
1. Create new Spring Boot project same as before
2. Create an interface class named **SecurityService**. It contains abstract method of:
   * Create JWT token
   * Get subject from JWT token

    ```java
    public interface SecurityService {
        String createToken(String subject, long ttlMillis);
        String getSubject(String token);
    }
    ```
3. Then, create **SecurityServiceImpl** class that implements the interface.
   * Create JWT token
        ```java
        // hardcode the secret key in this class
        private static final String secretKey = "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";
        
        @Override
        public String createToken(String subject, long ttlMillis) {
            if (ttlMillis <= 0) {
                throw new RuntimeException("Expiry time must be greater than Zero :["+ttlMillis+"] ");
            }
            
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            
            // Sign JWT with our ApiKey secret
            // Convert the secret key to a byte array 
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);

            // Pass the secret key in bytes to SecretKeySpec Java class to get a signingKey
            // While creating a signing key, we use JCA (Java Cryptography Architecture)
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            
            // Build the JWT token with signature algorithm and signing key above
            JwtBuilder builder = Jwts.builder().setSubject(subject).signWith(signatureAlgorithm, signingKey);
            
            // Set the expiration time
            long nowMillis = System.currentTimeMillis();
            builder.setExpiration(new Date(nowMillis + ttlMillis));
            
            return builder.compact();
        }
        ```
   * Get subject from JWT token
        ```java
        @Override
        public String getSubject(String token) {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }
        ```
4. Create a controller named **HomeController**. Before
calling the method, we have to autowire the SecurityService as follows:

    ```java
    @Autowired
    SecurityService securityService
    ```
   * Create JWT token
  
        ```java
        @ResponseBody
        @RequestMapping("/security/generate/token")
        public Map<String, Object> generateToken(@RequestParam(value="subject") String subject) {
            String token = securityService.createToken(subject, (2 * 1000 * 60));
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("result", token);
            return map;
        }
        ```

   * Get subject from JWT token
  
        ```java
        @ResponseBody
        @RequestMapping("/security/get/subject")
        public Map<String, Object> getSubject(@RequestParam(value="token") String token) {
            String subject = securityService.getSubject(token);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("result", subject);
            return map;
        }
        ```
5. It's done! Let's try to generate token by calling the API in a browser or any REST client. For example:

    ```
    http://localhost:8080/security/generate/token?subject=hafara
    ```

    ![](/img/ss14.png)
 
    This token will be used for user authentication-like purposes.

6. Then, try to get the subject we passed earlier by calling the API using generated token. For example:

    ```
    http://localhost:8080/security/get/subject?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWZhcmEiLCJleHAiOjE1NTU3MzUzODR9.NQCHa30gxQ-XmKbsilVtnW4GSf0wNVLxqQKtAh2FwTw
    ```

    ![](/img/ss15.png)

## Full Code

[Spring Security RESTful Web Services](book-management/)

