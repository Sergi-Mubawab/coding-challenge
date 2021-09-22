package com.n26.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.n26.constants.Constants;

public class WeakConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	private static final int CLEANER_TIME = Constants.ACCEPTED_SECONDS * 1000;

	private static final Logger logger = LoggerFactory.getLogger(WeakConcurrentHashMap.class);

	private transient Map<K, Long> timeMap = new ConcurrentHashMap<>();
	private long expiryInMillis = CLEANER_TIME;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");

	public WeakConcurrentHashMap() {
		initialize();
	}

	void initialize() {
		new CleanerThread().start();
	}

	@Override
	public V put(K key, V value) {
		Date date = new Date();
		timeMap.put(key, date.getTime());
		logger.info("Inserting : " + sdf.format(date) + " : " + key + " : " + value);
		return super.put(key, value);
	}

	@Override
	public void clear() {
		logger.info("Removing all: " + sdf.format(new Date()));
		super.clear();
	}

	class CleanerThread extends Thread {
		@Override
		public void run() {
			logger.info("Initiating Cleaner Thread..");
			while (true) {
				cleanMap();
				try {
					Thread.sleep(expiryInMillis / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void cleanMap() {
			long currentTime = new Date().getTime();
			for (K key : timeMap.keySet()) {
				if (currentTime > (timeMap.get(key) + expiryInMillis)) {
					V value = remove(key);
					timeMap.remove(key);
					logger.info("Removing : " + sdf.format(new Date()) + " : " + key + " : " + value);
				}
			}
		}
	}
}
