/*******************************************************************************
 * Copyright 2020 Grégoire Martinetti
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package api_global.geom.dim1.intImpl.seg;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import api_global.geom.dim1.intImpl.Lengthy;
import api_global.geom.dim1.intImpl.seg.impls.Bounds;
import api_global.geom.dim1.intImpl.seg.impls.PointSeg;
import api_global.geom.dim1.intImpl.seg.impls.SegFrom0;
import api_global.geom.dim1.intImpl.seg.impls.Segment;
import api_global.geom.dim1.intImpl.seg.impls.UnitSeg;
import lombok.AllArgsConstructor;
import lombok.Getter;


public interface Seg extends Lengthy {
	//////////////////////////////////////////////////////////////
	///General interface://///////////////////////////////////////
	//////////////////////////////////////////////////////////////
	int beg();
	default int len() {
		return end() - beg();
	}
	default int end() {
		return beg() + len();
	}

	public final static Seg origin = new Seg() {
		@Override
		public int beg() {
			return 0;
		}
		@Override
		public int len() {
			return 0;
		}
		@Override
		public int end() {
			return 0;
		}
	};	
	public static Optional<Seg> getEmptySeg(){
		return Optional.empty();
	}
	/** 
	 * use Bounds::of instead
	 * @param beg
	 * @param end
	 * @return
	 */
	@Deprecated 
	public static Seg bounds(int beg, int end) {
		return new Bounds(beg, end);
	}
	public static Seg of(int beg, int len) {
		return new Segment(beg, len);
	}
	public static Seg unit(int beg) {
		return new UnitSeg(beg);
	}
	public static Seg point(int position) {
		return new PointSeg(position);
	}
	public static Seg from0(int lenThatIsAlsoEnd) {
		return new SegFrom0(lenThatIsAlsoEnd);
	}
	default String toString_Seg() {
		return "beg:" + beg() + ", end:" + end() + ", len:" + len();
	}

	default boolean isPonctual() {
		return len() == 0;
	}
	default boolean contains(int position) {
		return position >= beg()  &&  position < end();
	}
	default boolean contains(Seg other) {
		return other.beg() >= beg()  &&  other.end() <= end();
	}
	default boolean contains_strict(Seg other) {
		return this.beg() < other.beg()  &&  other.end() <  this.end();
	}
	default boolean isInside(Seg other) {
		return other.contains(this);
	}
	

	default boolean intersect(Seg s) {
		return this.contains(s.beg())  ||  this.contains(s.end());
	}
	
	default Seg addLen(int lenInc) {
		return new Segment(this.beg(), len()+ lenInc);
	}
	default Seg translate(int delta) {
		return new Segment(beg() + delta, len());
	}
	default Seg withBeg(int position) {
		return new Segment(position, len());
	}
//	public default MutableSeg makeMutable() {
//		return new MutableSeg(this);
//	}

	//////////////////////////////////////////////////////////////
	///Geom interface:////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	
	//@sync absolute changed semantic from "len < 0 => len == 0" to "len < 0 => reverse bounds"
	//use "toIncRange" instead
//	public default Seg absolute() {
//		if(end() < beg())
//			return new PointSeg(beg());
//		return this;
//	}
	
	
	default boolean isStrictNegativeVector() {
		return len() < 0;
	}
	default Seg oppositeVect() {
		return new Segment(beg(), -len());
	}

	
	public default Optional<Seg> intersectionOpt(Seg other) {
		int b0 = beg();
		int b1 = other.beg();
		int e0 = end();
		int e1 = other.end();
		if(e0 <= b1  ||  e1 <= b0) 
			return Optional.empty();
		return Optional.of(Bounds.of(Math.max(b0, b1), Math.min(e0, e1)));
	}
	public default Seg inter(Seg other) {
		int b0 = beg();
		int b1 = other.beg();
		int e0 = end();
		int e1 = other.end();
		return Bounds.of(Math.max(b0, b1), Math.min(e0, e1));
	}
	public default Seg enclosingBoth(Seg other) {
		return new Bounds(Math.min(this.beg(), other.beg()), Math.max(this.end(), other.end()));
	}
	
//	public enum BiPresence {
//		c00, c01, c10, c11;
//	}
	public final static int BiPresence00 = 0b00,
			   BiPresence01 = 0b01,
			   BiPresence10 = 0b10,
			   BiPresence11 = 0b11;
//	@Getter 
//	@AllArgsConstructor(staticName = "of")
//	public static class SegWithBiPresence {
//		Seg seg;
//		int biPresence;
//	}
//	public default List<SegWithBiPresence> getSegWithBiPresenceList(Seg other){
//		List<SegWithBiPresence> rez;
//		if(this.getBeg() == other.getBeg()) {
//			rez = new ArrayList<>();
//			if(this.getEnd() == other.getEnd()) {
//				rez.add(SegWithBiPresence.of(this, BiPresence11));
//			} else if(this.getEnd() > other.getEnd()) {
//				rez.add(SegWithBiPresence.of(Seg.withEnd(getBeg(), other.getEnd()), BiPresence11));
//				rez.add(SegWithBiPresence.of(Seg.withEnd(other.getEnd(), getEnd()), BiPresence10));
//			} else {
//				rez.add(SegWithBiPresence.of(this, BiPresence11));
//				rez.add(SegWithBiPresence.of(Seg.withEnd(getEnd(), other.getEnd()), BiPresence10));
//			}
//		} else if(this.getBeg() < other.getBeg()){
//			rez = this.assumingThisBegInfOtherBeg(other, false);
//		} else {
//			rez = other.assumingThisBegInfOtherBeg(this, true);
//		}
//		return rez;
//	}
//	private List<SegWithBiPresence> assumingThisBegInfOtherBeg(Seg other, boolean swap) {
//		ArrayList<SegWithBiPresence> rez = new ArrayList<>();
//		int presence01 = swap ? BiPresence10 : BiPresence01;
//		int presence10 = swap ? BiPresence01 : BiPresence10;
//		if(this.getEnd() == other.getBeg()) {
//			rez.add(SegWithBiPresence.of(this, presence10));
//			rez.add(SegWithBiPresence.of(other, presence01));
//		} else if(this.getEnd() < other.getBeg()) {
//			rez.add(SegWithBiPresence.of(this, presence10));
//			rez.add(SegWithBiPresence.of(Seg.withEnd(this.getEnd(), other.getBeg()), BiPresence00));
//			rez.add(SegWithBiPresence.of(other, presence01));
//		} else {
//			if(this.getEnd() == other.getEnd()) {
//				rez.add(SegWithBiPresence.of(Seg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(SegWithBiPresence.of(other, BiPresence11));
//			} else if(this.getEnd() > other.getEnd()) {
//				rez.add(SegWithBiPresence.of(Seg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(SegWithBiPresence.of(other, BiPresence11));
//				rez.add(SegWithBiPresence.of(Seg.withEnd(other.getEnd(), this.getEnd()), presence10));
//			} else {
//				rez.add(SegWithBiPresence.of(Seg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(SegWithBiPresence.of(Seg.withEnd(other.getBeg(), this.getEnd()), BiPresence11));
//				rez.add(SegWithBiPresence.of(Seg.withEnd(this.getEnd(), other.getEnd()), presence01));
//			}
//		}
//		return rez;
//	}
	@Getter 
	@AllArgsConstructor(staticName = "of")
	public static class Partitions {
		int[] bounds;
		int [] presences;
	}
	public default Partitions getSegWithBiPresenceList2(Seg other){
		if(this.beg() == other.beg()) {
			if(this.end() == other.end()) {
				return Partitions.of(new int[]{beg(), end()}, 
						               new int[]{BiPresence11});
			} else if(this.end() > other.end()) {
				return Partitions.of(new int[]{beg(), other.end(), end()}, 
					              new int[]{     BiPresence11,   BiPresence10});
			} else {
				return Partitions.of(new int[]{beg(), end(), other.end()}, 
				                      new int[]{BiPresence11,   BiPresence10});
			}
		} else if(this.beg() < other.beg()){
			return this.assumingThisBegInfOtherBeg2(other, false);
		} else {
			return other.assumingThisBegInfOtherBeg2(this, true);
		}
	}
	private Partitions assumingThisBegInfOtherBeg2(Seg other, boolean swap) {
		int presence01 = swap ? BiPresence10 : BiPresence01;
		int presence10 = swap ? BiPresence01 : BiPresence10;
		if(this.end() == other.beg()) {
			return Partitions.of(new int[]{beg(), end(), other.end()}, 
                                       new int[]{presence10,   presence01});
		} else if(this.end() < other.beg()) {
			return Partitions.of(new int[]{beg(), end(), other.beg(), other.end()}, 
                                      new int[]{presence10,   BiPresence00,    presence01});
		} else {
			if(this.end() == other.end()) {
				return Partitions.of(new int[]{beg(), other.beg(), other.end()}, 
                                            new int[]{presence10,   BiPresence11});
			} else if(this.end() > other.end()) {
				return Partitions.of(new int[]{beg(), other.beg(), other.end(), this.end()}, 
                                          new int[]{presence10,   BiPresence11, presence10});
			} else {
				return Partitions.of(new int[]{beg(), other.beg(), this.end(), other.end()}, 
                                               new int[]{presence10,   BiPresence11, presence01});
			}
		}
	}
//	public default Seg makeSegAtRightOfIntersection_algebraic(Seg other) {
//		int e0 = getEnd();
//		int e1 = other.getEnd();
//		return Geom.bounds(Math.min(e0, e1), Math.max(e0, e1));
//	}
//	public default Seg makeSegAtLeftOfIntersection(Seg firstContent) {
//		int b0 = getBeg();
//		int b1 = firstContent.getBeg();
//		return Geom.bounds(Math.min(b0, b1), Math.max(b0, b1));
//	}
	
	
	
//	public default int absBeg() {
//		return Math.min(getBeg(), getEnd());
//	}
//	public default int absEnd() {
//		return Math.max(getBeg(), getEnd());
//	}

	//////////////////////////////////////////////////////////////
	///Range interface:///////////////////////////////////////////
	///with "beg in end out" convention
	//////////////////////////////////////////////////////////////

	default int last() {
		return end() - 1;
	}
	public static Seg pbounds(int beg, int end) {
		if(beg > end)
			return new PointSeg(beg);
		return new Bounds(beg, end);
	}
	/**
	 * following positive iteration convention => {"len <= 0" => empty subList result}
	 * @param <T>
	 * @param list
	 * @return
	 */
	default <T> List<T> subList(List<T> list){
		int beg = beg();
		int end = end();
		if(beg >= end)
			return List.of();
		return list.subList(beg, end);
	}
	default <T> List<T> subList_discardingDirection(List<T> list){
		return this.toIncRange().subList(list);
	}
	default void forEach(IntConsumer user) {
		int end = this.end();
		for(int i = this.beg(); i < end; i++) {
			user.accept(i);
		}
	}
	/**
	 * len < 0 => dec order (keeping "beg-in end-out" convention)
	 * @param user
	 */
	default void forEach_lenGivingOrder(IntConsumer user) {
		int end = this.end();
		if(len() >= 0)
			for(int i = this.beg(); i < end; i++)
				user.accept(i);
		else 
			for(int i = this.beg(); i > end; i--)
				user.accept(i);
	}
	default boolean isNegativeVector() {
		return len() <= 0;
	}
	default boolean isStrictDecRange() {
		return len() < 0;
	}
	/**
	 * reverseRange().forEach_lenGivingOrder => iteration in reverse order 
	 * @return
	 */
	default Seg reverseRange() {
		if(this.isStrictNegativeVector())
			return new Bounds(end()+1, beg()+1);
		else if(len() == 0)
			return this;
		else return new Bounds(end()-1, beg()-1);
	}
	default Seg toIncRange() {
		return isStrictDecRange() ? reverseRange() : this;
	}

	default Seg growAtExtremity(boolean decrementLeftBound, boolean incrementRightBound) {
		return Bounds.of(this.beg() - (decrementLeftBound ? 1 : 0), this.end() + (incrementRightBound ? 1 : 0));
	}
	default Seg growAtLeft() {
		return Bounds.of(this.beg() - 1, this.end());
	}
	default Seg growAtRight() {
		return Bounds.of(this.beg(), this.end() + 1);
	}
	default Seg homotheticTransform(int thatOffset, Function<Integer, Integer> thatElementLenGetter) {
		int beg = beg();
		int end = end();
		int b = thatOffset;
		for(int i = 0; i < beg; i++) {
			b += thatElementLenGetter.apply(i);
		}
		int e = b;
		for(int i = beg; i < end; i++) {
			e += thatElementLenGetter.apply(i);
		}
		return Bounds.of(b, e);
	}
	default <T extends Lengthy> Seg homotheticTransform(int thatOffset, List<T> thatElements) {
		int beg = beg();
		int end = end();
		int b = thatOffset;
		for(int i = 0; i < beg; i++) {
			b += thatElements.get(i).len();
		}
		int e = b;
		for(int i = beg; i < end; i++) {
			e += thatElements.get(i).len();
		}
		return Bounds.of(b, e);
	}
	
	
	/**
	 * /!\: range direction is discarded, you can reverse the stream order and the map it with Seg::reverseRange
	 * @param otherDirectedRange_directionDoesntMatter
	 * @return
	 */
	default Stream<Seg> without(Seg otherDirectedRange_directionDoesntMatter) {
		if(otherDirectedRange_directionDoesntMatter == null)
			return Stream.of(this);
		Seg otherRange = otherDirectedRange_directionDoesntMatter.toIncRange();
		Seg thisRange = toIncRange();
		int b0 = thisRange.beg();
		int e0 = thisRange.end();
		int b1 = otherRange.beg();
		int e1 = otherRange.end();
		Builder<Seg> builder = Stream.builder();
		if(b1 < b0) {
			if(e1 < b0)
				builder.add(this);
			else if(e1 >= e0)
				;//empty result
			else {
				builder.add(Bounds.of(e1, e0));
			}
		} else if(b1 > e0) {
			builder.add(this);
		} else {
			if(b0 != b1)
				builder.add(Bounds.of(b0, b1));
			if(e1 < e0) {
				builder.add(Bounds.of(e1, e0));
			}
		}
		return builder.build();
	}
	
	
	
}
