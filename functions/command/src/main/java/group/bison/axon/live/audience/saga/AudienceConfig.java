package group.bison.axon.live.audience.saga;


import org.axonframework.config.SagaConfiguration;
import org.axonframework.config.SagaConfigurer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudienceConfig {

    @Bean
    public SagaConfiguration<AudienceRoomManagerSaga> audienceRoomSagaConfiguration(@Autowired(required = false) AxonConfiguration configuration) {
        return SagaConfigurer.forType(AudienceRoomManagerSaga.class).initialize(configuration);
    }
}
