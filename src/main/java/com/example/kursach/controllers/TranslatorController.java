package com.example.kursach.controllers;

import com.example.kursach.models.translator.TranslatorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class TranslatorController {

    @GetMapping("/translator")
    public ModelAndView viewTranslator(){
        return new ModelAndView("translator");
    }

    @PostMapping("/translator")
    public ModelAndView translate(@RequestParam String text, @RequestParam String from, @RequestParam String to){
        try {
            OkHttpClient client = new OkHttpClient();

            okhttp3.RequestBody body = new FormBody.Builder()
                    .add("source_language", from)
                    .add("target_language", to)
                    .add("text", text)
                    .build();
            Request request = new Request.Builder()
                    .url("https://text-translator2.p.rapidapi.com/translate")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    //секретный ключ myKey
                    .addHeader("X-RapidAPI-Key", "71e450a987msh713f342f0636ddap1586dbjsn0f0608a48699")
                    .addHeader("X-RapidAPI-Host", "text-translator2.p.rapidapi.com")
                    .build();

            ResponseBody responseBody = client.newCall(request).execute().body();
            ObjectMapper objectMapper = new ObjectMapper();
            TranslatorResult result = objectMapper.readValue(responseBody.string(), TranslatorResult.class);
            return new ModelAndView("translator", "result", result.getData().getTranslatedText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
