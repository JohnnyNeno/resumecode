import java.awt.Dimension;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class SavedStates {
	
	private List<DefaultTreeModel> currentModelList;
	private int currentStatePointer;
	
	
	
	
	public SavedStates(){
		
		currentModelList= new ArrayList<DefaultTreeModel>();
		currentStatePointer=-1;
		}
	
	public void addingState(JTree jt,DefaultMutableTreeNode tr){
		
		
		DefaultTreeModel stateModel = copiedTree(jt,tr);
		
	
		
		
		if(currentStatePointer+1<currentModelList.size()){
			int amount =currentModelList.size();
			for(int i=currentStatePointer+1;i<amount;i++){
				currentModelList.remove(currentModelList.size()-1);
				
			}
			currentModelList.add(stateModel);
			currentStatePointer++;
		}
		
		else if(currentModelList.size()==10){
			currentModelList.remove(0);
			
			currentModelList.add(stateModel);
		}
		
		else{
			currentModelList.add(stateModel);
			currentStatePointer++;
			
		}
		
		
		
		
	
	}
	
	public DefaultTreeModel getUndoModel(){
		currentStatePointer--;
		return currentModelList.get(currentStatePointer);
		
	}
	
	public DefaultTreeModel getRedoModel(){
		currentStatePointer++;
		return currentModelList.get(currentStatePointer);
	}
	
	
    public void creatingNewTree(DefaultTreeModel oldModel, DefaultMutableTreeNode oldNode, DefaultTreeModel newModel, DefaultMutableTreeNode copiedNode){
		
		for(int i=0; i<oldNode.getChildCount();i++){
			
			SearchAlbumTemplate sa;
		
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) oldModel.getChild(oldNode,i);
			DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode();
			Set<Photo> tempPhotoSet = new HashSet<Photo>();
			
			Album fakeAlbum=(Album)childNode.getUserObject();
			tempPhotoSet = fakeAlbum.getPhotoSet();
				
			Album toBeInserted = new Album(childNode.getUserObject().toString());
			
			if(toBeInserted.toString().equals("Flagged")){
				 sa = new FlagAlbum("Flagged");
				 sa.addPhotosToAlbum(tempPhotoSet);
				 tempNode.setUserObject(sa);
				 newModel.insertNodeInto(tempNode, copiedNode, copiedNode.getChildCount());
			
			}
			else if(toBeInserted.toString().equals("Great Photos")){
				sa = new GreatPhotosAlbum("Great Photos");
				sa.addPhotosToAlbum(tempPhotoSet);
				tempNode.setUserObject(sa);
				newModel.insertNodeInto(tempNode, copiedNode, copiedNode.getChildCount());
			}
			
			else{
			toBeInserted.addPhotosToAlbum(tempPhotoSet);
			tempNode.setUserObject(toBeInserted);
			
			newModel.insertNodeInto(tempNode,copiedNode, copiedNode.getChildCount());}
			
			
			
			creatingNewTree(oldModel,childNode,newModel,(DefaultMutableTreeNode)copiedNode.getChildAt(i));
			
		}
	}
    
    
    public DefaultTreeModel copiedTree(JTree oldTree, DefaultMutableTreeNode oldNode){
    	Album modelRootAlbum = (Album)oldNode.getUserObject();
    	
    	Album root= new Album("Root");
    	Set<Photo> modelRootPhotoSet = modelRootAlbum.getPhotoSet();
    	root.addPhotosToAlbum(modelRootPhotoSet);
    	
		JTree newTree = makeDataTree(root);
		
		DefaultTreeModel theNewModel = (DefaultTreeModel) (newTree.getModel());
		DefaultMutableTreeNode theNewRoot = (DefaultMutableTreeNode) theNewModel.getRoot();
		DefaultTreeModel theOldModel = (DefaultTreeModel)(oldTree.getModel());
		
		creatingNewTree(theOldModel,oldNode,theNewModel,theNewRoot);
		
		return theNewModel;
    }
    
    
    private JTree makeDataTree(Album root) {
		
		DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("all photos");
		treeRoot.setUserObject(root);
		
		final JTree tree = new JTree(treeRoot);
		tree.setMinimumSize(new Dimension(200,400));

		tree.setToggleClickCount(3); // so that we can use double-clicks for previewing instead of expanding/collapsing

		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(selectionModel);
		
			
		
		return tree;
	}
    
    
    
    public boolean undoEnabled(){
    	if(currentStatePointer>0)
    		return true;
    	else
    		return false;
    }
	
    public boolean redoEnabled(){
    	if(currentStatePointer<currentModelList.size()-1)
    		return true;
    	else
    		return false;
    }

	
	
	

}
