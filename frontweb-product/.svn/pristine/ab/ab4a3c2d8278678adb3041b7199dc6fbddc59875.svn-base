package com.oracle.product.httpclient.utils;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class ClientEvictExpiredConnections extends Thread {
	private final HttpClientConnectionManager hccm;

	private volatile boolean shutdown;

	public ClientEvictExpiredConnections(HttpClientConnectionManager hccm) {
		this.hccm = hccm;
		this.start();
	}

	@Override
	public void run() {
		while (!shutdown) {
			synchronized (this) {
				try {
					wait(5000);
					hccm.closeExpiredConnections();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}

}
