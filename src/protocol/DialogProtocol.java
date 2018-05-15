package protocol;

import java.net.DatagramPacket;
import java.util.regex.Pattern;

public class DialogProtocol {

    protected CommandEnum command;
    private String rawData;
    private String content;

    public DialogProtocol() {}

    public DialogProtocol(String data) {
        this.rawData = data;
        String[] tab = data.split(";");
        this.command = getCommand(data);
        this.content = getContent(data);
    }

    private void readData(String data) {
        Pattern global = Pattern.compile("DialogProtocol\\{\\ncommand='.*'\\ncontent='.*'\\n\\}");
        if(!data.startsWith("DialogProtocol{")) {
            command = CommandEnum.UNKNOWN;
            content = "";
        } else {
            Pattern pattern = Pattern.compile("");
            String tab[] = data.split("\n");
//            if(tab[1].matches(\g\))
        }
    }

    public DialogProtocol(DatagramPacket p) {
        this((new String(p.getData())).trim());
    }

    public void setCommand(CommandEnum command) {
        this.command = command;
    }
    public void setCommand(String command) {
        this.command = CommandEnum.getEnum(command);
    }
    public void setContent(String content) {
        this.content = content;
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

    public String getRawData() { return rawData; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return "DialogProtocol{\ncommand='" + command + "'\ncontent='" + content + "'\n}";
    }

    // =========================================

    public static String requestConnection(Object asker) {
        return "DialogProtocol{\ncommand='ConnectRequest'\ncontent='hello'\n}";
    }

    public static CommandEnum getCommand(String message) {
        if(message.startsWith("DialogProtocol{\n")) {
            String tab[] = message.split("\n");
            for(int i = 1; i < tab.length; i++) {
                if(tab[i].startsWith("command=")) {
                    return CommandEnum.getEnum(tab[i].replace("'", "").replace("command=",""));
                }
            }
        }
        return CommandEnum.UNKNOWN;
    }

    public static String getContent(String message) {
        if(message.startsWith("DialogProtocol{\n")) {
            String tab[] = message.split("content=");
            if(tab.length > 2) {
                return "";
            } else {
                return tab[1].substring(1,tab[1].length()-1);
            }
        }
        return "";
    }
}
