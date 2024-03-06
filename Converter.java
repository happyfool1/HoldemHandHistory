package holdemhandhistory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {
	static class V {
		int index;
		double low;
		double high;
		String description;

		// Constructor
		public V(int index, double low, double high, String description) {
			this.index = index;
			this.low = low;
			this.high = high;
			this.description = description;
		}
	}

	public static void main(String[] args) {
		String inputFilePath = "c:\\in\\in.txt"; // Update this to the path of your input file
		String outputFilePath = "c:\\in\\out.txt"; // Path to the output Java file
		List<String> lines = readFile(inputFilePath);
		StringBuilder output = new StringBuilder();

		output.append("public class GeneratedVariables {\n\n");
		output.append("    static V[] v;\n");
		output.append("    static double[] ndx;\n\n");

		int n = 0; // Counter for variables
		for (String line : lines) {
			String[] parts = line.split(";"); // Assuming each line is in 'type variableName; // comment' format
			if (parts.length < 2)
				continue; // Skip invalid lines

			String variableName = parts[0].trim().split(" ")[1]; // Get the variable name
			String comment = parts[1].trim();
			String transformedName = transformVariableName(variableName, "D_"); // Prefix for double

			output.append("    static final int ").append(transformedName).append(" = ").append(n).append("; // ")
					.append(comment).append("\n");
			n++;
		}

		output.append("\n    static {\n");
		output.append("        v = new V[").append(n).append("];\n");
		output.append("        ndx = new double[").append(n).append("];\n");

		n = 0; // Reset counter for array initializations
		for (String line : lines) {
			String[] parts = line.split(";");
			if (parts.length < 2)
				continue;

			String variableName = parts[0].trim().split(" ")[1];
			String comment = parts[1].trim();
			String transformedName = transformVariableName(variableName, "D_");

			output.append("        v[").append(transformedName).append("] = new V(").append(transformedName)
					.append(", 0., 0., \"").append(comment).append("\");\n");
			output.append("        ndx[").append(transformedName).append("] = ").append(variableName).append(";\n");
			n++;
		}

		output.append("    }\n");
		output.append("}\n");

		writeFile(outputFilePath, output.toString());
	}

	private static List<String> readFile(String filePath) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static String transformVariableName(String originalName, String prefix) {
		return prefix + originalName.toUpperCase().replaceAll("[^A-Z0-9_]", "_");
	}

	private static void writeFile(String filePath, String content) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
