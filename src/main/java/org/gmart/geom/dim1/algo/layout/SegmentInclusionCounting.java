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
package org.gmart.geom.dim1.algo.layout;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.base.component.generator.counter.CounterImpl;
import org.gmart.geom.dim1.intImpl.seg.Seg;
public class SegmentInclusionCounting {

	//algo non-optimisé:
	// on trie par ordre de longueur croissante
	// on parcourt les noeuds en partant du 2ème (le premier ne peut rien contenir)
	// à partir du noeud courant on parcours les noeuds précédents en checkant l'inclusion, le noeud courant à un compteur de noeud qu'il contient.
	
	//algo plus opti:
	//lorsqu'un noeud est checké comme "contenu" alors il est déplacé comme "noeud enfant" du conteneur, ainsi un noeud qui contient le parent contient aussi tous les enfants qui ne sont pas rechecké
	//puisqu'ils ont été déplacé là.
	
	
	
	/** 
	 * hypothesis: 
	 * 	- all end and beg points are not superimposed !
	 *  - inputs are sorted by beg			
	 *  */
	public static <S extends INode> ArrayList<S> countInclusion(Stream<S> inputsSortedByBeg) {
		ArrayList<S> segments = inputsSortedByBeg.collect(Collectors.toCollection(ArrayList::new));
		int size = segments.size();
		for(int i = 0; i < size; i++) {
			S current = segments.get(i);
			int end = current.end();
			for(int j = i + 1; j < size; j++) {
				if(segments.get(j).end() < end) {
					current.incCount();
				}
			}
		}
		return segments;
	}
	
	public interface INode extends Seg, InclusionCounter {}
	
	public static class NodeBase implements INode {
		public NodeBase(Seg seg) {
			this.seg = seg;
		}
		Seg seg;
		
		public Seg getSeg() {
			return seg;
		}
		public int beg() {
			return seg.beg();
		}
		public int len() {
			return seg.len();
		}
		public int end() {
			return seg.end();
		}
		public boolean contains_strict(Seg other) {
			return seg.contains_strict(other);
		}
		CounterImpl counter = new CounterImpl();
		@Override public void incCount() {
			counter.inc();
		}
		@Override public void addCount(int toAdd) {
			counter.add(toAdd);
		}
		@Override public int getInclusionCount() {
			return counter.get();
		}
		
	}
//	public static class Node extends NodeBase {
//		public Node(Seg seg) {
//			super(seg);
//		}
//		Node child = null;
//	}
//	
//	public abstract class Abstract_A<N extends NodeBase, L extends List<N>> {
//		L segmentsOrdered;
//		public void sortByLenAndAddToInputsSegments(Stream<N> segments) {
//			addInputsSegments(segments.sorted(Comparator.comparingInt(Seg::getLen)));
//		}
//		private void addInputsSegments(Stream<N> segments) {
//			segments.forEach(segmentsOrdered::add);
//		}
//	}
//	
//	public class A_Opti extends Abstract_A<Node, LinkedList<Node>>{
//		public A_Opti(){
//			segmentsOrdered = new LinkedList<>();
//		}
//		
//		private void absorbAllIncluded(ListIterator<Node> listIterator, Node current) {
//			Node previous = null;
//			while(listIterator.hasPrevious()) {
//				previous = listIterator.previous();
//				if(current.includeStrict(previous)) {
//					current.addCount(previous.getInclusionCount() + 1);
//					if(current.child == null) {
//						current.child = previous;
//						listIterator.remove();
//					}
//				} else {
//					while(previous.child != null) {
//						previous = previous.child;
//						if(current.includeStrict(previous)) {
//							current.addCount(previous.getInclusionCount() + 1);
//							break;
//						}
//					}
//				}
//			}
//			if(previous != null)
//				returnOnCurNode(listIterator, current);
//		}
//		
//		
//		private void returnOnCurNode(ListIterator<Node> listIterator, Node current) {
//			while(listIterator.next() != current);
//		}
//		
////		private List<List<Seg>> result;
//		public Stream<Seg> calculate() {
////			result = new ArrayList<>();
//			ListIterator<Node> listIterator = segmentsOrdered.listIterator();
//			
//			
//			while(listIterator.hasNext()) {
//				Node next = listIterator.next();
//				absorbAllIncluded(listIterator, next);
//				//skipNextOfSameLen(listIterator, curLen).ifPresent(next2 -> {});
//			}
//			Builder<Node> builder = Stream.builder();
//			segmentsOrdered.forEach(node -> {
//				Node child = node.child;
//				while((child = node.child) != null) {
//					builder.accept(child);
//				}
//				builder.accept(node);
//			});
//			return builder.build().sorted(Comparator.comparingInt(Node::getInclusionCount)).map(e->e);
//		}
//		
//	}
//	public class A_NonOpti extends Abstract_A<NodeBase, ArrayList<NodeBase>> {
//		public A_NonOpti(){
//			segmentsOrdered = new ArrayList<>();
//		}
//		public Stream<Seg> calculate_nonOpti() {
//			for(int i = 1; i < segmentsOrdered.size(); i++) {
//				NodeBase current = segmentsOrdered.get(i);
//				for(int j = i; j >= 0; j--) {
//					if(current.includeStrict(segmentsOrdered.get(j))) {
//						current.incCount();
//					}
//				}
//			}
//			return segmentsOrdered.stream().sorted(Comparator.comparingInt(NodeBase::getInclusionCount)).map(e->e);
//		}
//	}

	
	
	
//	private Optional<Node> skipNextOfSameLen(ListIterator<Node> iterator, int curLen) {
//		while(iterator.hasNext()) {
//			Node next = iterator.next();
//			if(next.seg.getLen() == curLen)
//				;//next.setCount(0); init val is "0"
//			else return Optional.of(next);
//		}
//		return Optional.empty();
//	}
	
	
	
	
	
	
	
}
