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

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.service.impl.AccountServiceImpl;

public class AccountServiceTest {

	/**
	 * A auto-injected account DAO
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
	public void testCheckLogin_InactiveAccount() {
		Account expectedAcc = new Account();
		expectedAcc.setId(3);
		expectedAcc.setUserName("baoht");
		expectedAcc.setPassword("23456");
		expectedAcc.setActive(false);
		when(testDAO.findByUserNameAndPassword("baoht", "23456")).thenReturn(expectedAcc);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(2, null);
		Assert.assertEquals(expected, service.checkLogin("baoht", "23456"));
	}

	@Test
	public void testCheckLogin_ActiveAccount() {
		Account expectedAcc = new Account();
		expectedAcc.setId(1);
		expectedAcc.setUserName("minhhuan");
		expectedAcc.setPassword("@huanvip@");
		expectedAcc.setActive(true);
		when(testDAO.findByUserNameAndPassword("minhhuan", "@huanvip@")).thenReturn(expectedAcc);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(1, expectedAcc);
		Assert.assertEquals(expected, service.checkLogin("minhhuan", "@huanvip@"));
	}

	/* updateInfo() */
	@Test
	public void testUpdateInfo_NullInputAndNullDate() {
		when(testDAO.updateInfo(null)).thenReturn(false);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(null, null));
		} catch (ParseException e) {
			fail("Parse exception is thrown unexpectedly!");
		}
	}

	@Test
	public void testUpdateInfo_NullInputAndValidDate() {
		when(testDAO.updateInfo(null)).thenReturn(false);
		try {
			Assert.assertEquals(false, service.updateAccountInfo(null, "24/11/996"));
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
	public void testUpdateInfo_ValidInputAndNullDate() {
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
