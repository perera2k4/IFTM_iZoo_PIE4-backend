package com.backend.izoo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
	// Habilita auditing automático para @CreatedDate e @LastModifiedDate
}
