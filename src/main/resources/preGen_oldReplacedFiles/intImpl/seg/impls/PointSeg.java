package api_global.geom.dim1.intImpl.seg.impls;

import api_global.geom.dim1.intImpl.seg.Seg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class PointSeg implements Seg {
	private final int beg;

	public int len() {
		return 0;
	}
	public int end() {
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
