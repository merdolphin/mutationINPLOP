package mutationProcessing;

import java.util.ArrayList;
import java.util.HashMap;

public class ResiduesAbbreviation {
	
	HashMap<String, String> abbrToRes;
	HashMap<String, String> resToAbbr;
	
	public ResiduesAbbreviation() {

		 abbrToRes = new HashMap<String, String>();
		 resToAbbr = new HashMap<String, String>();

		String[] abbr = { "A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",
				"L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
		String[] residues = { "ALA", "ARG", "ASN", "ASP", "CYS", "GLN", "GLU",
				"GLY", "HIS", "ILE", "LEU", "LYS", "MET", "PHE", "PRO", "SER",
				"THR", "TRP", "TYR", "VAL" };

		for (Integer i = 0; i < abbr.length; i++) {
			abbrToRes.put(abbr[i], residues[i]);
			resToAbbr.put(residues[i], abbr[i]);
		}

	}

	public HashMap<String, String> getAbbrToRes() {
		return abbrToRes;
	}

	public HashMap<String, String> getResToAbbr() {
		return resToAbbr;
	}

}
