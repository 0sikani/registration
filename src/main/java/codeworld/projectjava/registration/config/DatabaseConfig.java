package codeworld.projectjava.registration.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public JdbcTemplate jdbcTemp(DataSource dtsource){
        return new JdbcTemplate(dtsource);
    }

    // @Bean
    // public NamedParameterJdbcTemplate npJdbcTemp(DataSource dtSource){
    //     return new NamedParameterJdbcTemplate(dtSource);
    // }
}
