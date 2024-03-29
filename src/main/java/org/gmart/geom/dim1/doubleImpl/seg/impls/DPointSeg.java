
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
package org.gmart.geom.dim1.doubleImpl.seg.impls;
import javax.annotation.processing.Generated;

import org.gmart.geom.dim1.doubleImpl.seg.DSeg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@Generated("api_global.geom.dim1.numberImplGen.Gen")
public class DPointSeg implements DSeg {
	private final double beg;

	public double len() {
		return 0;
	}
	public double end() {
		return beg;
	}
	public boolean isPonctual() {
		return true;
	}
	public boolean isStrictNegativeVector() {
		return false;
	}
	@Override
	public String toString() {
		return this.toString_Seg();
	}
}
	