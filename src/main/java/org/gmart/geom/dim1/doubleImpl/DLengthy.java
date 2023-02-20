
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
package org.gmart.geom.dim1.doubleImpl;
import javax.annotation.processing.Generated;

import org.gmart.base.data.transform.toPrimitive.ToDouble;
import org.gmart.geom.dim1.doubleImpl.seg.DSeg;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DBounds;
import org.gmart.geom.dim1.doubleImpl.seg.impls.DSegFrom0;

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
public interface DLengthy {
	double len();

	default DSeg universeTail(double posInIntercalated) {
		return new DBounds(posInIntercalated, len());
	}

	default DSeg[] universeSplit(double splitingPosition) {
		return universeSplit(len(), splitingPosition);
	}
	public static DSeg[] universeSplit(double totalSize, double splitingPosition) {
		return new DSeg[] {new DSegFrom0(splitingPosition), new DBounds(splitingPosition, totalSize)};
	}

	default <T extends DLengthy> Stream<DSeg> universeRangeComplement(DSeg directedRange_directionDoesntMatter) {
		DSeg inputRange = directedRange_directionDoesntMatter.toAbsoluteSegment();
		double beg = inputRange.beg();
		Builder<DSeg> builder = Stream.builder();
		if(beg > 0)
			builder.add(new DSegFrom0(beg));

		double universeLen = len();
		double end = inputRange.end();
		if(end < universeLen)
			builder.add(new DBounds(end, universeLen));
		return builder.build();
	}


	public static double[] calculateEndBoundsOfConcatData(double[] length) {
		double[] bounds = new double[length.length];
		double curBound = 0;
		for(int i = 0; i < length.length; i++) {
			curBound +=length[i];
			bounds[i] = curBound;
		}
		return bounds;
	}
	public static <T extends DLengthy> double[] calculateEndBoundsOfConcatData(List<T> lenghtyElems) {
		double[] bounds = new double[lenghtyElems.size()];
		double curBound = 0;
		for(int i = 0; i < lenghtyElems.size(); i++) {
			curBound += lenghtyElems.get(i).len();
			bounds[i] = curBound;
		}
		return bounds;
	}
	public static <T> double[] calculateEndBoundsOfConcatData(List<T> lenghtyElems, ToDouble<T> lenghtGetter) {
		double[] bounds = new double[lenghtyElems.size()];
		double curBound = 0;
		for(int i = 0; i < lenghtyElems.size(); i++) {
			curBound += lenghtGetter.apply(lenghtyElems.get(i));
			bounds[i] = curBound;
		}
		return bounds;
	}

	public static int searchIndexOfObjectWithEndBoundsAtPosition(double[] bounds, double position) {
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


	