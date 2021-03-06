/*
 * Copyright (C) 2016 Red Hat, Inc.
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
package io.syndesis.project.converter;

import java.io.IOException;
import java.util.List;

import io.syndesis.dao.extension.ExtensionDataManager;
import io.syndesis.dao.manager.DataManager;
import io.syndesis.project.converter.visitor.ConnectorStepVisitor;
import io.syndesis.project.converter.visitor.DataMapperStepVisitor;
import io.syndesis.project.converter.visitor.ExpressionFilterStepVisitor;
import io.syndesis.project.converter.visitor.ExtensionStepVisitor;
import io.syndesis.project.converter.visitor.RuleFilterStepVisitor;
import io.syndesis.project.converter.visitor.StepVisitorFactory;
import io.syndesis.project.converter.visitor.StepVisitorFactoryRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProjectGeneratorProperties.class)
public class ProjectGeneratorConfiguration {
    @Autowired
    private ProjectGeneratorProperties properties;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private ExtensionDataManager extensionDataManager;

    @Bean
    public ProjectGenerator projectConverter(StepVisitorFactoryRegistry registry) throws IOException {
        return new DefaultProjectGenerator(
            properties,
            registry,
            dataManager,
            extensionDataManager
        );
    }

    @Bean
    public StepVisitorFactoryRegistry registry(List<StepVisitorFactory<?>> factories) {
        return new StepVisitorFactoryRegistry(factories);
    }

    @Bean
    public StepVisitorFactory<ConnectorStepVisitor> endpointStepVisitorFactory() {
        return new ConnectorStepVisitor.EndpointFactory();
    }

    @Bean
    public StepVisitorFactory<ConnectorStepVisitor> conenctorStepVisitorFactory() {
        return new ConnectorStepVisitor.ConnectorFactory();
    }

    @Bean
    public StepVisitorFactory<DataMapperStepVisitor> dataMapperStepVisitorFactory() {
        return new DataMapperStepVisitor.Factory();
    }

    @Bean
    public StepVisitorFactory<RuleFilterStepVisitor> ruleFilterStepVisitorFactory() {
        return new RuleFilterStepVisitor.Factory();
    }

    @Bean
    public StepVisitorFactory<ExpressionFilterStepVisitor> expressionFilterStepVisitorFactory() {
        return new ExpressionFilterStepVisitor.Factory();
    }

    @Bean
    public StepVisitorFactory<ExtensionStepVisitor> extensionStepVisitorFactory() {
        return new ExtensionStepVisitor.Factory();
    }
}
