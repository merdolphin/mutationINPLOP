package mutationProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getSurroundingResidues {

	Set<Integer> surrondingResidueIDs;

	public getSurroundingResidues(String pdbFile, String resID,
			Double cutoffDistance) throws IOException {

		surrondingResidueIDs = new HashSet<Integer>();

		String resRegEx = "^ATOM\\s+\\d+\\s.*" + " " + resID + " .*$";
		Pattern resPattern = Pattern.compile(resRegEx);

		BufferedReader br = new BufferedReader(
				new FileReader(new File(pdbFile)));

		String line;
		Integer fields = 12;

		ArrayList<Double> X = new ArrayList<Double>();
		ArrayList<Double> Y = new ArrayList<Double>();
		ArrayList<Double> Z = new ArrayList<Double>();

		ArrayList<ArrayList<Double>> XYZ = new ArrayList<ArrayList<Double>>();
		// Read file and get the residues coordinates;

		br.mark(7000000);
		while ((line = br.readLine()) != null) {
			Matcher matchRes = resPattern.matcher(line);
			if (matchRes.matches()) {
				ArrayList<Double> tmpXYZ = new ArrayList<Double>();
				String[] parts = line.split("\\s+");
				Double x0, y0, z0;
				if (parts.length > 11) {
					x0 = Double.parseDouble(parts[6]);
					y0 = Double.parseDouble(parts[7]);
					z0 = Double.parseDouble(parts[8]);
				} else {
					fields = parts.length;
					x0 = Double.parseDouble(parts[5]);
					y0 = Double.parseDouble(parts[6]);
					z0 = Double.parseDouble(parts[7]);
				}
				tmpXYZ.add(x0);
				tmpXYZ.add(y0);
				tmpXYZ.add(z0);
				XYZ.add(tmpXYZ);
			}
		}

		br.reset();

		while ((line = br.readLine()) != null) {
			String[] parts = line.split("\\s+");
			Double x1, y1, z1;
			if (parts.length > 11) {
			x1 = Double.parseDouble(parts[6]);
			y1 = Double.parseDouble(parts[7]);
			z1 = Double.parseDouble(parts[8]);
			}else{
				x1 = Double.parseDouble(parts[5]);
				y1 = Double.parseDouble(parts[6]);
				z1 = Double.parseDouble(parts[7]);
			}
			
			ArrayList<Double> x1y1z1 = new ArrayList<Double>();
			x1y1z1.add(x1);
			x1y1z1.add(y1);
			x1y1z1.add(z1);
			// System.out.println(x1y1z1);
			if (inRange(x1y1z1, XYZ, cutoffDistance)) {
				if(fields == 12){
					surrondingResidueIDs.add(Integer.parseInt(parts[5]));
				}else{
					surrondingResidueIDs.add(Integer.parseInt(parts[4]));
				}
			}

		}
		//System.out.println(surrondingResidueIDs);

		// Test distance()
		//
		// ArrayList<Double> a = new ArrayList<Double>();
		// a.add(1.0);
		// a.add(2.0);
		// a.add(1.0);
		//
		// ArrayList<Double> b = new ArrayList<Double>();
		// b.add(1.0);
		// b.add(0.0);
		// b.add(0.0);
		// System.out.println(distance(a,b));
	}

	public Boolean inRange(ArrayList<Double> x1y1z1,
			ArrayList<ArrayList<Double>> xyz, Double d) {
		for (ArrayList<Double> x0y0z0 : xyz) {
			if (distance(x1y1z1, x0y0z0) <= d * d) {
				// System.out.println(distance(x1y1z1, x0y0z0) +" " + d*d );
				return true;
			}
		}
		return false;
	}

	public Double distance(ArrayList<Double> x1y1z1, ArrayList<Double> x0y0z0) {
		Double d = 0.0;
		for (Integer i = 0; i < 3; i++) {
			d += (x1y1z1.get(i) - x0y0z0.get(i))
					* (x1y1z1.get(i) - x0y0z0.get(i));
		}
		return d;
	}

	public Set<Integer> getSurrondingResidueIDs() {
		return surrondingResidueIDs;
	}

}
