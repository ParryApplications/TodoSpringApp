package org.parryapplications.spring.todoproject.security;

//TODO: It requires spring-boot-starter-security to be added under the dependency
//@Configuration
//public class BasicSecurityConfiguration {
//
//  //Security Filter Layers config:
//  @Bean
//  SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests((req) -> req.anyRequest().authenticated());
////    http.authorizeHttpRequests(req -> req.requestMatchers("/**").hasRole("ADMIN").requestMatchers("/todos/**").hasRole("USER"));
//    http.httpBasic(withDefaults());
////    http.formLogin(withDefaults());
//    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    http.csrf(AbstractHttpConfigurer::disable);
//    return http.build();
//  }
//
//  //Global CORS Mapping:
////  @Bean
////  public WebMvcConfigurer webMvcConfig(){
////    return new WebMvcConfigurer() {
////      @Override
////      public void addCorsMappings(CorsRegistry registry) {
////        WebMvcConfigurer.super.addCorsMappings(registry);
////          registry.addMapping("/**")
////                  .allowedMethods("*")
////                  .allowedOrigins("http://localhost:3000");
////      }
////    };
////  }
//
//  //User Details:
//  @Bean
//  public UserDetailsService userDetailsService(DataSource dataSource){
//    UserDetails admin = User.withUsername("root")
////            .password("{noop}admin")
//            .passwordEncoder(str -> bCryptPasswordEncoder().encode(str))
//            .password("admin")
//            .roles("ADMIN").build();
//
//    UserDetails user = User.withUsername("parry")
////            .password("{noop}user")
//            .passwordEncoder(str -> bCryptPasswordEncoder().encode(str))
//            .password("user")
//            .roles("USER").build();
//
////    return new InMemoryUserDetailsManager(user, admin);
//
//    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//    jdbcUserDetailsManager.createUser(user);
//    jdbcUserDetailsManager.createUser(admin);
//    return jdbcUserDetailsManager;
//  }
//
//  //JDBC dataSourceForH2, for user details:
////  @Bean
////  public DataSource dataSourceForH2(){
////    return new EmbeddedDatabaseBuilder()
////            .setType(EmbeddedDatabaseType.H2)
////            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
////  }
//
//  //JDBC dataSourceForMySql, for user details:
//  @Bean
//  public DataSource dataSourceForMySql(){
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//    dataSource.setUrl("jdbc:mysql://localhost:3306/todo_db");
//    dataSource.setUsername("root");
//    dataSource.setPassword("parryapplications");
//    return dataSource;
//  }
//
//    //Bcrypt password encode(Hashing):
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//      return new BCryptPasswordEncoder();
//    }
//}
