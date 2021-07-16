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

import com.google.api.gax.paging.Page;
import com.google.cloud.translate.v3beta1.*;
import io.grpc.Context;
import io.opencensus.metrics.export.Distribution;
import org.json.JSONArray;
import org.json.JSONObject;


public class Gcptest {
    public static void main(String[] a) throws IOException{

        String jsonString1 = new String(Files.readAllBytes(Paths.get("speechTanscript")));
        JSONObject obj = new JSONObject(jsonString1);
        //The JSON Structure {annotationResults_[speechTranscriptions_[alternatives_[words_-->{transcript_,{startTime_-->seconds,nanoseconds}}}]]]}
        String projectId = "channel-data-307403";
        // Supported Languages: https://cloud.google.com/translate/docs/languages
        String targetLanguage = "es";

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
                        translateText(projectId, targetLanguage, transcript);

                        timex= time.getInt("seconds_");
                        nanosecond = time.getInt("nanos_");
                        System.out.print("seconds::" + timex+" ; nanoseconds::" + nanosecond+"\n");
                    }
                    catch (Exception e) {
                        System.out.print("error");
                    }

                }

            }
        }
    }

    private static void translateText(String projectId, String targetLanguage, String transcript) {
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            LocationName parent = LocationName.of(projectId, "global");

            // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(transcript)
                            .build();

            TranslateTextResponse response = client.translateText(request);

            // Display the translation for each input text provided
            for (Translation translation : response.getTranslationsList()) {
                System.out.printf("Translated text: %s\n", translation.getTranslatedText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}








