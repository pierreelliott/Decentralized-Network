package protocol;

import java.net.DatagramPacket;

public class DialogProtocol {

    protected CommandEnum command;
    private String rawData;

    public DialogProtocol(String data) {
        this.rawData = data;
        this.command = CommandEnum.getEnum(data.split(";")[0]);
    }

    public DialogProtocol(DatagramPacket p) {
        this((new String(p.getData())).trim());
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
    public boolean isAbortingConnection() { return command == CommandEnum.ABORTINGCONNECTION; }
    public boolean isMessage() { return command == CommandEnum.MESSAGE; }

    // =========================================

    public static String requestConnection(Object asker) {
        return "ConnectRequest;";
    }
}
