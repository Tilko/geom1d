package api_global.geom.dim1;

public class UnitSeg implements Seg {
	int beg;
	UnitSeg(int beg){
		this.beg = beg;
	}
	@Override
	public int getBeg() {
		return beg;
	}
	@Override
	public int getLen() {
		return 1;
	}
	@Override
	public int getEnd() {
		
		return beg + 1;
	}
	@Override
	public boolean isNegativeVector() {
		return true;
	}
}
