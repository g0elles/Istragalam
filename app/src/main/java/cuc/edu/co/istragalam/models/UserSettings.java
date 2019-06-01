package cuc.edu.co.istragalam.models;

public class UserSettings {

    private Usuario user;
    private UserAccountSettings settings;

    public UserSettings(Usuario user, UserAccountSettings settings) {
        this.user = user;
        this.settings = settings;
    }

    public UserSettings() {

    }


    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public UserAccountSettings getSettings() {
        return settings;
    }

    public void setSettings(UserAccountSettings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", settings=" + settings +
                '}';
    }
}