import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import peersim.core.*;
import peersim.config.Configuration;

public class TrafficGene implements Control {
	private static final String PAR_PROT = "protocol";
	private static final String PAR_NUMREQPERCLIENT = "numReqPerClient";
	private static final String PAR_STARTUPFILEPATH = "startupFilePath";

	private final int pid;
	private final int numReqPerClient;
	private final String startupFilePath;

	public TrafficGene(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		numReqPerClient = Configuration.getInt(prefix + "." + PAR_NUMREQPERCLIENT);
		startupFilePath = Configuration.getString(prefix + "." + PAR_STARTUPFILEPATH);
	}

	public boolean execute() {
		int size = Network.size();
		int numClient = ((PeerProtocol)(((Node) Network.get(0)).getProtocol(pid))).numClient;
		Library.numAllOpera = (long)numClient * (long)numReqPerClient;
		for (int i = 0; i < numClient; i++)
			((PeerProtocol)(((Node) Network.get(i)).getProtocol(pid))).numOpera = numReqPerClient;
	
		String path = startupFilePath + "startup_file_" + Integer.toString(size) + ".txt";
		BufferedReader br = null;
		TreeSet<Comparator> ts = new TreeSet<Comparator>();
		try {
			br = new BufferedReader(new FileReader(path));
			for (int i = 0; i < numClient; i++) {
				long wait = Long.parseLong(br.readLine());
				Comparator comp = new Comparator(wait, i);
				ts.add(comp);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Comparator> it = ts.iterator();
		while (it.hasNext()) {
			Comparator comp = it.next();
			Node node = (Node) Network.get(comp.id);
			PeerProtocol pp = (PeerProtocol) node.getProtocol(pid);
			pp.doRequest(node, pid, comp.wait);
		}
		return false;
	}
}