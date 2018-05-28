package com.example.chenzw.xflib;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends UnityPlayerActivity {

    public SpeechRecognizer speechRecognizer;
    private Context unityContext;
    public String results = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void InitSpeechRecognizer (Context context) {
        unityContext = context;
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5b0370c5");
        speechRecognizer = SpeechRecognizer.createRecognizer(context, mInitListenter);
    }

    public InitListener mInitListenter = new InitListener() {
        @Override
        public void onInit(int i) {
            UnityPlayer.UnitySendMessage("GameManager", "InitXFResult", "Init Success !");
        }
    };

    public void StartSpeechListener () {
        //中文
        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //普通话
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        //不带标点
        speechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");

        speechRecognizer.startListening(mRecognizerListener);
    }

    public RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String text = parseResult(recognizerResult);
            results += text;
            if (b) {
                UnityPlayer.UnitySendMessage("GameManager", "RecognizerResult", results);
                results = "";
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    private String parseResult(RecognizerResult results) {
        String json = results.getResultString();
        StringBuffer ret = new StringBuffer();

        try {

            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
