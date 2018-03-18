import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public interface Exportable {
	
	abstract void exporting(List<DefaultMutableTreeNode> tnl);

}
