package com.cesarcaceres.springchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringChatApplicationTests {

	@Autowired
	private MockMvc mvc;

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

}
