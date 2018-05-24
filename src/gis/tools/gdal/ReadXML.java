/**
 * 
 */
package gis.tools.gdal;

import org.gdal.gdal.XMLNode;
import org.gdal.gdal.XMLNodeType;

/**
 * @author A.Riaydh
 * 
 */
public class ReadXML {

	public static void usage() {
		System.out.println("usage example: readxml {xml string}");
		System.exit(-1);
	}

	public static void main(String[] args) {
		if (args.length != 1)
			usage();
		XMLNode node = new XMLNode(args[0]);
		printNode(0, node);

	}

	public static void printNode(int recnum, XMLNode node) {
		for (int i = 0; i < recnum; i++)
			System.out.print(" ");
		System.out.println("Type: " + node.getType() + "  Value: "
				+ node.getValue());
		if (node.getChild() != null)
			printNode(recnum + 1, node.getChild());

		if (node.getNext() != null)
			printNode(recnum, node.getNext());

	}

}
