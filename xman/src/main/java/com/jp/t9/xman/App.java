package com.jp.t9.xman;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * Hello world!
 * https://www.programcreek.com/2011/01/best-java-development-tooling-jdt-and-astparser-tutorials/
 */
public class App {
	// use ASTParse to parse string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS11);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setUnitName("/Users/ron/git/T9/xman/src/main/java/com/jp/t9/xman/App.java");

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			Set<String> names = new HashSet<String>();

			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				System.out.println("Declaration of '" + name + "' at line" + cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue
			}

			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line " + cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
		});

	}

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

	// loop directory to get file list
	public static void ParseFilesInDir() throws IOException {
		// File dirs = new File(".");
		// String dirPath = dirs.getCanonicalPath() +
		// File.separator+"src"+File.separator;
		// String dirpath="D:\programs\workspace\strategy\src\";
		// File root = new File(dirpath);
		// System.out.println(root.listFiles());
		// File[] files = root.listFiles ( );
		String filePath = "/Users/ron/git/T9/xman/src/main/java/com/jp/t9/xman/App.java";

		// for (File f : files ) {
		// filePath = f.getAbsolutePath();
		// if(f.isFile()){
		parse(readFileToString(filePath));
		// }
		// }
	}

	public static void main(String[] args) throws IOException {
		ParseFilesInDir();
	}
}
