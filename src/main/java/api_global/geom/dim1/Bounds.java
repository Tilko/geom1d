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
package api_global.geom.dim1;

public class Bounds implements Seg {
	private final int beg;
	private final int end;
	
	public static Bounds of(int beg, int end) {
		return new Bounds(beg, end);
	}
	public Bounds(int beg, int end) {
		super();
		this.beg = beg;
		this.end = end;
	}
	public static Bounds withLen(int beg, int len) {
	
		return new Bounds(beg, beg + len);
	}
	@Override
	public int getBeg() {
		return beg;
	}
	@Override
	public int getEnd() {
		return end;
	}
	@Override
	public int getLast() {
		return end - 1;
	}
	@Override
	public boolean isNegativeVector() {
		return beg <= end;
	}
}
