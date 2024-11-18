package terhal;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author s4ooo
 */
public class User {
    private String userId; // Primary key, unique identifier
    private String Name;
    private String Username;
    private String email;
    private String passwordHash; // To store hashed password
    private LocalDateTime createdAt; // Timestamp of when the user was created

    public User() {
        this.userId = UUID.randomUUID().toString();
    }
    
    public User(String userId){
        this.userId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}