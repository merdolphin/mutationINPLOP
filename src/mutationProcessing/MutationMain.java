package mutationProcessing;

/*** written by lina <lina.oahz@gmail.com>
 *  on Sat Aug  9 21:01:50 SGT 2014
 *  "The trouble with being punctual is that nobody's there to appreciate it."  
 */

import java.io.IOException;

public class MutationMain {

	public static void main(String[] args) throws IOException {
		
//		String pwd = "/home/lina/scratch/bii/HN/structure/";
//		
//		String pdbFile = pwd + "3ti6A.pdb";
//		String mutation = "S246N";
		String chain = "A";
		Double distance = 5.0; // Surrounding cutoff
		
		if(args.length < 2){
			System.out.println("Usage: java7 -jar mutateINPLOP.jar FILE.pdb E119G"
					+ "\n"
					+ "Example: java7 -jar mutateINPLOP.jar 3ti6A.pdb E119G");
		}

		String pdbFile = args[0];
		String mutation = args[1];

		// System.out.println(args.length);
		if (args.length - 1 >= 2) {
			chain = args[2];
		}
		
		String mutatedOutputFile = pdbFile.replaceAll(".pdb", "") 
				+ mutation + ".pdb";
		
		String pdb = pdbFile.replace(".pdb", "");

		oneMutationProcessingPDB mutate = new oneMutationProcessingPDB(
				pdbFile,
				mutation, 
				chain,
				mutatedOutputFile);
		
		String mutatedID = mutate.getMutatedID();
		
		PlopParameters sideChainPred = new PlopParameters(
				pdbFile,
				mutation, 
				chain,
				mutatedOutputFile,
				mutatedID);
	}
}
