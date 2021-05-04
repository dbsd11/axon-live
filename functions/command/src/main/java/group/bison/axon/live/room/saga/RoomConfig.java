package group.bison.axon.live.room.saga;


import org.axonframework.config.SagaConfiguration;
import org.axonframework.config.SagaConfigurer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomConfig {

    @Bean
    public SagaConfiguration<CommonRoomManagerSaga> commonRoomSagaConfiguration(@Autowired(required = false) AxonConfiguration configuration) {
        return SagaConfigurer.forType(CommonRoomManagerSaga.class).initialize(configuration);
    }
}