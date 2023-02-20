package org.gmart.geom.dim1.numberImplGen;

import java.io.IOException;
import java.util.List;

import org.gmart.lang.java.JavaPrimitives;
import org.gmart.stjavagen.StForSingleJava;

class OldGenForExample {
	public static void main(String[] args) throws IOException {
		StForSingleJava.generateJavaFileFromSTGFile(st -> {
			List.of(JavaPrimitives.primitives).stream().filter(p -> {
				return p.getName().equals("int") || p.getName().equals("double");
			}).forEach(primitive -> st.add("type", primitive));
		});
	}
}