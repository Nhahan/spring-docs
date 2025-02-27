package org.example.expert.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(PersistenceConfig.class)
class PersistenceConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testPersistenceConfigLoads() {
        PersistenceConfig config = context.getBean(PersistenceConfig.class);
        assertNotNull(config);
    }
}
