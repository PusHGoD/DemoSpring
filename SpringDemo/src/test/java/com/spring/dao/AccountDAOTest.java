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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/spring/configuration/test.xml" })
public class AccountDAOTest {

	@Autowired
	AccountDAO dao;

	@Autowired
	DataSource dataSource;

	IDatabaseTester dbTester;

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
	public void testFindByUserNameAndPassword_NullUserNameAndNullPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword(null, null));
	}

	@Test
	public void testFindByUserNameAndPassword_NullUserNameAndNotNullPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword(null, "123"));
	}

	@Test
	public void testFindByUserNameAndPassword_NotNullUserNameAndNullPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword("quang", null));
	}

	@Test
	public void testFindByUserNameAndPassword_WrongPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword("quang", "1234"));
	}

	@Test
	public void testFindByUserNameAndPassword_WrongUserName() {
		Assert.assertNull(dao.findByUserNameAndPassword("quangque", "123"));
	}

	@Test
	public void testFindByUserNameAndPassword_WrongUserNameAndWrongPassword() {
		Assert.assertNull(dao.findByUserNameAndPassword("quangque", "qxyzt"));
	}

	@Test
	public void testFindByUserNameAndPassword_UsernameTooLong() {
		Assert.assertNull(dao.findByUserNameAndPassword("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa", "123"));
	}

	@Test
	public void testFindByUserNameAndPassword_PasswordTooLong() {
		Assert.assertNull(dao.findByUserNameAndPassword("quang", "123456789123456789123456789123456789"));
	}

	@Test
	public void testFindByUserNameAndPassword_UsernameAndPasswordTooLong() {
		Assert.assertNull(dao.findByUserNameAndPassword("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa",
				"123456789123456789123456789123456789"));
	}

	/* updateInfo */

	@Test
	public void testUpdateInfo_NullAccount() {
		Assert.assertEquals(false, dao.updateInfo(null));
	}

	@Test
	public void testUpdateInfo_NullInput() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, null, null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, "Quang1", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_NullLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", null, sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_NullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", "Quang1", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullFirstNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, "Quang1", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullLastNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", null, null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullFirstNameAndNullLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, null, sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_LongFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", sdf.parse("12/12/1996"),
					true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_LongLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndNullLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndNullFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndLongFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
			fail("No exception is thrown!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndLongLastNameNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}

	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndNullLastName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (ParseException e) {
			
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndNullFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (ParseException e) {
			
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndBoundaryFirstName() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}

	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndNullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndBoundaryLastName_NullDate() {
		Account acc = null;
		try {
			acc = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null,
					true);
			acc.setId(1);
			Assert.assertEquals(false, dao.updateInfo(acc));
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_ValidInputAndValidDate() {
		Account acc = null;
		try {
			acc = new Account("quang1", "321", "Nguyen", "Quang1", sdf.parse("12/12/1996"), true);
			acc.setId(1);
			Assert.assertEquals(true, dao.updateInfo(acc));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			fail("Parse exception is thrown unexpectedly!");
		}
	}
}
