
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
package org.gmart.geom.dim1.doubleImpl.seg;
import javax.annotation.processing.Generated;

import org.gmart.geom.dim1.doubleImpl.DLengthy;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DBounds;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DPointSeg;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DSegFrom0;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DSegment;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DUnitSeg;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Generated("api_global.geom.dim1.numberImplGen.Gen")
public interface DSeg extends DLengthy {
	//////////////////////////////////////////////////////////////
	///General interface://///////////////////////////////////////
	//////////////////////////////////////////////////////////////
	double beg();
	default double len() {
		return end() - beg();
	}
	default double end() {
		return beg() + len();
	}

	public final static DSeg origin = new DSeg() {
		@Override
		public double beg() {
			return 0;
		}
		@Override
		public double len() {
			return 0;
		}
		@Override
		public double end() {
			return 0;
		}
	};	
	public static Optional<DSeg> getEmptySeg(){
		return Optional.empty();
	}
	/** 
	 * use Bounds::of instead
	 * @param beg
	 * @param end
	 * @return
	 */
	@Deprecated 
	public static  DSeg bounds(double beg, double end) {
		return new DBounds(beg, end);
	}
	public static DSeg of(double beg, double len) {
		return new DSegment(beg, len);
	}
	public static DSeg unit(double beg) {
		return new DUnitSeg(beg);
	}
	public static DSeg point(double position) {
		return new DPointSeg(position);
	}
	public static DSeg from0(double lenThatIsAlsoEnd) {
		return new DSegFrom0(lenThatIsAlsoEnd);
	}
	default String toString_Seg() {
		return "beg:" + beg() + ", end:" + end() + ", len:" + len();
	}

	default boolean isPonctual() {
		return len() == 0;
	}
	default boolean contains(double position) {
		return position >= beg()  &&  position < end();
	}
	default boolean contains(DSeg other) {
		return other.beg() >= beg()  &&  other.end() <= end();
	}
	default boolean contains_strict(DSeg other) {
		return this.beg() < other.beg()  &&  other.end() <  this.end();
	}
	default boolean isInside(DSeg other) {
		return other.contains(this);
	}

	default boolean intersect(DSeg s) {
		return this.contains(s.beg())  ||  this.contains(s.end());
	}

	default DSeg addLen(double lenInc) {
		return new DSegment(this.beg(), len()+ lenInc);
	}
	default DSeg translate(double delta) {
		return new DSegment(beg() + delta, len());
	}
	default DSeg withBeg(double position) {
		return new DSegment(position, len());
	}
//	public default MutableSeg makeMutable() {
//		return new MutableSeg(this);
//	}

	//////////////////////////////////////////////////////////////
	///Geom interface:////////////////////////////////////////////
	//////////////////////////////////////////////////////////////

	//@sync absolute changed semantic from "len < 0 => len == 0" to "len < 0 => reverse bounds"
	//use "toIncRange" instead
//	public default DSeg absolute() {
//		if(end() < beg())
//			return new  DPointSeg(beg());
//		return this;
//	}

	default boolean isNegativeVector() {
		return len() <= 0;
	}
	default boolean isStrictNegativeVector() {
		return len() < 0;
	}
	default DSeg oppositeVect() {
		return new DSegment(beg(), -len());
	}


	public default Optional<DSeg> intersectionOpt(DSeg other) {
		double b0 = beg();
		double b1 = other.beg();
		double e0 = end();
		double e1 = other.end();
		if(e0 <= b1  ||  e1 <= b0) 
			return Optional.empty();
		return Optional.of(DBounds.of(Math.max(b0, b1), Math.min(e0, e1)));
	}
	public default DSeg inter(DSeg other) {
		double b0 = beg();
		double b1 = other.beg();
		double e0 = end();
		double e1 = other.end();
		return DBounds.of(Math.max(b0, b1), Math.min(e0, e1));
	}
	public default DSeg enclosingBoth(DSeg other) {
		return new DBounds(Math.min(this.beg(), other.beg()), Math.max(this.end(), other.end()));
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
//	public static class DSegWithBiPresence {
//		DSeg seg;
//		int biPresence;
//	}
//	public default List<SegWithBiPresence> getSegWithBiPresenceList(DSeg other){
//		List<SegWithBiPresence> rez;
//		if(this.getBeg() == other.getBeg()) {
//			rez = new ArrayList<>();
//			if(this.getEnd() == other.getEnd()) {
//				rez.add(DSegWithBiPresence.of(this, BiPresence11));
//			} else if(this.getEnd() > other.getEnd()) {
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(getBeg(), other.getEnd()), BiPresence11));
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(other.getEnd(), getEnd()), BiPresence10));
//			} else {
//				rez.add(DSegWithBiPresence.of(this, BiPresence11));
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(getEnd(), other.getEnd()), BiPresence10));
//			}
//		} else if(this.getBeg() < other.getBeg()){
//			rez = this.assumingThisBegInfOtherBeg(other, false);
//		} else {
//			rez = other.assumingThisBegInfOtherBeg(this, true);
//		}
//		return rez;
//	}
//	private List<SegWithBiPresence> assumingThisBegInfOtherBeg(DSeg other, boolean swap) {
//		ArrayList<SegWithBiPresence> rez = new ArrayList<>();
//		int presence01 = swap ? BiPresence10 : BiPresence01;
//		int presence10 = swap ? BiPresence01 : BiPresence10;
//		if(this.getEnd() == other.getBeg()) {
//			rez.add(DSegWithBiPresence.of(this, presence10));
//			rez.add(DSegWithBiPresence.of(other, presence01));
//		} else if(this.getEnd() < other.getBeg()) {
//			rez.add(DSegWithBiPresence.of(this, presence10));
//			rez.add(DSegWithBiPresence.of(DSeg.withEnd(this.getEnd(), other.getBeg()), BiPresence00));
//			rez.add(DSegWithBiPresence.of(other, presence01));
//		} else {
//			if(this.getEnd() == other.getEnd()) {
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(DSegWithBiPresence.of(other, BiPresence11));
//			} else if(this.getEnd() > other.getEnd()) {
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(DSegWithBiPresence.of(other, BiPresence11));
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(other.getEnd(), this.getEnd()), presence10));
//			} else {
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(this.getBeg(), other.getBeg()), presence10));
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(other.getBeg(), this.getEnd()), BiPresence11));
//				rez.add(DSegWithBiPresence.of(DSeg.withEnd(this.getEnd(), other.getEnd()), presence01));
//			}
//		}
//		return rez;
//	}
	@Getter 
	@AllArgsConstructor(staticName = "of")
	public static class Partitions {
		double[] bounds;
		int [] presences;
	}
	public default Partitions getSegWithBiPresenceList2(DSeg other){
		if(this.beg() == other.beg()) {
			if(this.end() == other.end()) {
				return Partitions.of(new double[]{beg(), end()}, 
						               new int[]{BiPresence11});
			} else if(this.end() > other.end()) {
				return Partitions.of(new double[]{beg(), other.end(), end()}, 
					              new int[]{     BiPresence11,   BiPresence10});
			} else {
				return Partitions.of(new double[]{beg(), end(), other.end()}, 
				                      new int[]{BiPresence11,   BiPresence10});
			}
		} else if(this.beg() < other.beg()){
			return this.assumingThisBegInfOtherBeg2(other, false);
		} else {
			return other.assumingThisBegInfOtherBeg2(this, true);
		}
	}
	private Partitions assumingThisBegInfOtherBeg2(DSeg other, boolean swap) {
		int presence01 = swap ? BiPresence10 : BiPresence01;
		int presence10 = swap ? BiPresence01 : BiPresence10;
		if(this.end() == other.beg()) {
			return Partitions.of(new double[]{beg(), end(), other.end()}, 
                                       new int[]{presence10,   presence01});
		} else if(this.end() < other.beg()) {
			return Partitions.of(new double[]{beg(), end(), other.beg(), other.end()}, 
                                      new int[]{presence10,   BiPresence00,    presence01});
		} else {
			if(this.end() == other.end()) {
				return Partitions.of(new double[]{beg(), other.beg(), other.end()}, 
                                            new int[]{presence10,   BiPresence11});
			} else if(this.end() > other.end()) {
				return Partitions.of(new double[]{beg(), other.beg(), other.end(), this.end()}, 
                                          new int[]{presence10,   BiPresence11, presence10});
			} else {
				return Partitions.of(new double[]{beg(), other.beg(), this.end(), other.end()}, 
                                               new int[]{presence10,   BiPresence11, presence01});
			}
		}
	}
//	public default DSeg makeSegAtRightOfIntersection_algebraic(DSeg other) {
//		double e0 = end();
//		double e1 = other.end();
//		return Geom.bounds(Math.min(e0, e1), Math.max(e0, e1));
//	}
//	public default DSeg makeSegAtLeftOfIntersection(DSeg firstContent) {
//		double b0 = beg();
//		double b1 = firstContent.beg();
//		return Geom.bounds(Math.min(b0, b1), Math.max(b0, b1));
//	}



//	public default double absBeg() {
//		return Math.min(getBeg(), getEnd());
//	}
//	public default double absEnd() {
//		return Math.max(getBeg(), getEnd());
//	}

	/** same result of toIncRange, it is to have a common interface with Seg (but for"D" prefix) (with different impl) for code generation convenience */
	default DSeg toAbsoluteSegment() {
		return toPositiveDirectionVector();
	}
	/////////////////////////////////////////
	/////real number semantic specific///////
	/////////////////////////////////////////
	default DSeg toPositiveDirectionVector() {
		if(len()<0)
			return this.switchBounds();
		return this;
	}

	default DSeg switchBounds() {
		return new DBounds(this.end(), this.beg());
	}

	public default Stream<DSeg> without(DSeg other) { //Remaining  *NonPonctual* Seg en partie ...
		if(other == null)
			return Stream.of(this);

		DSeg absSegment = this.toPositiveDirectionVector();
		DSeg otherAbsSegment = this.toPositiveDirectionVector();
		double b0 = absSegment.beg();
		double e0 = absSegment.end();
		double b1 = otherAbsSegment.beg();
		double e1 = otherAbsSegment.end();
		Builder<DSeg> builder = Stream.builder();
		if(b1 < b0) {
			//s0 = getEmptySeg();
			if(e1 < b0)
				builder.add(this);
			else if(e1 > e0)
				;//s1 = getEmptySeg();
			else {
				builder.add(DBounds.of(e1, e0));
			}
		} else if(b1 > e0) {
			builder.add(this);
			//s1 = getEmptySeg();
		} else {
			builder.add(DBounds.of(b0, b1));
			if(e1 < e0) {
				builder.add(DBounds.of(e1, e0));
			}
		}
		return builder.build();
	}






}