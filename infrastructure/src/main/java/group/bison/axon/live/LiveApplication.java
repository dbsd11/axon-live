package group.bison.axon.live;

import group.bison.axon.live.audience.commands.StartJoinRoomAudienceCommand;
import group.bison.axon.live.room.commands.StartCreateRoomCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.function.compiler.FunctionCompiler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.function.Function;

@SpringBootApplication(proxyBeanMethods = false, scanBasePackages = "group.bison.axon.live", exclude = {WebMvcAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
@EnableWebFlux
public class LiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveApplication.class, args);
    }

    @Bean
    public Function<StartCreateRoomCommand, String> createRoomFunction(CommandGateway commandGateway) {
        return cmd -> {
            String room = null;
            try {
                room = commandGateway.sendAndWait(cmd).toString();
            } catch (Exception e) {
                throw e;
            }
            return room == null ? "" : room;
        };
    }

    @Bean
    public Function<StartJoinRoomAudienceCommand, String> audienceJoinRoomFunction(CommandGateway commandGateway) {
        return cmd -> {
            String room = null;
            try {
                room = commandGateway.sendAndWait(cmd).toString();
            } catch (Exception e) {
                throw e;
            }
            return room == null ? "" : room;
        };
    }

    @Bean
    public <T, R> FunctionCompiler<T, R> compiler() {
        return new FunctionCompiler<>();
    }
}
