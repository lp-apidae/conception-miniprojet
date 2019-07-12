package metier;

import java.util.Comparator;

public class I_CatalogueComparator implements Comparator<I_Catalogue> {
	@Override
	public int compare(I_Catalogue c1, I_Catalogue c2) {
       return (c1.getNom().compareTo(c2.getNom()));
	}
}
