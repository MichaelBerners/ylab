package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * класс инкапсулирующий чтение данных из конфиг файла
 */
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * метод осуществляющий чтение из конфиг файла (key=value)
     */
    public static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.yml")){
            PROPERTIES.load(resourceAsStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
