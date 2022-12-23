package validators;

public class InputValidator {

    public static String validate(String message) {
        return message.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("&", "&amp;")
                .replaceAll("\"", "&quot;\t");
    }
}
