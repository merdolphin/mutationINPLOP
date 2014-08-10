package mutationProcessing;

/*** written by lina <lina.oahz@gmail.com>
 *  on Sat Aug  9 21:01:50 SGT 2014
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class PlopParameters {

	public PlopParameters(String pdbFile, String mutation, String chain,
			String mutatedFile, String mutatedID) throws IOException {

		//String pwd = "/home/lina/scratch/bii/HN/structure/";
		//String sideChainlistFile = pwd + "sidechains.list1";
		String sideChainlistFile = "sidechains.list1";
		
		Double cutoffDistance = 5.0;

		String inplop1File = pdbFile + "_" + mutation + "INPLOP1";

		String inplop2File = pdbFile + "_" + mutation + "INPLOP2";

		BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File(
				inplop1File)));

		BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(
				sideChainlistFile)));

		BufferedWriter bw3 = new BufferedWriter(new FileWriter(new File(
				inplop2File)));

		bw1.write("datadir   /home/lina/scratch/src/plop-downloaded/Version25_data"
				+"\n"
				+ "load pdb " + mutatedFile + " &" + "\n" + "side predict &"
				+ "\n" + "file sidechains.list1" + "\n" + "write pdb "
				+ pdbFile + "_" + mutation + "_r.pdb" + "\n");

		bw1.close();

		
		String chainlabel = chain;
		
		if (chain != " ") {
			bw2.write(chain + ":" + mutatedID);
		} else {
			chainlabel = "_";
			bw2.write("_" + ":" + mutatedID);
		}
		bw2.newLine();
		bw2.close();

		getSurroundingResidues getting = new getSurroundingResidues(
				mutatedFile, mutatedID, cutoffDistance);
		
		Set<Integer> residues = getting.getSurrondingResidueIDs();
		
		String minimizedResidues = "";
		
		for (Integer res : residues){
			
			minimizedResidues += "res " + chainlabel + ":" + res 
					+ " " + chainlabel + ":" + res + " & \n" ; 
			
		}
		
		bw3.write("datadir   /home/lina/scratch/src/plop-downloaded/Version25_data"
				+ "\n"
				+ "load pdb " + pdbFile + "_" + mutation + "_r.pdb" 
				+ "\n"
				+ "minimize & " 
				+ "\n" 
				+ minimizedResidues.substring(0,minimizedResidues.length()-3)
				+ "\n" 
				+ "write pdb " + pdbFile + "_" + mutation + "_r_min.pdb" + "\n");
		bw3.close();
	}

}
