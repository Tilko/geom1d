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
package api_global.geom.dim1.doubleImpl.seg;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import api_global.geom.dim1.doubleImpl.DLengthy;
import api_global.geom.dim1.doubleImpl.seg.impls.DBounds;
import api_global.geom.dim1.doubleImpl.seg.impls.DPointSeg;
import api_global.geom.dim1.doubleImpl.seg.impls.DSegFrom0;
import api_global.geom.dim1.doubleImpl.seg.impls.DSegment;


public interface DSeg extends DLengthy {
//	public default MutableSeg makeMutable() {
//		return new MutableSeg(this);
//	}
	
	public static DSeg of(double beg, double len) {
		return new DSegment(beg, len);
	}
	
	public double beg();
	public default double len() {
		return end() - beg();
	}
	public default double end() {
		return beg() + len();
	}

	default boolean isNegativeVector() {
		return len() < 0;
	}
	
	public default boolean contains(double position) {
		return position >= beg()  &&  position < end();
	}
	public default String toString_Seg() {
		return "beg:" + beg() + ", end:" + end() + ", len:" + len();
	}
	
	public default boolean intersect(DSeg s) {
		return this.contains(s.beg())  ||  this.contains(s.end());
	}
	
	public default DSeg newWithIncLen(double lenInc) {
		return DSeg.of(this.beg(), len()+ lenInc);
	}
	public default DSeg copyAndMove(double delta) {
		return new DSegment(beg() + delta, len());
	}
	public default DSeg copyAndMoveAt(double position) {
		return new DSegment(position, len());
	}
	public default Optional<DSeg> makeIntersection(DSeg firstContent) {
		double b0 = beg();
		double b1 = firstContent.beg();
		double e0 = end();
		double e1 = firstContent.end();
		if(e0 <= b1  ||  e1 <= b0) 
			return Optional.empty();
		return Optional.of(DBounds.of(Math.max(b0, b1), Math.min(e0, e1)));
	}
	
	public default DSeg union(DSeg other) {
		return new DBounds(Math.min(this.beg(), other.beg()), Math.max(this.end(), other.end()));
	}
	
	
	public default boolean isEmpty() {
		return len() <= 0;
	}
	default boolean isEmptyRange() {
		return len() <= 0;
	}
	public default boolean isPonctual() {
		return len() == 0;
	}
	
//	public default double absBeg() {
//		return Math.min(getBeg(), getEnd());
//	}
//	public default double absEnd() {
//		return Math.max(getBeg(), getEnd());
//	}
	public default DSeg getAbsSegment() {
		if(len() < 0)
			return new DPointSeg(beg());
		return this;
	}
	public default Optional<DSeg> getEmptySeg(){
		return Optional.empty();
	}
//	public default Optional<Seg>[] remove(Seg other) {
//		DSeg absSegment = getAbsSegment();
//		DSeg otherAbsSegment = other.getAbsSegment();
//		double b0 = absSegment.getBeg();
//		double e0 = absSegment.getEnd();
//		double b1 = otherAbsSegment.getBeg();
//		 e1 = otherAbsSegment.getEnd();
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
	
	public default boolean isIn(double beg, double end) {
		return this.beg() >= beg  &&  this.end() <= end;
	}
	
	
	
//	public default <T> DSeg convert(T that, int thatOffset, BiFunction<Integer, T, Integer> thatElementLenGetter) {
//		double beg = getBeg();
//		double end = getEnd();
//		double b = thatOffset;
//		for(int i = 0; i < beg; i++) {
//			b += thatElementLenGetter.apply(i, that);
//		}
//		int e = b;
//		for(int i = beg; i < end; i++) {
//			e += thatElementLenGetter.apply(i, that);
//		}
//		return Geom.bounds(b, e);
//	}
//	public default <T extends DLengthy> DSeg homotheticTransform(int thatOffset, List<T> thatElements) {
//		double beg = getBeg();
//		double end = getEnd();
//		int b = thatOffset;
//		for(int i = 0; i < beg; i++) {
//			b += thatElements.get(i).getLen();
//		}
//		int e = b;
//		for(int i = beg; i < end; i++) {
//			e += thatElements.get(i).getLen();
//		}
//		return Geom.bounds(b, e);
//	}

	default boolean includeStrict(DSeg other) {
		return this.beg() < other.beg()  &&  other.end() <  this.end();
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
