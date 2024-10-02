

package terhal;



public class User {
    private String userId;   // معرف المستخدم الفريد
    private String name;     // الاسم الكامل للمستخدم
    private String email;    // عنوان البريد الإلكتروني للمستخدم
    private String username;  // اسم المستخدم لتسجيل الدخول
    private String password;  // كلمة مرور المستخدم

    // المُنشئ
    public User(String userId, String name, String email, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // الدوال المساعدة (Getters)
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

