package application;

import java.util.ArrayList;
import java.util.Objects;

public class Lettre {
	
	char lettre;
	private ArrayList<Integer> positions;
	
	public Lettre(char _lettre, String mot) {
		super();
		this.lettre = _lettre;
		this.positions = new ArrayList<Integer>();
		
		for (int i=0;i<mot.length();i++) {
			if(lettre==mot.charAt(i)) {
				positions.add(i);
			}
		}
		
	}

	public char getLettre() {
		return lettre;
	}

	public void setLettre(char lettre) {
		this.lettre = lettre;
	}

	public ArrayList<Integer> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Integer> positions) {
		this.positions = positions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lettre, positions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lettre other = (Lettre) obj;
		return lettre == other.lettre && Objects.equals(positions, other.positions);
	}
	
	
	

}
