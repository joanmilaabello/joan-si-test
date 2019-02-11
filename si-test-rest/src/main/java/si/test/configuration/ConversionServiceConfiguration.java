package si.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import si.test.converter.LineToLogin;

@Configuration
public class ConversionServiceConfiguration {
    @Bean(name="conversionService")
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new LineToLogin());
        return defaultConversionService;
    }
}
