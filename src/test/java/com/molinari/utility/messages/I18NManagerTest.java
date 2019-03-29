package com.molinari.utility.messages;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class I18NManagerTest {

	@Spy
	I18NManager undertest;
	
	@Test
	public void test() {
		Mockito.when(undertest.getMessages()).thenReturn(new ArrayList<ResourceBundle>());
		Mockito.when(undertest.getMessaggio("test")).thenReturn("Test per sostituiore @, @ e @");
		
		String messaggio = undertest.getMessaggio("test", new String[] {"pippo", "pluto", "paperino"});
	
		Assert.assertEquals("Test per sostituiore pippo, pluto e paperino", messaggio);
	}

}
