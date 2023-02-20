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

public class DSegFrom0 implements DSeg {
	double lenThatIsAlsoEnd;
	public DSegFrom0(double lenThatIsAlsoEnd){
		this.lenThatIsAlsoEnd = lenThatIsAlsoEnd;
	}
	@Override
	public double beg() {
		return 0;
	}
	@Override
	public double len() {
		return lenThatIsAlsoEnd;
	}
	@Override
	public double end() {
		return lenThatIsAlsoEnd;
	}
	@Override
	public boolean isNegativeVector() {
		return lenThatIsAlsoEnd < 0;
	}
}
