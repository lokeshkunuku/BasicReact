import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class Test3 {
    // TODO(developer): Uncomment these lines.

    public static void main( String[] args ) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation =
                translate.translate("Hello",
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("es"),
                       Translate.TranslateOption.model("base"));

        System.out.printf("TranslatedText:\nText: %s\n", translation.getTranslatedText());

    }
}
