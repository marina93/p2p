import java.net.*;
import java.io.*;

/**
 * @author jcerecedameca@hawk.iit.edu - A20432616
 * @author mtorresgomez@hawk.iit.edu - A20432664
 * 
 *         PeerServerHandler Class. It is in charge of handling the request that
 *         the Peers receive.
 */
class PeerPullServerHandler1 extends Thread {
	private Socket socket = new Socket();
	private String dir = "";
	private String s = "";
	private int version;
	private long ttr;
	private int origin;

	/**
	 * Constructor of the class. It creates a handler and receives the socket to
	 * send the file and the Peer directory in which the file is located.
	 * 
	 * @param socket
	 * @param dir
	 */
	public PeerPullServerHandler1(Socket socket, String dir, String s, int version, long ttr, int origin) {
		super("PeerServerHandler");
		this.socket = socket;
		this.dir = dir;
		this.s = s;
		this.version = version;
		this.ttr = ttr;
		this.origin = origin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 * 
	 * Creates a Buffer Stream to send the file and sends it.
	 */
	@Override
	public void run() {
		try {
			final File reqFile = new File(dir + "/" + s);
			System.out.println("The file " + dir + "/" + reqFile.getName() + " is going to be sent.");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(version + "%" + ttr + "%" + origin);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(reqFile));
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			byte[] byteArray = new byte[(int) reqFile.length()];
			int i;
			System.out.println("Sending " + reqFile.getName() + "(" + byteArray.length + " bytes)");
			while ((i = bis.read(byteArray)) != -1) {
				bos.write(byteArray, 0, i);
			}
			out.close();
			bis.close();
			bos.close();
			System.out.println("Done.");

		} catch (Exception e) {
			System.out.println("Aqui 2");
			System.err.println(e);
		}
	}
}
