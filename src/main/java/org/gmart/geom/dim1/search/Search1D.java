package org.gmart.geom.dim1.search;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.gmart.base.data.transform.toPrimitive.ToDouble;
import org.gmart.base.data.transform.toPrimitive.ToInteger;
import org.gmart.geom.dim1.intImpl.Lengthy;

@Generated("api_global.geom.dim1.search.Gen")
public class Search1D {
	public static void main(String[] args) {
		int intArr[] = { 10, 20, 30 };
		System.out.println(Arrays.binarySearch(intArr, 5));
		System.out.println(Arrays.binarySearch(intArr, 10));
		System.out.println(Arrays.binarySearch(intArr, 15));
		System.out.println(Arrays.binarySearch(intArr, 21));
		System.out.println(Arrays.binarySearch(intArr, 30));
		System.out.println("////");
		test();
		double doubleArr[] = { 1, 2, 3 };
		System.out.println(Arrays.binarySearch(doubleArr, 0.5));
		System.out.println(Arrays.binarySearch(doubleArr, 1));
		System.out.println(Arrays.binarySearch(doubleArr, 1.5));
		System.out.println(Arrays.binarySearch(doubleArr, 3));
		System.out.println(Arrays.binarySearch(doubleArr, 3.5));
		int vfgg;
		//calculateEndBoundsOfConcatData(List.of("z", "z"), ToInteger.of(s->s.length()));

		//DoubleStream.iterate(0, t -> t < 4, t -> t+0.5).forEach(t -> System.out.println("t:" + t + "=>" + selectSequenceElem_binarySearch(t, doubleArr.length, doubleArr)));
	}
	private static void test() {
		int intArr[] = { 10, 20, 30 };
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, -5) == 0;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 5) == 0;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 10) == 1;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 11) == 1;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 20) == 2;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 35) == 3;
		assert Lengthy.searchIndexOfObjectWithEndBoundsAtPosition(intArr, 1000) == 3;
	}

}