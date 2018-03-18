import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

public interface Observer {
	public void update(JTree jt, DefaultMutableTreeNode tn, int i, boolean a,boolean b);

}
