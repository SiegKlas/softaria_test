package ru.softaria;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebTracker {

    public static String generateEmailContent(Map<String, String> yesterday, Map<String, String> today) {
        Set<String> disappearedPages = new HashSet<>(yesterday.keySet());
        disappearedPages.removeAll(today.keySet());

        Set<String> newPages = new HashSet<>(today.keySet());
        newPages.removeAll(yesterday.keySet());

        Set<String> changedPages = new HashSet<>();
        for (String url : yesterday.keySet()) {
            if (today.containsKey(url) && !yesterday.get(url).equals(today.get(url))) {
                changedPages.add(url);
            }
        }

        StringBuilder emailBuilder = new StringBuilder();
        emailBuilder.append("Здравствуйте, дорогая и.о. секретаря\n\n");

        if (disappearedPages.isEmpty() && newPages.isEmpty() && changedPages.isEmpty()) {
            emailBuilder.append("За последние сутки во вверенных Вам сайтах не произошло изменений.");
        } else {
            emailBuilder.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:");
        }

        emailBuilder.append("\n\n");

        if (!disappearedPages.isEmpty()) {
            emailBuilder.append("Исчезли следующие страницы:\n");
            for (String url : disappearedPages) {
                emailBuilder.append(url).append("\n");
            }
            emailBuilder.append("\n");
        }

        if (!newPages.isEmpty()) {
            emailBuilder.append("Появились следующие новые страницы:\n");
            for (String url : newPages) {
                emailBuilder.append(url).append("\n");
            }
            emailBuilder.append("\n");
        }

        if (!changedPages.isEmpty()) {
            emailBuilder.append("Изменились следующие страницы:\n");
            for (String url : changedPages) {
                emailBuilder.append(url).append("\n");
            }
            emailBuilder.append("\n");
        }

        emailBuilder.append("С уважением,\nавтоматизированная система\nмониторинга.");

        return emailBuilder.toString();
    }
}
