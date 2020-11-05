package api_global.geom.dim1;

public class PunctualSegment implements Seg {
	int beg;
	PunctualSegment(int beg){
		this.beg = beg;
	}
	@Override
	public int getBeg() {
		return beg;
	}
	@Override
	public int getLen() {
		return 0;
	}
	@Override
	public int getEnd() {
		return beg;
	}
	@Override
	public boolean isNegativeVector() {
		return false;
	}
}
