package api_global.geom.dim1;

public class Bounds implements Seg {
	private final int beg;
	private final int end;
	
	public static Bounds of(int beg, int end) {
		return new Bounds(beg, end);
	}
	public Bounds(int beg, int end) {
		super();
		this.beg = beg;
		this.end = end;
	}
	public static Bounds withLen(int beg, int len) {
	
		return new Bounds(beg, beg + len);
	}
	@Override
	public int getBeg() {
		return beg;
	}
	@Override
	public int getEnd() {
		return end;
	}
	@Override
	public int getLast() {
		return end - 1;
	}
	@Override
	public boolean isNegativeVector() {
		return beg <= end;
	}
}
