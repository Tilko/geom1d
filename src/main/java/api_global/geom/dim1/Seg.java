package api_global.geom.dim1;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;


public interface Seg {
//	public default MutableSeg makeMutable() {
//		return new MutableSeg(this);
//	}
	
	public static Seg of(int beg, int len) {
		return new Segment(beg, len);
	}
	
	public int getBeg();
	public default int getLen() {
		return getEnd() - getBeg();
	}
	public default int getEnd() {
		return getBeg() + getLen();
	}
	public default int getLast() {
		return getEnd() - 1;
	}
	
	default boolean isNegativeVector() {
		return getLen() < 0;
	}
	
	public default boolean contains(int position) {
		return position >= getBeg()  &&  position < getEnd();
	}
	public default String toString_Seg() {
		return "beg:" + getBeg() + ", end:" + getEnd() + ", len:" + getLen();
	}
	
	public default boolean intersect(Seg s) {
		return this.contains(s.getBeg())  ||  this.contains(s.getEnd());
	}
	
	public default Seg newWithIncLen(int lenInc) {
		return Geom.seg(this.getBeg(), getLen()+ lenInc);
	}
	public default Seg copyAndMove(int delta) {
		return new Segment(getBeg() + delta, getLen());
	}
	public default Seg copyAndMoveAt(int position) {
		return new Segment(position, getLen());
	}
	public default Optional<Seg> makeIntersection(Seg firstContent) {
		int b0 = getBeg();
		int b1 = firstContent.getBeg();
		int e0 = getEnd();
		int e1 = firstContent.getEnd();
		if(e0 <= b1  ||  e1 <= b0) 
			return Optional.empty();
		return Optional.of(Geom.bounds(Math.max(b0, b1), Math.min(e0, e1)));
	}
	
	public default Seg union(Seg other) {
		return new Bounds(Math.min(this.getBeg(), other.getBeg()), Math.max(this.getEnd(), other.getEnd()));
	}
	
	
	public default boolean isEmpty() {
		return getLen() <= 0;
	}
	default boolean isEmptyRange() {
		return getLen() <= 0;
	}
	public default boolean isPonctual() {
		return getLen() == 0;
	}
	
//	public default int absBeg() {
//		return Math.min(getBeg(), getEnd());
//	}
//	public default int absEnd() {
//		return Math.max(getBeg(), getEnd());
//	}
	public default Seg getAbsSegment() {
		if(getEnd() < getBeg())
			return new PunctualSegment(getBeg());
		return this;
	}
	public default Optional<Seg> getEmptySeg(){
		return Optional.empty();
	}
//	public default Optional<Seg>[] remove(Seg other) {
//		Seg absSegment = getAbsSegment();
//		Seg otherAbsSegment = other.getAbsSegment();
//		int b0 = absSegment.getBeg();
//		int e0 = absSegment.getEnd();
//		int b1 = otherAbsSegment.getBeg();
//		int e1 = otherAbsSegment.getEnd();
//		Optional<Seg> s0, s1;
//		if(b1 < b0) {
//			s0 = getEmptySeg();
//			if(e1 < b0)
//				s1 = Optional.of(this);
//			else if(e1 > e0)
//				s1 = getEmptySeg();
//			else {
//				s1 = Optional.of(Geom.bounds(e1, e0));
//			}
//		} else if(b1 > e0) {
//			s0 = Optional.of(this);
//			s1 = getEmptySeg();
//		} else {
//			s0 = Optional.of(Geom.bounds(b0, b1));
//			s1 = e1 > e0 ? getEmptySeg() : Optional.of(Geom.bounds(e1, e0));
//		}
//		return new Optional[] {s0,s1};
//	}
	public default <T extends Finite1DUniverseGeom> Stream<Seg> complementInPositiveFinite1DUniverse(T finitePositiveUniverse) {
		int beg = this.getBeg();
		assert beg >= 0;
		int universeSize = finitePositiveUniverse.getFiniteUniverse1DSize();
		int end = this.getEnd();
		assert end <= universeSize;
		Builder<Seg> builder = Stream.builder();
		if(beg != 0)
			builder.add(new SegPoint(beg));
		if(end != universeSize)
			builder.add(new Bounds(end, universeSize));
		return builder.build();
	}
	public default Stream<Seg> removeSegAndGetStreamOfRemainingSeg(Seg other) { //Remaining  *NonPonctual* Seg en partie ...
		if(other == null)
			return Stream.of(this);
		
		Seg absSegment = getAbsSegment();
		Seg otherAbsSegment = other.getAbsSegment();
		int b0 = absSegment.getBeg();
		int e0 = absSegment.getEnd();
		int b1 = otherAbsSegment.getBeg();
		int e1 = otherAbsSegment.getEnd();
		Builder<Seg> builder = Stream.builder();
		if(b1 < b0) {
			//s0 = getEmptySeg();
			if(e1 < b0)
				builder.add(this);
			else if(e1 > e0)
				;//s1 = getEmptySeg();
			else {
				builder.add(Geom.bounds(e1, e0));
			}
		} else if(b1 > e0) {
			builder.add(this);
			//s1 = getEmptySeg();
		} else {
			builder.add(Geom.bounds(b0, b1));
			if(e1 < e0) {
				builder.add(Geom.bounds(e1, e0));
			}
		}
		return builder.build();
	}
	
	public default Seg copyAndGrowAtExtremity(boolean decrementLeftBound, boolean incrementRightBound) {
		return Geom.bounds(this.getBeg() - (decrementLeftBound ? 1 : 0), this.getEnd() + (incrementRightBound ? 1 : 0));
	}
	public default Seg copyAndGrowAtLeft() {
		return Geom.bounds(this.getBeg() - 1, this.getEnd());
	}
	public default Seg copyAndGrowAtRight() {
		return Geom.bounds(this.getBeg(), this.getEnd() + 1);
	}
	
	
	public default boolean isIn(int beg, int end) {
		return this.getBeg() >= beg  &&  this.getEnd() <= end;
	}
	
	
	
	public default <T> Seg convert(T that, int thatOffset, BiFunction<Integer, T, Integer> thatElementLenGetter) {
		int beg = getBeg();
		int end = getEnd();
		int b = thatOffset;
		for(int i = 0; i < beg; i++) {
			b += thatElementLenGetter.apply(i, that);
		}
		int e = b;
		for(int i = beg; i < end; i++) {
			e += thatElementLenGetter.apply(i, that);
		}
		return Geom.bounds(b, e);
	}
	public default <T extends Lengthy> Seg homotheticTransform(int thatOffset, List<T> thatElements) {
		int beg = getBeg();
		int end = getEnd();
		int b = thatOffset;
		for(int i = 0; i < beg; i++) {
			b += thatElements.get(i).getLen();
		}
		int e = b;
		for(int i = beg; i < end; i++) {
			e += thatElements.get(i).getLen();
		}
		return Geom.bounds(b, e);
	}

	default boolean includeStrict(Seg other) {
		return this.getBeg() < other.getBeg()  &&  other.getEnd() <  this.getEnd();
	}
	
}
