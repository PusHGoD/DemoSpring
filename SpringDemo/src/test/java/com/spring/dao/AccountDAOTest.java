package com.spring.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.entity.Account;
import com.spring.entity.Role;

/**
 * @author HuanPM+QuangNND The Unit Test for AccountDAO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/spring/configuration/test.xml" })
public class AccountDAOTest {

	/**
	 * Autowired Account DAO
	 */
	@Autowired
	AccountDAO dao;

	/**
	 * Autowired data source
	 */
	@Autowired
	DataSource dataSource;

	/**
	 * A DBUNit Database Tester
	 */
	IDatabaseTester dbTester;

	/**
	 * Simple date format
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Before
	public void setUp() throws Exception {
		dbTester = new DataSourceDatabaseTester(dataSource, "springdemo");
		IDataSet dataSet = getDataSet();
		dbTester.setDataSet(dataSet);
		dbTester.onSetup();
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("src/test/java/com/spring/configuration/user.xml"));
	}

	@After
	public void tearDown() throws Exception {
		dbTester.onTearDown();
	}

	/* findByUserNameAndPassword */

	@Test
	public void testFindByUserNameAndPassword_WrongPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword("quang", "1234"));
	}

	@Test
	public void testFindByUserNameAndPassword_CorrectUsernameAndPassword() {
		Account result = dao.findByUserNameAndPassword("quang", "123");
		Assert.assertEquals(new Integer(1), result.getId());
		Assert.assertEquals("quang", result.getUserName());
		Assert.assertEquals("123", result.getPassword());
		Assert.assertEquals("duy", result.getFirstName());
		Assert.assertEquals("quang", result.getLastName());
	}

	/* updateInfo */

	@Test
	public void testUpdateInfo_NullAccount() {
		Assert.assertEquals(false, dao.updateInfo(null));
	}

	@Test
	public void testUpdateInfo_LongFirstName() {
		Account acc = null;
		Role role = new Role("admin");
		try {
			acc = new Account(role, "quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1",
					sdf.parse("12/12/1996"), "quang@mail.com", true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_LongLastNameAndLongFirstName() {
		Account acc = null;
		Role role = new Role("admin");
		try {
			acc = new Account(role, "quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", sdf.parse("12/12/1996"), "quang@mail.com", true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndBoundaryFirstName() {
		Account acc = null;
		Role role = new Role("admin");
		try {
			acc = new Account(role, "quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					sdf.parse("12/12/1996"), "quang@mail.com", true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}

	}

	@Test
	public void testUpdateInfo_ValidInputAndValidDate() {
		Account acc = null;
		Role role = new Role("admin");
		try {
			acc = new Account(role, "quang", "123", "aaaaaaaaaaaaaaaa", "Quang1", sdf.parse("12/12/1996"),
					"quang@mail.com", true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	/* Update password */

	@Test
	public void testUpdatePassword_NullAccountAndNullPassword() {
		Assert.assertEquals(false, dao.updatePassword(null, null));
	}

	@Test
	public void testUpdatePassword_ValidAccountAndValidPassword() {
		Assert.assertEquals(true, dao.updatePassword(dao.findByUserNameAndPassword("quang", "123"), "12345"));
	}

	@Test
	public void testUpdatePassword_InvalidAccountAndValidPassword() {
		Assert.assertEquals(false, dao.updatePassword(dao.findByUserNameAndPassword("quangque", "123"), "12345"));
	}

	/* Add account */

	@Test
	public void testAddAccount_NullAccount() {
		Assert.assertEquals(false, dao.addAccount(null));
	}

	@Test
	public void testAddAccount_ValidAccount() {
		Role role = new Role();
		role.setId(1);
		Account acc;
		try {
			acc = new Account(role, "quang", "123", "Duy1", "Quang1", sdf.parse("12/12/1996"), "quang@mail.com", true);
			Assert.assertEquals(true, dao.addAccount(acc));
		} catch (ParseException e) {
			fail("Parse exception thrown unexpectedly!");
		}

	}
}
