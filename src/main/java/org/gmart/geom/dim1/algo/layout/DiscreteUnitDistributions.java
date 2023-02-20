package org.gmart.geom.dim1.algo.layout;

import java.util.stream.DoubleStream;

import org.gmart.base.data.transform.number.doubleFuncts.UnitD2Y;
import org.gmart.geom.dim1.Alignment;

public class DiscreteUnitDistributions {
	public static DoubleStream uniform(int cardinal) {
		if(cardinal == 0) return DoubleStream.empty();
		double step = 1./(cardinal-1);
		return DoubleStream.iterate(0, x -> x <= 1, x -> x + step);
	}
	public static DoubleStream circularLeft(int cardinal) {
		if(cardinal == 0) return DoubleStream.empty();
		double step = 1./cardinal;
		return DoubleStream.iterate(0, x -> x < 1, x -> x + step);
	}
	public static DoubleStream circularRight(int cardinal) {
		if(cardinal == 0) return DoubleStream.empty();
		double step = 1./cardinal;
		return DoubleStream.iterate(step, x -> x <= 1, x -> x + step);
	}
	public static DoubleStream circularCenter(int cardinal) {
		if(cardinal == 0) return DoubleStream.empty();
		double step = 1./cardinal;
		return DoubleStream.iterate(step/2, x -> x <= 1, x -> x + step);
	}
	public static DoubleStream circular(int cardinal, Alignment alignment) {
		return switch (alignment) {
		    case beg -> circularLeft(cardinal);
		    case center -> circularCenter(cardinal);
		    case end -> circularRight(cardinal);
		};
	}
	public static DoubleStream circular(int cardinal, Alignment alignment, Alignment originAlignment) {
		return (switch (alignment) {
		    case beg -> circularLeft(cardinal);
		    case center -> circularCenter(cardinal);
		    case end -> circularRight(cardinal);
		}).map(x -> x - originAlignment.coeff);
	}
	
	public static UnitD2Y<DoubleStream> circularLeft(int cardinal, UnitD2Y<Double> phase) {
		return t -> circularLeft(cardinal).map(x -> (x + phase.apply(t)%1));
	}
	public static UnitD2Y<DoubleStream> circularRight(int cardinal, UnitD2Y<Double> phase) {
		return t -> circularRight(cardinal).map(x -> (x + phase.apply(t)%1));
	}
	public static UnitD2Y<DoubleStream> circularCenter(int cardinal, UnitD2Y<Double> phase) {
		return t -> circularCenter(cardinal).map(x -> (x + phase.apply(t)%1));
	}
	public static UnitD2Y<DoubleStream> circular(int cardinal, Alignment alignment, UnitD2Y<Double> phase) {
		return t -> circular(cardinal, alignment).map(x -> (x + phase.apply(t)%1));
	}
	public static void main(String[] args) {
		System.out.println("m:" + 1.1%1);
		System.out.println("circularLeft:");
		DiscreteUnitDistributions.circularLeft(3).forEach(x -> System.out.println(x));
		System.out.println("circularRight");
		DiscreteUnitDistributions.circularLeft(3).forEach(x -> System.out.println(x));
		System.out.println("circularCenter");
		DiscreteUnitDistributions.circularCenter(3).forEach(x -> System.out.println(x));
		System.out.println("uniform");
		DiscreteUnitDistributions.uniform(4).forEach(x -> System.out.println(x));
	}
}