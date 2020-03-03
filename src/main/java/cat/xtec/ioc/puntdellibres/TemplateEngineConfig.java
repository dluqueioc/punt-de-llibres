package cat.xtec.ioc.puntdellibres;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class TemplateEngineConfig {

  @Bean
  public LayoutDialect layoutDialect() {
      return new LayoutDialect();
  }

}
