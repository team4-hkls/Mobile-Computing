package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* input: student list of collected data files
 * output: clusters by all neighbors (regardless of signal strength)
 *         ex) [[a,b,c],[d],[e]] (ArrayList of Set)
 */

public class ClusteringModule {
	static final int k = 3;
	ArrayList<String> studentList;
	Map<String, Set<String>> allNeighbors;

	public ArrayList<Set<String>> start(ArrayList<String> studentList) {
		this.studentList = studentList;
		allNeighbors = new HashMap<String, Set<String>>();

		String path = ClusteringModule.class.getResource("").getPath();
		ArrayList<String> folders = getFolderList(path);

		// For each device, find neighbors
		Iterator<String> it = folders.iterator();
		while (it.hasNext()) {
			String id = it.next();
			File folder = new File(path + "\\" + id);
			allNeighbors.put(id, findNeighbors(folder));
		}
		
		return clustering();
	}
	
	// calculate neighbors of each device.
	private Set<String> findNeighbors(File folder) {
		Set<String> neighbors = new HashSet<String>();
		String[] txts = folder.list();
		try {
			for (int i = 0; i < txts.length; i++) {
				File file = new File(folder.getPath() + "\\" + txts[i]); // text file
				
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while ((line = br.readLine()) != null) {
					String[] tokens = line.split("\\s+");
					
					// format: date + time + MACaddress(=ID) + SS + txPower
					if (tokens.length > 4 && studentList.contains(tokens[2])) {
						neighbors.add(tokens[2]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return neighbors;
	}
	
	private ArrayList<Set<String>> clustering() {
		ArrayList<Set<String>> clusters = new ArrayList<Set<String>>();
		for(String student : allNeighbors.keySet()) {
			if(!addToExistClusters(clusters, student)) {
				Set<String> temp = allNeighbors.get(student);
				temp.add(student);
				clusters.add(temp);
			}
		}
		
		return clusters;
	}
	
	private boolean addToExistClusters(ArrayList<Set<String>> clusters, String student) {
		for(Set<String> key : clusters) {
			if(key.contains(student)) {
				clusters.remove(key);
				key.addAll(allNeighbors.get(student));
				clusters.add(key);
				return true;
			}
		}
		
		return false;
	}

	private ArrayList<String> getFolderList(String path) {
		File file = new File(path);
		String[] folders = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		return new ArrayList<String>(Arrays.asList(folders));
	}

}