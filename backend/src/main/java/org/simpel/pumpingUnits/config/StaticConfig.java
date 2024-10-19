package org.simpel.pumpingUnits.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class StaticConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Получаем текущий рабочий каталог
        String workingDir = Paths.get("").toAbsolutePath().toString();

        // Задаем путь к папке uploads
        String uploadsDir = workingDir + File.separator + "uploads" + File.separator;

        // Проверяем наличие папки и создаем, если не существует
        File uploadFolder = new File(uploadsDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Добавляем обработчик для ресурса
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadsDir);
    }
}
