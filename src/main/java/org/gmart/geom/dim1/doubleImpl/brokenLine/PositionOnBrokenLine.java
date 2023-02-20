package org.gmart.geom.dim1.doubleImpl.brokenLine;

public record PositionOnBrokenLine(int index, double remaining, boolean isOnSegment) {
//	public boolean isOnBrokenLine(int segmentNumber) {
//		return index >= 0  && index < segmentNumber;
//	}
}