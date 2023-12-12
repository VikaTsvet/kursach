package com.example.kursach;
import com.example.kursach.models.translator.TranslatorData;
import org.junit.Assert;
import org.junit.Test;

public class TranslatorDataTest {

    @Test
    public void testGetTranslatedText() {
        String expectedTranslatedText = "Hello";
        TranslatorData translatorData = new TranslatorData();
        translatorData.setTranslatedText(expectedTranslatedText);

        String actualTranslatedText = translatorData.getTranslatedText();

        Assert.assertEquals(expectedTranslatedText, actualTranslatedText);
    }

    @Test
    public void testSetTranslatedText() {
        String expectedTranslatedText = "Bonjour";
        TranslatorData translatorData = new TranslatorData();
        translatorData.setTranslatedText(expectedTranslatedText);
        String actualTranslatedText = translatorData.getTranslatedText();
        Assert.assertEquals(expectedTranslatedText, actualTranslatedText);
    }
}
