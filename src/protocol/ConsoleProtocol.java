package protocol;

public class ConsoleProtocol {
    private String rawData;
    private String command;
    private String content;
    private DialogProtocol protoc;

    public ConsoleProtocol(String text) {
        String[] tab = text.split(" ");
        protoc = new DialogProtocol();
        protoc.setCommand(tab[0]);
        protoc.setContent(tab[1]);
    }

    public static DialogProtocol getProtocoleMessage(String text) {
        String command = text.split(" ")[0];
        DialogProtocol protoc = new DialogProtocol();
        boolean recognized = true;
        switch (command) {
            case "ping":
            case "message":
                break;
            default:
                command = "message";
                recognized = false;
        }
        protoc.setCommand(command);
        String content = recognized ? text.substring(command.length()+1) : text;
        protoc.setContent(content);
        return protoc;
    }
}
