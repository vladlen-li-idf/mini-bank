package kz.solva.migration.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.ClassPathResource

object PropertyConfig {

  @Bean
  fun yamlPropertyFactory() = YamlPropertiesFactoryBean()
    .apply { setResources(ClassPathResource("application.yml")) }

  @Bean
  fun placeholderConfigurer(
    yamlPropertiesFactoryBean: YamlPropertiesFactoryBean
  ): PropertySourcesPlaceholderConfigurer {
    return PropertySourcesPlaceholderConfigurer()
      .apply {
        setProperties(yamlPropertiesFactoryBean.getObject()!!)
      }
  }
}
