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
        String[] tab = text.split(" ");
        DialogProtocol protoc = new DialogProtocol();
        protoc.setCommand(tab[0]);
        protoc.setContent(text.substring(tab[0].length()+1));
        return protoc;
    }
}
