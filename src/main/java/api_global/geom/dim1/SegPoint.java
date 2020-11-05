package api_global.geom.dim1;

public class SegPoint implements Seg {
	int lenThatIsAlsoEnd;
	public SegPoint(int lenThatIsAlsoEnd){
		this.lenThatIsAlsoEnd = lenThatIsAlsoEnd;
	}
	@Override
	public int getBeg() {
		return 0;
	}
	@Override
	public int getLen() {
		return lenThatIsAlsoEnd;
	}
	@Override
	public int getEnd() {
		return lenThatIsAlsoEnd;
	}
	@Override
	public boolean isNegativeVector() {
		return lenThatIsAlsoEnd < 0;
	}
}
