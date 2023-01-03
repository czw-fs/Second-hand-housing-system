package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: fs
 * @date: 2023/1/3 11:22
 * @Description: everything is ok
 */
@Configuration
@EnableWebSecurity//开启spring security的自动配置,会给我们生成一个登录页面
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启Controller中方法的权限控制
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //在内存中设置一个认证的用户名和密码
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles("");
    }*/

    //创建一个密码加密器放到IOC容器中
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的方法，否则认证过程将失效,除非当前方法配置了认证
        //super.configure(http);
        //配置允许iframe标签访问
        http.headers().frameOptions().sameOrigin();

        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login")
                .permitAll().anyRequest().authenticated();

        //配置自定义登录页面
        http.formLogin().loginPage("/login")//配置去自定义页面访问的路径
                        .defaultSuccessUrl("/");//配置登录成功之后前往的地址
        //配置登出的地址及登出成功之后去往的地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        //关闭跨域请求伪造: 防止钓鱼网站,springsecurity自带的登录页面会生成一个令牌进行效验,如果请求没有令牌,则服务器不处理次请求,
        //我们写的是静态页面,没有生成令牌,故将其令牌校验关闭
        http.csrf().disable();

        //配置自定义的无权限访问的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
