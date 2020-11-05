package api_global.geom.dim1;

public enum Alignment {
	beg   (0.),
	center(0.5),
	end   (1.)
	;
	public final double coeff;
	Alignment(double coeff){
		this.coeff = coeff;
	}
	
	public double getCoord(double containerWingspan, double contentWingspan) {
		return coeff*(containerWingspan - contentWingspan);
	}
	
	public interface To_algoModuleId_EnumMapping {
		Alignment to_algoModuleId_Homolog();
	}
}