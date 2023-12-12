package com.example.kursach;

import com.example.kursach.models.translator.TranslatorData;
import com.example.kursach.models.translator.TranslatorResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TranslatorResultTest {

    @Test
    public void testTranslatorResult() {
        String status = "success";
        TranslatorData data = new TranslatorData();

        TranslatorResult result = new TranslatorResult();
        result.setStatus(status);
        result.setData(data);

        assertEquals(status, result.getStatus());
        assertEquals(data, result.getData());
    }

    @Test
    public void testTranslatorResult_DefaultConstructor() {
        TranslatorResult result = new TranslatorResult();

        assertNull(result.getStatus());
        assertNull(result.getData());
    }
}
