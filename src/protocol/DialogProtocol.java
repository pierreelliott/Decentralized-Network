package protocol;

public class DialogProtocol {

    protected CommandEnum command;
    private String rawData;

    public DialogProtocol(String data) {
        this.rawData = data;
        this.command = CommandEnum.getEnum(data.split(";")[0]);
    }

    public boolean isConnection() {
        switch (command) {
            case CONNECTREQUEST:
                return true;
            default:
                return false;
        }
    }

    public boolean isAskingConnection() { return command == CommandEnum.CONNECTREQUEST; }
    public boolean isMessage() { return command == CommandEnum.MESSAGE; }

    // =========================================

    public static String requestConnection(Object asker) {
        return "ConnectRequest;";
    }
}
