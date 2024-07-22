import org.junit.Test;
import ru.softaria.WebTracker;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WebTrackerTest {

    @Test
    public void testAllDifferentContent() {
        Map<String, String> yesterday = new HashMap<>();
        Map<String, String> today = new HashMap<>();

        yesterday.put("http://example.com/page1", "<html>Old Content 1</html>");
        yesterday.put("http://example.com/page2", "<html>Old Content 2</html>");
        yesterday.put("http://example.com/page3", "<html>Old Content 3</html>");

        today.put("http://example.com/page1", "<html>New Content 1</html>");
        today.put("http://example.com/page2", "<html>Old Content 2</html>");
        today.put("http://example.com/page4", "<html>New Content 4</html>");

        String expectedEmailContent = """
                Здравствуйте, дорогая и.о. секретаря

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы:
                http://example.com/page3

                Появились следующие новые страницы:
                http://example.com/page4

                Изменились следующие страницы:
                http://example.com/page1

                С уважением,
                автоматизированная система
                мониторинга.""";

        String actualEmailContent = WebTracker.generateEmailContent(yesterday, today);
        assertEquals(expectedEmailContent, actualEmailContent);
    }

    @Test
    public void testPartialDifferentContent() {
        Map<String, String> yesterday = new HashMap<>();
        Map<String, String> today = new HashMap<>();

        yesterday.put("http://example.com/page1", "<html>Old Content 1</html>");
        yesterday.put("http://example.com/page2", "<html>Old Content 2</html>");
        yesterday.put("http://example.com/page3", "<html>Old Content 3</html>");

        today.put("http://example.com/page1", "<html>Old Content 1</html>");
        today.put("http://example.com/page2", "<html>Old Content 2</html>");
        today.put("http://example.com/page4", "<html>New Content 4</html>");

        String expectedEmailContent = """
                Здравствуйте, дорогая и.о. секретаря

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы:
                http://example.com/page3

                Появились следующие новые страницы:
                http://example.com/page4

                С уважением,
                автоматизированная система
                мониторинга.""";

        String actualEmailContent = WebTracker.generateEmailContent(yesterday, today);
        assertEquals(expectedEmailContent, actualEmailContent);
    }

    @Test
    public void testSameContent() {
        Map<String, String> yesterday = new HashMap<>();
        Map<String, String> today = new HashMap<>();

        yesterday.put("http://example.com/page1", "<html>Old Content 1</html>");
        yesterday.put("http://example.com/page2", "<html>Old Content 2</html>");
        yesterday.put("http://example.com/page3", "<html>Old Content 3</html>");

        today.put("http://example.com/page1", "<html>Old Content 1</html>");
        today.put("http://example.com/page2", "<html>Old Content 2</html>");
        today.put("http://example.com/page3", "<html>Old Content 3</html>");

        String expectedEmailContent = """
                Здравствуйте, дорогая и.о. секретаря
                
                За последние сутки во вверенных Вам сайтах не произошло изменений.
                
                С уважением,
                автоматизированная система
                мониторинга.""";

        String actualEmailContent = WebTracker.generateEmailContent(yesterday, today);
        assertEquals(expectedEmailContent, actualEmailContent);
    }
}