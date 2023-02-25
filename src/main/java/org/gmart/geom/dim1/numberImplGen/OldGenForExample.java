package org.gmart.geom.dim1.numberImplGen;

import java.io.IOException;
import java.util.List;

import org.gmart.devtools.coding.java.gen.templating.JavaGenerators;
import org.gmart.lang.java.JavaPrimitives;

class OldGenForExample {
	public static void main(String[] args) throws IOException {
		JavaGenerators.generateJavaFileFromSTGFile(st -> {
			List.of(JavaPrimitives.primitives).stream().filter(p -> {
				return p.getName().equals("int") || p.getName().equals("double");
			}).forEach(primitive -> st.add("type", primitive));
		});
	}
}