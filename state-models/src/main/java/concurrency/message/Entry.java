package concurrency.message;

public class Entry<R, P> extends Port<R> {
	private final Port<CallMsg<R, P>> cp = new Port<>();
	private CallMsg<R, P> cm;

	public P call(R req) throws InterruptedException {
		Channel<P> clientChan = new Channel<P>();
		cp.send(new CallMsg<R, P>(req, clientChan));
		return clientChan.receive();
	}

	public R accept() throws InterruptedException {
		cm = cp.receive();
		return cm.request;
	}

	public void reply(P res) throws InterruptedException {
		cm.replychan.send(res);
	}

	private static class CallMsg<R, P> {
		R request;
		Channel<P> replychan;

		CallMsg(R m, Channel<P> c) {
			request = m;
			replychan = c;
		}
	}
}