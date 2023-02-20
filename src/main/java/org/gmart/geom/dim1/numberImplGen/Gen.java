package org.gmart.geom.dim1.numberImplGen;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.gmart.base.data.structure.tuple.homogeneous.Bi;
import org.gmart.lang.java.JavaPrimitives;
import org.gmart.stjavagen.StForSingleJava;

public class Gen {
	public static void main(String[] args) {
		//Mapping map = makeMap();
		String doubleTypePrefix = "D";
		List.of(JavaPrimitives.primitives).stream().filter(p -> {
			return p.getName().equals("int") || p.getName().equals("double");
		}).forEach(primitive -> {
			String typePrefix = primitive.getName().equals("int") ? "" : doubleTypePrefix;

			Function<String, String> numberConcretion = stgName -> stgName.replace("Number", primitive.getNameBoxedShort()).replace("number", primitive.getName());
			Function<String, String> toConcreteName = stgName -> typePrefix + stgName;
			StForSingleJava.generateJavaFilesFromSTGFileTree(null//new File("C:\\Users\\marti\\workingLowLevel\\geom1d\\src\\main\\java\\api_global\\geom\\dim1\\numberImplGen\\test")
					, numberConcretion, toConcreteName, (typeName, st) -> {
						//boxed, raw, capRaw, typePrefix, packageName, typeNameOfFile, specificPart, speImports
						st.add("boxed", primitive.getNameBoxed());
						st.add("raw", primitive.getName());
						st.add("capRaw", primitive.getNameBoxedShort());
						st.add("typePrefix", typePrefix);
						List<Bi<String>> spec = getKeyValuePairs(typeName, primitive.getName());
						spec.forEach(kvp -> st.add(kvp.getValue0(), kvp.getValue1()));
					});
		});
	}
	public static List<Bi<String>> getKeyValuePairs(String stgTypeName, String primitive) {
		Stream.Builder<Bi<String>> bui = Stream.builder();
		if(stgTypeName.equals("Seg")) {			
			if(primitive.equals("int"))
				bui.accept(Bi.of("specificPart", intSpec));
			else bui.accept(Bi.of("specificPart", doubleSpec));
		}
		return bui.build().toList();
	}
	public static class Mapping {
		//boxed, raw, capRaw, typePrefix, packageName, typeNameOfFile, specificPart, speImports
		
	}
	
	static String intSpec =
			""" 
			/** same result of toIncRange, it is to have a common interface with DSeg (but for"D" prefix) (with different impl) for code generation convenience */
	 		default Seg toAbsoluteSegment() {
				return toIncRange();
			}
			//////////////////////////////////////////////////////////////
			///Range interface:///////////////////////////////////////////
			///with "beg in end out" convention
			//////////////////////////////////////////////////////////////

			default int last() {
				return end() - 1;
			}
			public static Seg pbounds(int beg, int end) {
				if(beg > end)
					return new PointSeg(beg);
				return new Bounds(beg, end);
			}
			/**
			 * following positive iteration convention => {"len <= 0" => empty subList result}
			 * @param <T>
			 * @param list
			 * @return
			 */
			default <T> List<T> subList(List<T> list){
				int beg = beg();
				int end = end();
				if(beg >= end)
					return List.of();
				return list.subList(beg, end);
			}
			default <T> List<T> subList_discardingDirection(List<T> list){
				return this.toIncRange().subList(list);
			}
			default void forEach(IntConsumer user) {
				int end = this.end();
				for(int i = this.beg(); i < end; i++) {
					user.accept(i);
				}
			}
			/**
			 * len < 0 => dec order (keeping "beg-in end-out" convention)
			 * @param user
			 */
			default void forEach_lenGivingOrder(IntConsumer user) {
				int end = this.end();
				if(len() >= 0)
					for(int i = this.beg(); i < end; i++)
						user.accept(i);
				else 
					for(int i = this.beg(); i > end; i--)
						user.accept(i);
			}
			
			/**
			 * reverseRange().forEach_lenGivingOrder => iteration in reverse order 
			 * @return
			 */
			default Seg reverseRange() {
				if(this.isStrictNegativeVector())
					return new Bounds(end()+1, beg()+1);
				else if(len() == 0)
					return this;
				else return new Bounds(end()-1, beg()-1);
			}
			default Seg toIncRange() {
				return isStrictNegativeVector() ? reverseRange() : this;
			}

			default Seg growAtExtremity(boolean decrementLeftBound, boolean incrementRightBound) {
				return Bounds.of(this.beg() - (decrementLeftBound ? 1 : 0), this.end() + (incrementRightBound ? 1 : 0));
			}
			default Seg growAtLeft() {
				return Bounds.of(this.beg() - 1, this.end());
			}
			default Seg growAtRight() {
				return Bounds.of(this.beg(), this.end() + 1);
			}
			default Seg homotheticTransform(int thatOffset, Function<Integer, Integer> thatElementLenGetter) {
				int beg = beg();
				int end = end();
				int b = thatOffset;
				for(int i = 0; i < beg; i++) {
					b += thatElementLenGetter.apply(i);
				}
				int e = b;
				for(int i = beg; i < end; i++) {
					e += thatElementLenGetter.apply(i);
				}
				return Bounds.of(b, e);
			}
			default <T extends Lengthy> Seg homotheticTransform(int thatOffset, List<T> thatElements) {
				int beg = beg();
				int end = end();
				int b = thatOffset;
				for(int i = 0; i < beg; i++) {
					b += thatElements.get(i).len();
				}
				int e = b;
				for(int i = beg; i < end; i++) {
					e += thatElements.get(i).len();
				}
				return Bounds.of(b, e);
			}
			
			
			/**
			 * /!\\: range direction is discarded, you can reverse the stream order and the map it with Seg::reverseRange
			 * @param otherDirectedRange_directionDoesntMatter
			 * @return
			 */
			default Stream<Seg> without(Seg otherDirectedRange_directionDoesntMatter) {
				if(otherDirectedRange_directionDoesntMatter == null)
					return Stream.of(this);
				Seg otherRange = otherDirectedRange_directionDoesntMatter.toIncRange();
				Seg thisRange = toIncRange();
				int b0 = thisRange.beg();
				int e0 = thisRange.end();
				int b1 = otherRange.beg();
				int e1 = otherRange.end();
				Builder<Seg> builder = Stream.builder();
				if(b1 < b0) {
					if(e1 < b0)
						builder.add(this);
					else if(e1 >= e0)
						;//empty result
					else {
						builder.add(Bounds.of(e1, e0));
					}
				} else if(b1 > e0) {
					builder.add(this);
				} else {
					if(b0 != b1)
						builder.add(Bounds.of(b0, b1));
					if(e1 < e0) {
						builder.add(Bounds.of(e1, e0));
					}
				}
				return builder.build();
			}
			
		 		
								""";
	
	static String doubleSpec = """ 
/** same result of toIncRange, it is to have a common interface with Seg (but for"D" prefix) (with different impl) for code generation convenience */
default DSeg toAbsoluteSegment() {
	return toPositiveDirectionVector();
}
/////////////////////////////////////////
/////real number semantic specific///////
/////////////////////////////////////////
default DSeg toPositiveDirectionVector() {
	if(len()<0)
		return this.switchBounds();
	return this;
}

default DSeg switchBounds() {
	return new DBounds(this.end(), this.beg());
}

public default Stream<DSeg> without(DSeg other) { //Remaining  *NonPonctual* Seg en partie ...
	if(other == null)
		return Stream.of(this);
	
	DSeg absSegment = this.toPositiveDirectionVector();
	DSeg otherAbsSegment = this.toPositiveDirectionVector();
	double b0 = absSegment.beg();
	double e0 = absSegment.end();
	double b1 = otherAbsSegment.beg();
	double e1 = otherAbsSegment.end();
	Builder<DSeg> builder = Stream.builder();
	if(b1 < b0) {
		//s0 = getEmptySeg();
		if(e1 < b0)
			builder.add(this);
		else if(e1 > e0)
			;//s1 = getEmptySeg();
		else {
			builder.add(DBounds.of(e1, e0));
		}
	} else if(b1 > e0) {
		builder.add(this);
		//s1 = getEmptySeg();
	} else {
		builder.add(DBounds.of(b0, b1));
		if(e1 < e0) {
			builder.add(DBounds.of(e1, e0));
		}
	}
	return builder.build();
}
		
	 		
							""";	
}
