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
package api_global.geom.dim1.intImpl.seg.impls;

import api_global.geom.dim1.intImpl.seg.Seg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class UnitSeg implements Seg {
	private final int beg;

	@Override
	public int len() {
		return 1;
	}
	@Override
	public int end() {
		return beg + 1;
	}
	@Override
	public boolean isStrictNegativeVector() {
		return false;
	}
	@Override
	public String toString() {
		return this.toString_Seg();
	}
}
