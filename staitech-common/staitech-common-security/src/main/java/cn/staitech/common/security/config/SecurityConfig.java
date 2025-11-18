package cn.staitech.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    /**
     * 密码编码器 - 使用 BCrypt（推荐）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // strength 表示计算强度，4-31，默认10
        // 强度10表示 2^10 次哈希计算
        return new BCryptPasswordEncoder();
    }
}
