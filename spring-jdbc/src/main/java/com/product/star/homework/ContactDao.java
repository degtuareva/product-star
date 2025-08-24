package com.product.star.homework;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao {

    private static final String GET_ALL_SQL = "SELECT id, name, surname, phone_number, email FROM contacts";
    private static final String GET_BY_ID_SQL = GET_ALL_SQL + " WHERE id = :id";
    private static final String INSERT_SQL = "INSERT INTO contacts (name, surname, phone_number, email) VALUES (:name, :surname, :phoneNumber, :email)";
    private static final String UPDATE_PHONE_SQL = "UPDATE contacts SET phone_number = :phoneNumber WHERE id = :id";
    private static final String UPDATE_EMAIL_SQL = "UPDATE contacts SET email = :email WHERE id = :id";
    private static final String DELETE_SQL = "DELETE FROM contacts WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final ContactRowMapper ROW_MAPPER = new ContactRowMapper();

    public ContactDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(GET_ALL_SQL, ROW_MAPPER);
    }

    public Contact getContactById(long id) {
        return jdbcTemplate.queryForObject(GET_BY_ID_SQL, new MapSqlParameterSource("id", id), ROW_MAPPER);
    }

    public long addContact(Contact contact) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", contact.getName())
                .addValue("surname", contact.getSurname())
                .addValue("phoneNumber", contact.getPhone())
                .addValue("email", contact.getEmail());

        jdbcTemplate.update(INSERT_SQL, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    public void updatePhoneNumber(long id, String phoneNumber) {
        jdbcTemplate.update(UPDATE_PHONE_SQL, new MapSqlParameterSource("id", id).addValue("phoneNumber", phoneNumber));
    }

    public void updateEmail(long id, String email) {
        jdbcTemplate.update(UPDATE_EMAIL_SQL, new MapSqlParameterSource("id", id).addValue("email", email));
    }

    public void deleteContact(long id) {
        jdbcTemplate.update(DELETE_SQL, new MapSqlParameterSource("id", id));
    }
}
