package com.molinari.utility.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.molinari.utility.controller.ControlloreBase;

public class ConnectionPoolTest {

	private static final int MAX_CONNECTIONS = 2;

	private static class TestConnectionPool extends ConnectionPool {

		private List<Connection> createdConnections;

		@Override
		protected Connection newConnection() {
			if (createdConnections == null) {
				createdConnections = new ArrayList<>();
			}
			final Connection cn = mock(Connection.class);
			createdConnections.add(cn);
			return cn;
		}

		List<Connection> getCreatedConnections() {
			if (createdConnections == null) {
				createdConnections = new ArrayList<>();
			}
			return createdConnections;
		}

		@Override
		protected String getPassword() {
			return null;
		}

		@Override
		protected String getUser() {
			return null;
		}

		@Override
		protected String getDriver() {
			return "java.lang.Object";
		}

		@Override
		protected String getDbUrl() {
			return "jdbc:test";
		}

		@Override
		protected int getNumeroConnessioniDisponibili() {
			return MAX_CONNECTIONS;
		}
	}

	@Before
	public void setUp() {
		ControlloreBase.getSingleton().setLog(Logger.getLogger("ConnectionPoolTest"));
	}

	@Test
	public void useConnectionRilasciaSenzaChiudereERiusaLaConnessione() throws SQLException {
		final TestConnectionPool pool = new TestConnectionPool();
		assertEquals(MAX_CONNECTIONS, pool.getCreatedConnections().size());

		final Connection first = pool.useConnection(cn -> cn);
		final Connection second = pool.useConnection(cn -> cn);
		assertNotSame(first, second);

		final Connection third = pool.useConnection(cn -> cn);
		assertSame(first, third);

		assertEquals(MAX_CONNECTIONS, pool.getCreatedConnections().size());
		verify(first, never()).close();
		verify(second, never()).close();
	}

	@Test
	public void releaseNewConnectionNonSuperaIlMassimo() {
		final TestConnectionPool pool = new TestConnectionPool();

		pool.releaseNewConnection();
		pool.releaseNewConnection();

		assertEquals(MAX_CONNECTIONS, pool.getCreatedConnections().size());
	}

	@Test
	public void scartaConnessioniChiuseSenzaRestituirleAlPool() throws SQLException {
		final TestConnectionPool pool = new TestConnectionPool();
		final Connection closed = pool.getCreatedConnections().get(0);
		when(closed.isClosed()).thenReturn(true);

		final Connection used = pool.useConnection(cn -> cn);

		assertNotSame(closed, used);
		verify(closed).close();
	}
}
