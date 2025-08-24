package com.product.star.homework;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

public class ContactDaoTests {

    private EmbeddedDatabase db;
    private ContactDao contactDao;

    @Before
    public void setup() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")  // создание таблиц
                .addScript("classpath:data.sql")    // начальные данные, опционально
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(db);
        contactDao = new ContactDao(new org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate(jdbcTemplate));
    }

    @After
    public void teardown() {
        db.shutdown();
    }

    @Test
    public void testGetAllContacts() {
        List<Contact> contacts = contactDao.getAllContacts();
        Assert.assertNotNull(contacts);
        Assert.assertEquals(2, contacts.size());
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact();
        contact.setName("Alice");
        contact.setSurname("Johnson");
        contact.setPhone("1112223333");
        contact.setEmail("alice.johnson@example.com");

        Long id = contactDao.addContact(contact);
        Assert.assertNotNull(id);

        Contact savedContact = contactDao.getContactById(id);
        Assert.assertEquals("Alice", savedContact.getName());
    }

    @Test
    public void testUpdatePhone() {
        contactDao.updatePhoneNumber(1L, "9999999999");
        Contact contact = contactDao.getContactById(1L);
        Assert.assertEquals("9999999999", contact.getPhone());
    }

    @Test
    public void testUpdateEmail() {
        contactDao.updateEmail(2L, "new.email@example.com");
        Contact contact = contactDao.getContactById(2L);
        Assert.assertEquals("new.email@example.com", contact.getEmail());
    }

    @Test
    public void testDeleteContact() {
        contactDao.deleteContact(1L);
        List<Contact> contacts = contactDao.getAllContacts();
        Assert.assertEquals(1, contacts.size());
    }
}
