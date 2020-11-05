package api_global.geom.dim1;

public class Geom {
	
	//put that in a client code seg factory:
	//public static boolean assertPositiveSegment = false;
//	if(Geom.assertPositiveSegment)
//		assert len >= 0;
//	if(Geom.assertPositiveSegment)
//		assert beg <= end;
//	if(Geom.assertPositiveSegment)
//		assert len >= 0;
//	public SegPoint(int lenThatIsAlsoEnd){
//		if(Geom.assertPositiveSegment)
//			assert lenThatIsAlsoEnd >= 0;
//		this.lenThatIsAlsoEnd = lenThatIsAlsoEnd;
//	}

	
	public static Segment seg(int beg, int len) {
		
		return new Segment(beg, len);
	}

	public static Seg bounds(int beg, int end) {
		return new Bounds(beg, end);
	}
	public static Seg boundsWithIncluded(int first, int second) {
		return new Bounds(first, second+1);
	}
//	public static Seg boundsExcludingLimits(int first, int second) {
//		return first <= second ? new Bounds(first + 1, second) : new Bounds(second + 1, first);
//	}

	public static Seg segPoint(int endThatIsAlsoLen) {
		return new SegPoint(endThatIsAlsoLen);
	}
	
	public final static Seg segZero = new SegPoint(0);

	public static Seg pbounds(int beg, int end) {
		if(beg > end)
			return new Bounds(beg, beg);
		return new Bounds(beg, end);
	}

	public static Seg unitSegment(int index) {
		return new UnitSeg(index);
	}

	public static Seg tailSeg(Finite1DUniverseGeom that, int posInIntercalated) {
		return new Bounds(posInIntercalated, that.getFiniteUniverse1DSize());
	}

	public static Seg[] split(int splitingPosition, Finite1DUniverseGeom splittedComposite) {
		return split(splitingPosition, splittedComposite.getFiniteUniverse1DSize());
	}
	public static Seg[] split(int splitingPosition, int totalSize) {
		return new Seg[] {new SegPoint(splitingPosition), new Bounds(splitingPosition, totalSize)};
	}
}
