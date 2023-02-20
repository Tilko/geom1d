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
package api_global.geom.dim1.intImpl;

import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import api_global.geom.dim1.intImpl.seg.Seg;
import api_global.geom.dim1.intImpl.seg.impls.Bounds;
import api_global.geom.dim1.intImpl.seg.impls.SegFrom0;

/**
 * 1D equivalent to 2D Spanned interface
 * @author marti
 * 
 */
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
		Seg inputRange = directedRange_directionDoesntMatter.toIncRange();
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
}
