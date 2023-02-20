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
package org.gmart.geom.dim1.intImpl.algo.layout.nonOverlappingBy1DChannel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.geom.dim1.intImpl.algo.layout.SegmentInclusionCounting;
import org.gmart.geom.dim1.intImpl.algo.layout.SegmentInclusionCounting.NodeBase;
import org.gmart.geom.dim1.intImpl.seg.Seg;

public class NonOverlapingBy1DChannel<S extends Seg> {
	
	/**
	 * inputs segment are supposed to be algebraic: their length can be negative, a negative length means that the segment make a connection from high coordinate to low coordinate 
	 * (the segments are vectors with a sense).
	 *  */
	public static Stream<Stream<Seg>> groupByChannelAndSortChannelByLeastIntersection(Stream<Seg> inputs) {
		
		
		return null;
	}
	
	
	public static Stream<Stream<Seg>> groupByChannelAndSortByInclusion(Stream<Seg> inputs) {
		Stream<NodeBase> sortedByBeg = inputs.map(NodeBase::new).sorted(Comparator.comparingInt(Seg::beg));
		ArrayList<NodeBase> sortedByBeg_inclusionCounted = SegmentInclusionCounting.countInclusion(sortedByBeg);
		Stream<List<NodeBase>> groupedByChannel = groupByChannel(sortedByBeg_inclusionCounted.stream());
		Stream<List<NodeBase>> sortedByInclusion = groupedByChannel.sorted(Comparator.comparingInt(equiChannelSegs -> equiChannelSegs.stream().mapToInt(NodeBase::getInclusionCount).sum()));
		return sortedByInclusion.map(list -> list.stream().map(NodeBase::getSeg));
	}
	
	/** 
	 * hypothesis: 
	 *  - inputs are sorted by beg		
	 *  */
	public static <S extends Seg> Stream<List<S>> groupByChannel(Stream<S> sortedByBeg) {
		NonOverlapingBy1DChannel<S> nonOverlapingBy1DChannel_SortByBeg = new NonOverlapingBy1DChannel<>();
		nonOverlapingBy1DChannel_SortByBeg.addInputsSegments(sortedByBeg);
		return nonOverlapingBy1DChannel_SortByBeg.calculate().stream();
	}
	
	
	//for test:
	public static void main(String[] args) {
		NonOverlapingBy1DChannel<Seg> algo = new NonOverlapingBy1DChannel<>();
		algo.addInputsSegments_andSortByBeg(Stream.of(Seg.of(0, 5), Seg.of(7, 1), Seg.of(2, 6), Seg.of(6, 3), Seg.of(9, 2), Seg.of(9, 2)));
		List<List<Seg>> rez = algo.calculate();
		rez.forEach(equiChannelSeg -> System.out.println(equiChannelSeg.stream().map(seg -> "{"+ seg.beg() + "," + seg.len() + "}").collect(Collectors.joining(","))));
	}
	
	private LinkedList<S> segmentsOrdered;
	public NonOverlapingBy1DChannel(){
		segmentsOrdered = new LinkedList<>();
	}
	
	public void addInputsSegments(Stream<S> segments) {
		segments.forEach(segmentsOrdered::add);
	}
	public void addInputsSegments_andSortByBeg(Stream<S> segments) {
		addInputsSegments(segments.sorted(Comparator.comparingInt(Seg::beg)));
	}
	private List<List<S>> result;
	public List<List<S>> calculate() {
		result = new ArrayList<>();
		while(segmentsOrdered.size() != 0) {
			result.add(buildEquiChannelSegment(segmentsOrdered));
		}
		return result;
	}
	
	//this assume that the input list is ordered by "seg.beg"
	private List<S> buildEquiChannelSegment(LinkedList<S> segmentsOrdered){
		List<S> equiChannelResult = new ArrayList<>();
		Iterator<S> iterator = segmentsOrdered.iterator();
		//we know that the list is not empty, we pop the first:
		S lastPoppedSeg = iterator.next();
		equiChannelResult.add(lastPoppedSeg);
		iterator.remove();
		int lastPoppedSeg_end = lastPoppedSeg.end();
		
		while(iterator.hasNext()) {
			S next = iterator.next();
			if(next.beg() > lastPoppedSeg_end) {
				equiChannelResult.add(next);
				iterator.remove();
				lastPoppedSeg_end = next.end();
			}
		}
		return equiChannelResult;
	}
	
	
	
	
}
