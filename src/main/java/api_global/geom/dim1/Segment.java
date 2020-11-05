package api_global.geom.dim1;

public class Segment implements Seg {
	private final int beg;
	private final int len;
	
	public Segment(int beg, int len) {
		super();
		this.beg = beg;
		this.len = len;
	}
	public static Segment withBounds(int beg, int end) {
		return new Segment(beg, end - beg);
	}
	@Override
	public int getBeg() {
		return beg;
	}
	@Override
	public int getLen() {
		return len;
	}
	@Override
	public boolean isNegativeVector() {
		return len < 0;
	}
}
