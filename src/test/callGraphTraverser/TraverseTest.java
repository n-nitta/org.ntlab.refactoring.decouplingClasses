package test.callGraphTraverser;

public class TraverseTest {
	public static final String PARAM_TYPE = "ParamType";
	public static final String PARAM_TYPE2 = "ParamType2";
	public static final String RETURN_TYPE = "ReturnType";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphBuilder builder = new GraphBuilder();
		builder.addClass(RETURN_TYPE);
		builder.addClass(PARAM_TYPE);
		builder.addClass(PARAM_TYPE2);
		
		builder.addClass("A");
		builder.addMethod("A", "mA", RETURN_TYPE, new String[] {PARAM_TYPE, PARAM_TYPE2});
		
		builder.addClass("B");
		builder.addMethod("B", "mB", RETURN_TYPE, new String[] {PARAM_TYPE});
		
		builder.addClass("C");
		builder.addMethod("C", "mC", RETURN_TYPE);
		
		builder.addClass("D");
		builder.addMethod("D", "mD", RETURN_TYPE);
		
		builder.addClass("E");
		builder.addMethod("E", "mE", RETURN_TYPE);
		
		builder.addClass("F");
		builder.addMethod("F", "mF", RETURN_TYPE);
		
		builder.addClass("G");
		builder.addMethod("G", "mG", RETURN_TYPE);
		
		builder.addClass("H");
		builder.addMethod("H", "mH", RETURN_TYPE);
		
		builder.addClass("I");
		builder.addMethod("I", "mI", RETURN_TYPE);
		
		builder.addClass("J");
		builder.addMethod("J", "mJ", RETURN_TYPE);
		
		builder.addClass("K");
		builder.addMethod("K", "mK", RETURN_TYPE);
		
		builder.addClass("L");
		builder.addMethod("L", "mL", RETURN_TYPE);
		
		builder.addClass("M");
		builder.addMethod("M", "mM", RETURN_TYPE);
		
		builder.addClass("N");
		builder.addMethod("N", "mN", RETURN_TYPE);
		
		builder.addClass("Target");
		builder.addMethod("Target", "constructor", RETURN_TYPE);
		
		builder.invoke("A", "mA", new String[] {PARAM_TYPE, PARAM_TYPE2}, "F", "mF", new String[] {});
		builder.invoke("B", "mB", new String[] {PARAM_TYPE}, "G", "mG", new String[] {});
		builder.invoke("F", "mF", new String[] {}, "H", "mH", new String[] {});
		builder.invoke("G", "mG", new String[] {}, "H", "mH", new String[] {});
		builder.invoke("C", "mC", new String[] {}, "H", "mH", new String[] {});
		builder.invoke("F", "mF", new String[] {}, "I", "mI", new String[] {});
		builder.invoke("H", "mH", new String[] {}, "I", "mI", new String[] {});
		builder.invoke("D", "mD", new String[] {}, "I", "mI", new String[] {});
		builder.invoke("E", "mE", new String[] {}, "J", "mJ", new String[] {});
		builder.invoke("J", "mJ", new String[] {}, "K", "mK", new String[] {});
		builder.invoke("K", "mK", new String[] {}, "L", "mL", new String[] {});
		builder.invoke("L", "mL", new String[] {}, "M", "mM", new String[] {});
		builder.invoke("I", "mI", new String[] {}, "N", "mN", new String[] {});
		builder.invoke("M", "mM", new String[] {}, "N", "mN", new String[] {});
		builder.invoke("N", "mN", new String[] {}, "Target", "constructor", new String[] {});
		
		builder.setTarget("Target");
		
		builder.print();
		
		System.out.println("RefactoringPatternDetector is running...");
		RefactoringPatternDetector.startExplore(builder);
	}

}
