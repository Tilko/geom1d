
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
package org.gmart.geom.dim1.intImpl;
import javax.annotation.processing.Generated;

import org.gmart.base.data.transform.toPrimitive.ToInteger;
import org.gmart.geom.dim1.intImpl.seg.Seg;
import org.gmart.geom.dim1.intImpl.seg.impls.Bounds;
import org.gmart.geom.dim1.intImpl.seg.impls.SegFrom0;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

/**
 * 1D equivalent to 2D Spanned interface
 * @author marti
 * 
 */
@Generated("api_global.geom.dim1.numberImplGen.Gen")
public interface Lengthy {
	int len();

	default Seg universeTail(int posInIntercalated) {
		return new Bounds(posInIntercalated, len());
	}

	default Seg[] universeSplit(int splitingPosition) {
		return universeSplit(len(), splitingPosition);
	}
	public static Seg[] universeSplit(int totalSize, int splitingPosition) {
		return new Seg[] {new SegFrom0(splitingPosition), new Bounds(splitingPosition, totalSize)};
	}

	default <T extends Lengthy> Stream<Seg> universeRangeComplement(Seg directedRange_directionDoesntMatter) {
Seg inputRange = directedRange_directionDoesntMatter.toAbsoluteSegment();
		int beg = inputRange.beg();
		Builder<Seg> builder = Stream.builder();
		if(beg > 0)
			builder.add(new SegFrom0(beg));

		int universeLen = len();
		int end = inputRange.end();
		if(end < universeLen)
			builder.add(new Bounds(end, universeLen));
		return builder.build();
	}


	public static int[] calculateEndBoundsOfConcatData(int[] length) {
		int[] bounds = new int[length.length];
		int curBound = 0;
		for(int i = 0; i < length.length; i++) {
			curBound +=length[i];
			bounds[i] = curBound;
		}
		return bounds;
	}
	public static <T extends Lengthy> int[] calculateEndBoundsOfConcatData(List<T> lenghtyElems) {
		lenghtyElems.stream().mapToInt(Lengthy::len).toArray();
		int[] bounds = new int[lenghtyElems.size()];
		int curBound = 0;
		for(int i = 0; i < lenghtyElems.size(); i++) {
			curBound += lenghtyElems.get(i).len();
			bounds[i] = curBound;
		}
		return bounds;
	}
	public static <T> int[] calculateEndBoundsOfConcatData(List<T> lenghtyElems, ToInteger<T> lenghtGetter) {
		int[] bounds = new int[lenghtyElems.size()];
		int curBound = 0;
		for(int i = 0; i < lenghtyElems.size(); i++) {
			curBound += lenghtGetter.apply(lenghtyElems.get(i));
			bounds[i] = curBound;
		}
		return bounds;
	}

	public static int searchIndexOfObjectWithEndBoundsAtPosition(int[] bounds, int position) {
		if(bounds.length < 5) {
			for(int i = 0; i < bounds.length; i++) {
				if(position < bounds[i])
					return i;
			}
			return bounds.length;
		} else {
			int rez = Arrays.binarySearch(bounds, position);
			if(rez >= 0) {
				return rez+1;
			} else {
				return -rez-1;
			}			
		}
	}

}


	