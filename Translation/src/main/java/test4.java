/*import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.v3beta1.*;
import java.io.Reader;
import java.lang.reflect.Array;
import org.json.JSONString;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.FileReader;*/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


public class test4 {
    public static void main(String[] a) throws IOException, JSONException {
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        String jsonString1 = new String(Files.readAllBytes(Paths.get("speechTanscript")));
        JSONObject obj = new JSONObject(jsonString1);
        //The JSON Structure {annotationResults_[speechTranscriptions_[alternatives_[words_-->{transcript_,{startTime_-->seconds,nanoseconds}}}]]]}
        JSONArray arr = obj.getJSONArray("annotationResults_");
        JSONObject obj1;
        JSONArray arr1;
        JSONObject obj2;
        JSONArray arr2 ;
        JSONObject obj3 ;
        JSONArray arr3 ;
        String transcript ;
        int timex;
        int nanosecond ;
        JSONObject time;
        /*JSONObject obj4 = null;
        JSONObject arr4 = null;
        Integer seconds = null;
        String names[] = null;
        */


        for (int i = 0; i < arr.length(); i++)//annotationResults_-->speechTranscriptions_
        {
            obj1 = arr.getJSONObject(i);
            arr1 = obj1.getJSONArray("speechTranscriptions_");
            for (int j = 0; j < arr1.length(); j++) //speechTranscriptions_-->alternatives_
            {
                obj2 = arr1.getJSONObject(j);
                arr2 = obj2.getJSONArray("alternatives_");

                for (int k = 0; k < arr2.length(); k++)//alternatives_-->words_
                {
                    obj3 = arr2.getJSONObject(k);
                    arr3= obj3.getJSONArray("words_");
                    transcript = arr2.getJSONObject(k).getString("transcript_");
                    int x=0;
                    try{
                        time=arr3.getJSONObject(x).getJSONObject("startTime_");
                        System.out.println(" Transcript :: "+transcript);
                        timex= time.getInt("seconds_");
                        nanosecond = time.getInt("nanos_");
                        System.out.print("seconds::" + timex+" ; nanoseconds::" + nanosecond+"\n");

                        Translation translation =
                                translate.translate(
                                        transcript,
                                        Translate.TranslateOption.sourceLanguage("en"),
                                        Translate.TranslateOption.targetLanguage("es"),
                                        // Use "base" for standard edition, "nmt" for the premium model.
                                        Translate.TranslateOption.model("base"));

                        System.out.printf("TranslatedText:\nText: %s\n", translation.getTranslatedText());



                    }
                    catch (Exception e) {
                        System.out.print("");
                    }

                }

            }
        }
    }
}








