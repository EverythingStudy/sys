package cn.staitech.system.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * 密码工具类
 * 负责密码的哈希、验证、强度校验等功能
 */
@Component
public class PasswordUtil {

    // 密码强度配置
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");

    // PBKDF2 配置
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATIONS = 100000;

    // 从配置文件中读取参数
    private static int minPasswordLength;
    private static int maxPasswordLength;
    private static boolean requireUppercase;
    private static boolean requireLowercase;
    private static boolean requireDigit;
    private static boolean requireSpecialChar;

    @Value("${app.security.password.min-length:8}")
    public void setMinPasswordLength(int minLength) {
        minPasswordLength = minLength;
    }

    @Value("${app.security.password.max-length:128}")
    public void setMaxPasswordLength(int maxLength) {
        maxPasswordLength = maxLength;
    }

    @Value("${app.security.password.require-uppercase:true}")
    public void setRequireUppercase(boolean require) {
        requireUppercase = require;
    }

    @Value("${app.security.password.require-lowercase:true}")
    public void setRequireLowercase(boolean require) {
        requireLowercase = require;
    }

    @Value("${app.security.password.require-digit:true}")
    public void setRequireDigit(boolean require) {
        requireDigit = require;
    }

    @Value("${app.security.password.require-special-char:true}")
    public void setRequireSpecialChar(boolean require) {
        requireSpecialChar = require;
    }

    /**
     * 生成随机盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 对密码进行哈希处理
     */
    public static String hashPassword(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    Base64.getDecoder().decode(salt),
                    ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码哈希处理失败", e);
        }
    }

    /**
     * 验证密码
     */
    public static boolean verifyPassword(String rawPassword, String salt, String storedHash) {
        String calculatedHash = hashPassword(rawPassword, salt);
        return calculatedHash.equals(storedHash);
    }

    /**
     * 验证密码强度
     */
    public static void validateStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (password.length() < minPasswordLength) {
            throw new IllegalArgumentException("密码长度不能少于 " + minPasswordLength + " 位");
        }

        if (password.length() > maxPasswordLength) {
            throw new IllegalArgumentException("密码长度不能超过 " + maxPasswordLength + " 位");
        }

        if (requireUppercase && !UPPERCASE_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("密码必须包含至少一个大写字母");
        }

        if (requireLowercase && !LOWERCASE_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("密码必须包含至少一个小写字母");
        }

        if (requireDigit && !DIGIT_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("密码必须包含至少一个数字");
        }

        if (requireSpecialChar && !SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("密码必须包含至少一个特殊字符");
        }

        // 检查常见弱密码
        if (isCommonWeakPassword(password)) {
            throw new IllegalArgumentException("密码过于简单，请使用更复杂的密码");
        }
    }

    /**
     * 检查是否为常见弱密码
     */
    private static boolean isCommonWeakPassword(String password) {
        String[] weakPasswords = {
                "123456", "password", "12345678", "qwerty", "abc123",
                "1234567", "111111", "1234567890", "admin", "letmein"
        };

        String lowerCasePassword = password.toLowerCase();
        for (String weak : weakPasswords) {
            if (lowerCasePassword.equals(weak) || lowerCasePassword.contains(weak)) {
                return true;
            }
        }

        // 检查连续字符或重复字符
        if (isSequential(password) || isRepeating(password)) {
            return true;
        }

        return false;
    }

    /**
     * 检查是否为连续字符
     */
    private static boolean isSequential(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            char c3 = password.charAt(i + 2);

            // 检查数字连续
            if (Character.isDigit(c1) && Character.isDigit(c2) && Character.isDigit(c3)) {
                if (c1 + 1 == c2 && c2 + 1 == c3) {
                    return true;
                }
                if (c1 - 1 == c2 && c2 - 1 == c3) {
                    return true;
                }
            }

            // 检查字母连续（不区分大小写）
            if (Character.isLetter(c1) && Character.isLetter(c2) && Character.isLetter(c3)) {
                char lower1 = Character.toLowerCase(c1);
                char lower2 = Character.toLowerCase(c2);
                char lower3 = Character.toLowerCase(c3);
                if (lower1 + 1 == lower2 && lower2 + 1 == lower3) {
                    return true;
                }
                if (lower1 - 1 == lower2 && lower2 - 1 == lower3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查是否为重复字符
     */
    private static boolean isRepeating(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            char c3 = password.charAt(i + 2);
            if (c1 == c2 && c2 == c3) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成随机密码
     */
    public static String generateRandomPassword() {
        String uppercase = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        String lowercase = "abcdefghjkmnpqrstuvwxyz";
        String digits = "23456789";
        String specialChars = "!@#$%^&*";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // 确保包含每种要求的字符类型
        if (requireUppercase) {
            password.append(uppercase.charAt(random.nextInt(uppercase.length())));
        }
        if (requireLowercase) {
            password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        }
        if (requireDigit) {
            password.append(digits.charAt(random.nextInt(digits.length())));
        }
        if (requireSpecialChar) {
            password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }

        // 填充剩余长度
        String allChars = uppercase + lowercase + digits + specialChars;
        int remainingLength = minPasswordLength - password.length();
        for (int i = 0; i < remainingLength; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 打乱顺序
        return shuffleString(password.toString());
    }

    /**
     * 打乱字符串顺序
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        SecureRandom random = new SecureRandom();
        for (int i = characters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[index];
            characters[index] = temp;
        }
        return new String(characters);
    }

    /**
     * 获取密码强度评分 (0-100)
     */
    public static int getPasswordStrengthScore(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int score = 0;

        // 长度评分
        score += Math.min(password.length() * 4, 40);

        // 字符种类评分
        if (UPPERCASE_PATTERN.matcher(password).find()) score += 10;
        if (LOWERCASE_PATTERN.matcher(password).find()) score += 10;
        if (DIGIT_PATTERN.matcher(password).find()) score += 10;
        if (SPECIAL_CHAR_PATTERN.matcher(password).find()) score += 15;

        // 重复字符扣分
        if (hasRepeatedCharacters(password)) {
            score -= 10;
        }

        // 连续字符扣分
        if (isSequential(password)) {
            score -= 15;
        }

        // 常见弱密码扣分
        if (isCommonWeakPassword(password)) {
            score -= 30;
        }

        return Math.max(0, Math.min(score, 100));
    }

    /**
     * 检查是否有重复字符
     */
    private static boolean hasRepeatedCharacters(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取密码强度等级
     */
    public static PasswordStrength getPasswordStrength(String password) {
        int score = getPasswordStrengthScore(password);
        if (score >= 80) return PasswordStrength.VERY_STRONG;
        if (score >= 60) return PasswordStrength.STRONG;
        if (score >= 40) return PasswordStrength.MEDIUM;
        if (score >= 20) return PasswordStrength.WEAK;
        return PasswordStrength.VERY_WEAK;
    }

    /**
     * 密码强度等级枚举
     */
    public enum PasswordStrength {
        VERY_WEAK("非常弱", "red"),
        WEAK("弱", "orange"),
        MEDIUM("中等", "yellow"),
        STRONG("强", "lightgreen"),
        VERY_STRONG("非常强", "green");

        private final String description;
        private final String color;

        PasswordStrength(String description, String color) {
            this.description = description;
            this.color = color;
        }

        public String getDescription() {
            return description;
        }

        public String getColor() {
            return color;
        }
    }
}
