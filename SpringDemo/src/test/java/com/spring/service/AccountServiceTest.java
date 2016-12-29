package com.spring.service;

import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.service.impl.AccountServiceImpl;

/**
 * @author DanhLT The Unit Test for AccountService
 */
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

	/* ============== checkLogin() ============== */
	@Test
	public void testCheckLogin_NullUserNameAndNullPassword() {
		when(testDAO.findByUserNameAndPassword(null, null)).thenReturn(null);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		Assert.assertEquals(expected, service.checkLogin("", ""));
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

	/* ============== checkActive() ============== */

	@Test
	public void testCheckLogin_InactiveAccount() {
		Account expectedAcc = new Account();
		expectedAcc.setId(3);
		expectedAcc.setUserName("danh");
		expectedAcc.setPassword("123");
		expectedAcc.setActive(false);
		when(testDAO.findByUserNameAndPassword("danh", service.encryptMD5("123"))).thenReturn(expectedAcc);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(2, null);
		Assert.assertEquals(expected, service.checkLogin("danh", "123"));
	}

	@Test
	public void testCheckLogin_ActiveAccount() {
		Account expectedAcc = new Account();
		expectedAcc.setId(1);
		expectedAcc.setUserName("minhhuan");
		expectedAcc.setPassword("@huanvip@");
		expectedAcc.setActive(true);
		when(testDAO.findByUserNameAndPassword("minhhuan", service.encryptMD5("@huanvip@"))).thenReturn(expectedAcc);
		Entry<Integer, Account> expected = new AbstractMap.SimpleEntry<Integer, Account>(1, expectedAcc);
		Assert.assertEquals(expected, service.checkLogin("minhhuan", "@huanvip@"));
	}

	/* ============== checkPass() ============== */

	@Test
	public void testRandomPassword() {
		Assert.assertNotEquals("", service.randomPassword(5));
	}

	@Test
	public void testRandomPasswordWrong() {
		Assert.assertEquals("", service.randomPassword(0));
	}

	@Test
	public void testResetPass() {
		Account acc = new Account();
		when(testDAO.updatePassword(Matchers.any(Account.class), Matchers.any(String.class))).thenReturn(true);
		Assert.assertTrue(service.resetPassword(acc, "danhlt@test.com", "danhlt@test.com"));
	}

	/* ============== checkAddNewAccount() ============== */

	@Test
	public void testAddNewAccount() {
		Account acc = new Account();
		acc.setId(1);
		acc.setUserName("minhhuan");
		acc.setActive(true);
		when(testDAO.addAccount(acc)).thenReturn(true);
		Assert.assertTrue(
				service.addNewAccount(acc, "danhlt@test.com", "danhlt@test.com", 5));
	}

	@Test
	public void testAddNewAccountNull() {
		Account acc = null;
		when(testDAO.addAccount(acc)).thenReturn(false);
		Assert.assertFalse(
				service.addNewAccount(acc, "danhlt@test.com", "danhlt@test.com", 5));
	}

	/* ============== updateInfo() ============== */

	@Test
	public void testUpdateInfo_NullInputAccount() {
		Account acc = null;
		when(testDAO.updateInfo(acc)).thenReturn(false);

		Assert.assertEquals(false, service.updateAccountInfo(acc));

	}

	@Test
	public void testUpdateInfo_ValidDate() {
		Account acc = new Account();

		when(testDAO.updateInfo(acc)).thenReturn(true);

		Assert.assertEquals(true, service.updateAccountInfo(acc));

	}
}
