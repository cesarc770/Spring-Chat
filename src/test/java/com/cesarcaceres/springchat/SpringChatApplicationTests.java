package com.cesarcaceres.springchat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringChatApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	WebDriver webDriver;

	@Test
	public void login() throws Exception
	{
		this.mvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("login"));

	}

	@Test
	public void index() throws Exception
	{
		this.mvc.perform(get("/home")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("chat"));

	}

	@Test
	public void loginAndJoin () throws Exception {
		webDriver = new HtmlUnitDriver(true);
		webDriver.get("http://localhost:8080");
		WebElement username = webDriver.findElement(By.id("username"));
		username.sendKeys("Cesar");

		WebElement login_form = webDriver.findElement(By.className("submit"));
		login_form.click();


		(new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement username1 = webDriver.findElement(By.id("username"));
				System.out.println("username: " + username1.getText());
				return username1.getText().equals("Cesar");
			}
		});
	}

	@Test
	public void chat () throws Exception {
		webDriver = new HtmlUnitDriver(true);
		webDriver.get("http://localhost:8080/home?username=Cesar");

		WebElement inputBox = webDriver.findElement(By.id("msg"));
		inputBox.sendKeys("Hello");

		WebElement send_btn = webDriver.findElement(By.id("send-btn"));
		send_btn.click();

		(new WebDriverWait(webDriver, 5)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement textbox1 = webDriver.findElement(By.className("msg-text"));
				return textbox1.getText().contains("Hello");
			}
		});
	}

	@Test
	public void logout () throws Exception {
		webDriver = new HtmlUnitDriver(true);
		webDriver.get("http://localhost:8080/home?username=Cesar");

		WebElement logout_btn = webDriver.findElement(By.id("logout-btn"));
		logout_btn.click();

		(new WebDriverWait(webDriver, 5)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement login_btn = webDriver.findElement(By.className("act-but"));
				return login_btn.isDisplayed();
			}
		});
	}

}
