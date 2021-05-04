/*
 * Copyright (c) 2010-2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package group.bison.axon.live.config;

import group.bison.axon.live.audience.saga.AudienceRoomManagerSaga;
import group.bison.axon.live.room.AudienceJoinRoomAgg;
import group.bison.axon.live.room.RoomAgg;
import group.bison.axon.live.room.saga.CommonRoomManagerSaga;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.config.EventProcessingModule;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.config.CommandHandlerSubscriber;
import org.axonframework.spring.config.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Map;

//@Configuration
@ComponentScan("group.bison.axon.live")
@Import(CQRSH2DBConfig.class)
public class CQRSInfrastructureConfig {

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = SimpleCommandBus.builder().build();
        commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
        return commandBus;
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        return new AnnotationCommandHandlerBeanPostProcessor();
    }

    @Bean
    public CommandHandlerSubscriber commandHandlerSubscriber() {
        return new CommandHandlerSubscriber();
    }

    /**
     * Ideally, this bean would be created by the axon-spring module automatically, by setting the
     * {@link org.axonframework.spring.config.EnableAxon} on one of our configuration classes.
     * This however throws 'interesting' lifecycle exceptions which didn't seem all that clear.
     * In the interest of getting this application running again for the time being, I've resorted to using the
     * {@link org.axonframework.config.Configuration} as shown below.
     * If you are interested in other forms of setting up the configuration, the
     * [Reference Guide](https://docs.axonframework.org/) would be an ideal place to start your investigation.
     */
//    @Bean
    public org.axonframework.config.Configuration configuration(CommandBus commandBus,
                                                                EventStore eventStore,
                                                                AxonConfiguration axonConfiguration,
                                                                ApplicationContext applicationContext) {
        EventProcessingModule queryModel = new EventProcessingModule();
        EventProcessingModule commandPublisherModel = new EventProcessingModule();
        EventProcessingConfigurer queryModelConfiguration =
                queryModel.registerSubscribingEventProcessor("queryModel");
        EventProcessingConfigurer commandPublisherConfiguration =
                commandPublisherModel.registerSubscribingEventProcessor("commandPublishingEventHandlers")
                        .registerSaga(CommonRoomManagerSaga.class)
                        .registerSaga(AudienceRoomManagerSaga.class);

        Map<String, Object> eventHandlingComponents = applicationContext.getBeansWithAnnotation(ProcessingGroup.class);

        eventHandlingComponents.forEach((key, value) -> {
            if (key.contains("Listener")) {
                commandPublisherConfiguration.registerEventHandler(conf -> value);
            } else {
                queryModelConfiguration.registerEventHandler(conf -> value);
            }
        });

        org.axonframework.config.Configuration configuration =
                DefaultConfigurer.defaultConfiguration()
                        .configureCommandBus(conf -> commandBus)
                        .configureEventStore(conf -> eventStore)
                        .configureAggregate(RoomAgg.class)
                        .configureAggregate(AudienceJoinRoomAgg.class)
                        .registerModule(queryModel)
                        .registerModule(commandPublisherModel)
                        .buildConfiguration();

        configuration.start();
        return configuration;
    }
}
