package com.n26.database;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.n26.domain.Transaction;

class DelayedObject implements Delayed {

	private static final long DELAYED_TIME_SECONDS = 60;

	private Transaction transaction;
	private long time;

	public DelayedObject(Transaction transaction) {
		this.transaction = transaction;
		this.time = System.currentTimeMillis() + DELAYED_TIME_SECONDS;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff = time - System.currentTimeMillis();
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed obj) {
		if (this.time < ((DelayedObject) obj).time) {
			return -1;
		}
		if (this.time > ((DelayedObject) obj).time) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "\n{" + "transaction=" + transaction.toString() + ", time=" + time + "}";
	}
}