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
package api_global.geom.dim1.doubleImpl.seg.impls;

import api_global.geom.dim1.doubleImpl.seg.DSeg;

public class DSegment implements DSeg {
	private final double beg;
	private final double len;
	
	public DSegment(double beg, double len) {
		super();
		this.beg = beg;
		this.len = len;
	}
	public static DSegment withBounds(double beg, double end) {
		return new DSegment(beg, end - beg);
	}
	@Override
	public double beg() {
		return beg;
	}
	@Override
	public double len() {
		return len;
	}
	@Override
	public boolean isNegativeVector() {
		return len < 0;
	}
}
