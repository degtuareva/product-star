package com.product.star.homework;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setName(rs.getString("name"));
        contact.setSurname(rs.getString("surname"));
        contact.setPhone(rs.getString("phone_number"));
        contact.setEmail(rs.getString("email"));
        return contact;
    }
}
