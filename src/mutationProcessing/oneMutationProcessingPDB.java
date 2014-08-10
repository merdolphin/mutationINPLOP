package mutationProcessing;

/*** written by lina <lina.oahz@gmail.com>
 *  on Sat Aug  9 21:01:50 SGT 2014
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class oneMutationProcessingPDB {

	String mutatedID;
	
	public String getMutatedID() {
		return mutatedID;
	}

	public oneMutationProcessingPDB(String pdbFile, String mutation,
			String chain, String outputFile)
			throws IOException {

		mutatedID = mutation.replaceAll("[A-Z]", "");
		String [] mutatedElements = mutation.split("");
		String tobeMutated = mutation.split("")[1];
		String mutatedTo = mutatedElements[mutatedElements.length-1];
		ResiduesAbbreviation abbrev = new ResiduesAbbreviation();
		String tobeMutatedRes = abbrev.getAbbrToRes().get(tobeMutated);
		String mutatedToRes = abbrev.getAbbrToRes().get(mutatedTo);
		System.out.println("Mutating Residue " + tobeMutatedRes + " to "
				+ mutatedToRes + " ...\n");

		String atomRegEx = "^ATO.*$";
		Pattern atomPattern = Pattern.compile(atomRegEx);

		String mutatedRegEx = "^ATO.*" + chain + " " + mutatedID + " .*$";
		Pattern mutatedPattern = Pattern.compile(mutatedRegEx);

		BufferedReader br = new BufferedReader(
				new FileReader(new File(pdbFile)));

		String line;

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				outputFile)));

		Integer backboneCount = 0;

		while ((line = br.readLine()) != null) {

			Matcher atomMatch = atomPattern.matcher(line);
			Matcher mutatedMatch = mutatedPattern.matcher(line);

			if (atomMatch.matches()) {

				if (mutatedMatch.matches()) {
					backboneCount++;
					if (backboneCount <= 5) {
						String mutatedLine = line.replaceAll(tobeMutatedRes,
								mutatedToRes);

						bw.write(mutatedLine);
						bw.newLine();
					}
				} else {
					bw.write(line);
					bw.newLine();
				}
			}
		}
		br.close();
		bw.close();
	}

}
