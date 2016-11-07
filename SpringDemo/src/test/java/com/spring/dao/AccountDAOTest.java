package com.spring.dao;

import java.io.File;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/spring/configuration/test.xml" })
public class AccountDAOTest {

	@Autowired
	AccountDAO dao;

	@Autowired
	DataSource dataSource;

	IDatabaseTester dbTester;

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
	public void testFindByUserNameAndPassword_ValidAccount() {
		Assert.assertNotNull(dao.findByUserNameAndPassword("quang", "123"));
	}

}
