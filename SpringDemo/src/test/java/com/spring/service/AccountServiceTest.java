package com.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.service.impl.AccountServiceImpl;

/**
 * @author HuanPM
 * The Unit Test for AccountService
 */
public class AccountServiceTest {

	/**
	 * A auto-injected Account DAO
	 */
	@Mock
	AccountDAO testDAO;

	/**
	 * Account Service whose property DAO is injected and mocked by Mockito
	 */
	@InjectMocks
	AccountServiceImpl service;

	/**
	 * Initiate mocking
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/* checkLogin() */
	@Test
	public void testCheckLogin_NullUserNameAndNullPassword() {
		when(testDAO.findByUserNameAndPassword(null, null)).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin(null, null));
	}

	@Test
	public void testCheckLogin_NullUserNameAndNotNullPassword() {
		when(testDAO.findByUserNameAndPassword(null, "123")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin(null, "123"));
	}

	@Test
	public void testCheckLogin_NotNullUserNameAndNullPassword() {
		when(testDAO.findByUserNameAndPassword("123", null)).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("123", null));
	}

	@Test
	public void testCheckLogin_WrongPassword() {
		when(testDAO.findByUserNameAndPassword("minhhuan", "@huavi")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("minhhuan", "@huavi"));
	}

	@Test
	public void testCheckLogin_WrongUserName() {
		when(testDAO.findByUserNameAndPassword("quangque", "lazziness")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("quangque", "lazziness"));
	}

	@Test
	public void testCheckLogin_WrongUserNameAndWrongPassword() {
		when(testDAO.findByUserNameAndPassword("quangque", "@huanvip@")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("quangque", "@huanvip@"));
	}

	@Test
	public void testFindByUserNameAndPassword_UsernameTooLong() {
		when(testDAO.findByUserNameAndPassword("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa", "123")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa", "123"));
	}

	@Test
	public void testFindByUserNameAndPassword_PasswordTooLong() {
		when(testDAO.findByUserNameAndPassword("quang", "123456789123456789123456789123456789")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("quang", "123456789123456789123456789123456789"));
	}

	@Test
	public void testFindByUserNameAndPassword_UsernameAndPasswordTooLong() {
		when(testDAO.findByUserNameAndPassword("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa",
				"123456789123456789123456789123456789")).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("quangquedsaaaaaaaaaaaaaaaaaajkkkkkkaaaaaaaa",
				"123456789123456789123456789123456789"));
	}

	@Test
	public void testCheckLogin_InactiveAccount() {
		Account expectedinput = new Account();
		expectedinput.setId(3);
		expectedinput.setUserName("baoht");
		expectedinput.setPassword("23456");
		expectedinput.setActive(false);
		when(testDAO.findByUserNameAndPassword("baoht", "23456")).thenReturn(expectedinput);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(2, null);
		Assert.assertEquals(expected, service.checkLogin("baoht", "23456"));
	}

	@Test
	public void testCheckLogin_ActiveAccount() {
		Account expectedinput = new Account();
		expectedinput.setId(1);
		expectedinput.setUserName("minhhuan");
		expectedinput.setPassword("@huanvip@");
		expectedinput.setActive(true);
		when(testDAO.findByUserNameAndPassword("minhhuan", "@huanvip@")).thenReturn(expectedinput);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(1, expectedinput);
		Assert.assertEquals(expected, service.checkLogin("minhhuan", "@huanvip@"));
	}

	/* updateInfo() */
	@Test
	public void testUpdateInfo_NullAccount() {
		when(testDAO.updateInfo(null)).thenReturn(false);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(null, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_NullInputAndNullDate() {
		Account input = new Account("quang", "123", null, null, null, true);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, null));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullFirstName() {
		Account input = new Account("quang", "123", null, "Quang1", null, true);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_NullLastName() {
		Account input = new Account("quang", "123", "Duy1", null, null, true);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testUpdateInfo_NullDate() {
		Account input = new Account();
		input.setId(1);
		input.setUserName("minhhuan");
		input.setFirstName("Bao");
		input.setLastName("Huynh");
		input.setActive(true);
		when(testDAO.updateInfo(input)).thenReturn(false);
		try {
			assertEquals(false, service.updateAccountInfo(input, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_NullInputAndInvalidDate() {
		when(testDAO.updateInfo(null)).thenReturn(false);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(null, "abc"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_NullFirstNameAndNullLastName() {
		when(testDAO.updateInfo(null)).thenReturn(false);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(null, "24/11/1996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_LongFirstName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastName() {
		Account input = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndNullLastName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndNullFirstName() {
		Account input = new Account("quang", "123", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndLongFirstName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndNullDate() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, null));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongLastNameAndNullDate() {
		Account input = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, null));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_LongFirstNameAndLongLastName_NullDate() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(input, null));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, "12/12/1996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastName() {
		Account input = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, "12/12/1996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndNullLastName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndNullFirstName() {
		Account input = new Account("quang", "123", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenThrow(DataIntegrityViolationException.class);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, "12/12/1996"));
			fail("No exception is thrown");
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		} catch (DataIntegrityViolationException e) {

		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndBoundaryFirstName() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, "12/12/1996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndNullDate() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Quang1", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryLastNameAndNullDate() {
		Account input = new Account("quang", "123", "Duy1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_BoundaryFirstNameAndBoundaryLastName_NullDate() {
		Account input = new Account("quang", "123", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				null, true);
		input.setId(1);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			Assert.assertEquals(true, service.updateAccountInfo(input, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}

	}

	@Test
	public void testUpdateInfo_InvalidInputAndNullDate() {
		Account input = new Account();
		when(testDAO.updateInfo(input)).thenReturn(false);
		try {
			assertEquals(false, service.updateAccountInfo(input, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_InvalidInputAndValidDate() {
		Account input = new Account();
		when(testDAO.updateInfo(input)).thenReturn(false);
		try {
			assertEquals(false, service.updateAccountInfo(input, "24/11/996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_InvalidInputAndInvalidDate() {
		Account input = new Account();
		when(testDAO.updateInfo(input)).thenReturn(false);
		try {
			assertEquals(false, service.updateAccountInfo(input, "24//996"));
			fail("Invalid date in unhandled!");
		} catch (ParseException e) {
		}
	}

	@Test
	public void testUpdateInfo_ValidInputAndInvalidDate() {
		Account input = new Account();
		input.setId(1);
		input.setUserName("minhhuan");
		input.setFirstName("Bao");
		input.setLastName("Huynh");
		input.setActive(true);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			service.updateAccountInfo(input, "abc");
			fail("Invalid date in unhandled!");
		} catch (ParseException e) {

		}
	}

	@Test
	public void testUpdateInfo_ValidInputAndValidDate() {
		Account input = new Account();
		input.setId(1);
		input.setUserName("minhhuan");
		input.setFirstName("Bao");
		input.setLastName("Huynh");
		input.setActive(true);
		when(testDAO.updateInfo(input)).thenReturn(true);
		try {
			assertEquals(true, service.updateAccountInfo(input, "11/11/1996"));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}
}
