package nc.oliweb.jdbctemplate.service;

import nc.oliweb.jdbctemplate.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Service
public class TestService {

    private final Logger log = LoggerFactory.getLogger(TestService.class);

    private JdbcTemplate jdbcTemplate;

    public TestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void start() {
        log.info("Test Service initializing...");
        List<MappedValue> listUser = jdbcTemplate.query("select * from jhi_user, jhi_user_authority, jhi_authority where jhi_user.id = jhi_user_authority.user_id and jhi_user_authority.authority_name = jhi_authority.name", new CustomRowMapper());
        Map<String, List<MappedValue>> map = listUser.stream().collect(groupingBy(MappedValue::getAuthorityName));
        log.info("Test Service finish initializing...");
    }

    static class MappedValue {
        String username;
        String authorityName;

        public String getUsername() {

            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAuthorityName() {
            return authorityName;
        }

        public void setAuthorityName(String authorityName) {
            this.authorityName = authorityName;
        }
    }

    static class CustomRowMapper implements RowMapper<MappedValue> {
        @Override
        public MappedValue mapRow(ResultSet rs, int rowNum) throws SQLException {
            MappedValue mappedValue = new MappedValue();
            mappedValue.setUsername(rs.getString("login"));
            mappedValue.setAuthorityName(rs.getString("name"));
            return mappedValue;
        }
    }
}
