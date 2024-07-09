package com.vannarith.bussiness.app.util;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class AppTools {
    public static String appGetTranslation(final String translateCode,
            final String langCode) {
        var resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("message/error");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        resourceBundleMessageSource.setDefaultLocale(new Locale("EN"));
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        
        Locale locale = null;
        if (!langCode.isEmpty() && langCode != null) {
            locale = new Locale(langCode);
        }
        String message = resourceBundleMessageSource.getMessage(translateCode, null, locale);
        return message;
    }
}
