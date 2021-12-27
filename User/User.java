package User;

public class User {
    private String userId;
    private int level = 0;

    public User (String userId) {
        this.userId = userId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUserId() { return userId; }
    public int getUserLevel() { return level; }

}
