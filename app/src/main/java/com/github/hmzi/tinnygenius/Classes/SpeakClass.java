package com.github.hmzi.tinnygenius.Classes;


import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import androidx.annotation.RequiresApi;

import java.util.Locale;
import java.util.Set;

public class SpeakClass implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    // Parameterized Constructor.
    public SpeakClass(Context context) {
        tts = new TextToSpeech(context, this);
    }

    // Initialization Configurations.
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Setting language here Locale.US
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This language is not supported");
            } else {
                speakOut("");
            }
        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }

    // Speak out function.
    public void speakOut(String data) {
        if (data == null || data.isEmpty())
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String utteranceId = String.valueOf(this.hashCode()); // this.hashCode() + "";
            tts.speak(data, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        } else {
            tts.speak(data, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    // function to shutdown tts.
    public void shutDown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
