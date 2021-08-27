package concurrency.parcel;

import concurrency.time.TimeStop;

public interface ParcelMover {
	void enter(Parcel p) throws TimeStop;
}