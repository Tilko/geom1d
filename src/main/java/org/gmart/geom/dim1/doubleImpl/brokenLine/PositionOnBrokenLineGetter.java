package org.gmart.geom.dim1.doubleImpl.brokenLine;

import java.util.List;
import java.util.stream.IntStream;

import org.gmart.base.component.integrator.number.DIntegrators;

public class PositionOnBrokenLineGetter {
	public static PositionOnBrokenLineGetter forSegmentsLen(List<Double> segmentLen) {
		List<Double> ends = segmentLen.stream().map(DIntegrators.pre()).toList();
		return new PositionOnBrokenLineGetter(ends);
	}
	public static PositionOnBrokenLineGetter forSegmentsEnds(List<Double> ends) {
		return new PositionOnBrokenLineGetter(ends);
	}
	
	
	
	List<Double> ends;
	private PositionOnBrokenLineGetter(List<Double> ends) {
		super();
		this.ends = ends;
	}
	
	public PositionOnBrokenLine getPositionOnBrokenLine(double x) {
		return getPositionOnBrokenLine_withAbsoluteEnds(ends, x);
	}

	private static PositionOnBrokenLine makeNegativePosition(double absolutePosition) {
		return new PositionOnBrokenLine(-1, absolutePosition, false);
	}
	public static PositionOnBrokenLine getPositionOnBrokenLine_withAbsoluteEnds(List<Double> ends, double x) {
		if(x < 0) 
			return makeNegativePosition(x);
		return getPositionOnBrokenLine_withAbsoluteEnds_internal(ends, x);
	}
	private static PositionOnBrokenLine getPositionOnBrokenLine_withAbsoluteEnds_internal(List<Double> ends, double x) {
		int size = ends.size();
		Double totalLen = ends.get(size-1);
		if(x > totalLen) {
			return new PositionOnBrokenLine(size, x - totalLen, false);
		}
		return IntStream.range(0, size).filter(i -> ends.get(i) >= x).mapToObj(i->i).findFirst().map(i-> {
			return i == 0 ?  new PositionOnBrokenLine(0, x, true)
				          :  new PositionOnBrokenLine(i, x-ends.get(i-1), true);
			}).get();
	}
	public static PositionOnBrokenLine getPositionOnBrokenLine(List<Double> segmentLen, double x) {
		if(x < 0) 
			return makeNegativePosition(x);
		List<Double> ends = segmentLen.stream().map(DIntegrators.pre()).toList();
		return getPositionOnBrokenLine_withAbsoluteEnds_internal(ends, x);
	}
}