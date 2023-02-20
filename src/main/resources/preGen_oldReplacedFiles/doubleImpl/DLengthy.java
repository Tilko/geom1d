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
package api_global.geom.dim1.doubleImpl;

import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import api_global.geom.dim1.doubleImpl.seg.DSeg;
import api_global.geom.dim1.doubleImpl.seg.impls.DBounds;
import api_global.geom.dim1.doubleImpl.seg.impls.DSegFrom0;
import api_global.geom.dim1.intImpl.Lengthy;
import api_global.geom.dim1.intImpl.seg.impls.SegFrom0;

public interface DLengthy {
	double len();
	
	default DSeg universeTail(int posInIntercalated) {
		return new DBounds(posInIntercalated, len());
	}
	
	default DSeg[] universeSplit(double splitingPosition) {
		return universeSplit(len(), splitingPosition);
	}
	public static DSeg[] universeSplit(double totalSize, double splitingPosition) {
		return new DSeg[] {new DSegFrom0(splitingPosition), new DBounds(splitingPosition, totalSize)};
	}
	
	default <T extends Lengthy> Stream<DSeg> universeRangeComplement(DSeg directedRange_directionDoesntMatter) {
		DSeg inputRange = directedRange_directionDoesntMatter.toPositiveDirectionVector();
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

	

	
}
