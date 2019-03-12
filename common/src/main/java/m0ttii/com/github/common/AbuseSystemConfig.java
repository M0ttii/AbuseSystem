package m0ttii.com.github.common;

public class AbuseSystemConfig {

    private final String databaseHost;
    private final int databasePort;
    private final String databaseUser;
    private final String databasePassword;
    private final String databaseName;

    public AbuseSystemConfig(String databaseHost, int databasePort, String databaseUser, String databasePassword, String databaseName) {
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.databaseName = databaseName;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

}
