import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

public class CurrentState implements Subject {
	
	private List<Observer> observerList = new ArrayList<Observer>();
	
	private JTree stateTree;
	private DefaultMutableTreeNode treeRoot;
	private DefaultMutableTreeNode updateableNode= new DefaultMutableTreeNode();
	private Set<Photo> rootPhotoSet;
	private SavedStates ss;
	private boolean undoEnabled;
	private boolean redoEnabled;
	
	public  List<Observer> getObserverList(){
		return observerList;
	}
	
	
	public void addObserver(Observer o){
		observerList.add(o);
	}
	
	public void removeObserver(Observer o){
		observerList.remove(o);
	}
	
	public void inform(){
		
		
		for (int i=0;i<observerList.size();i++){
			Observer obs = (Observer)observerList.get(i);
			
			obs.update(stateTree,treeRoot,observerList.size(),undoEnabled, redoEnabled);
		}
	}
	
	public CurrentState(List<Album>a){
		stateTree=makeDataTree(a.get(0));
		rootPhotoSet = new HashSet<Photo>();
		
		rootPhotoSet =a.get(0).getPhotoSet();
		for(int i=1;i<a.size();i++){
		DefaultMutableTreeNode trnode= new DefaultMutableTreeNode();
		trnode.setUserObject(a.get(i));
		DefaultTreeModel model = (DefaultTreeModel) (stateTree.getModel());
		model.insertNodeInto(trnode,treeRoot, treeRoot.getChildCount());
		stateTree.scrollPathToVisible(new TreePath(trnode.getPath()));

		}
		
		ss = new SavedStates();
		
		
	}
	
	private JTree makeDataTree(Album root) {
		
		treeRoot = new DefaultMutableTreeNode("all photos");
		treeRoot.setUserObject(root);
		
		final JTree tree = new JTree(treeRoot);
		tree.setMinimumSize(new Dimension(200,400));

		tree.setToggleClickCount(3); // so that we can use double-clicks for previewing instead of expanding/collapsing

		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(selectionModel);
		
			
		
		return tree;
	}
	
	
	public void findCorrectNode(DefaultMutableTreeNode tn, Album a, DefaultTreeModel tm){
		
		updateableNode = treeRoot;
		
		for(int i=0;i<tn.getChildCount();i++){
			
			DefaultMutableTreeNode aChildNode = (DefaultMutableTreeNode) tm.getChild(tn, i);
			Album compareAlbum = (Album)aChildNode.getUserObject();
			if(a.toString().equals(compareAlbum.toString())){
				updateableNode=aChildNode;
				
				break;
			}
			findCorrectNode(aChildNode,a,tm);
			
		}
		
		
		
	}

	
	public void addAlbumToTree(DefaultMutableTreeNode selectedTreeNode,Album albumToBeAdded){
		Album a = (Album) selectedTreeNode.getUserObject();
	
		DefaultTreeModel treeModel = (DefaultTreeModel) (stateTree.getModel());
		
		findCorrectNode(treeRoot,a,treeModel);
		
		
		DefaultMutableTreeNode tempNode= new DefaultMutableTreeNode();
		tempNode.setUserObject(albumToBeAdded);
		
		treeModel.insertNodeInto(tempNode,updateableNode, updateableNode.getChildCount());
		stateTree.scrollPathToVisible(new TreePath(tempNode.getPath()));
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
	}
	
	public void removeAlbumFromTree(Album a){
		
		DefaultTreeModel treeModel = (DefaultTreeModel) (stateTree.getModel());
		
		findCorrectNode(treeRoot,a,treeModel);
		
		treeModel.removeNodeFromParent(updateableNode);
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
		
	}
	
	
	public void addPhotoSetToTreeNode(Album a,Set<Photo>photoSet){
		
		DefaultTreeModel treeModel = (DefaultTreeModel) (stateTree.getModel());
		
		findCorrectNode(treeRoot,a,treeModel);
		
		Album addPhotosToThis = (Album)updateableNode.getUserObject();
		
		addPhotosToThis.addPhotosToAlbum(photoSet);
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
		
	}
	
	public void removePhotoSetFromTreeNode(Album a, Set<Photo>photoSet){
		
		DefaultTreeModel treeModel = (DefaultTreeModel) (stateTree.getModel());
		
		findCorrectNode(treeRoot,a,treeModel);
		
		Album removePhotosFromThis = (Album) updateableNode.getUserObject();
		Object photoArray[]= photoSet.toArray();
		
		
		for(int i=0;i<photoArray.length;i++){
			
			
			Photo p =(Photo) photoArray[i];
	
			removePhotosFromThis.removePhotoFromAlbum(p);
		
		}
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
	
	}
		
	public void flagAlbumUpdate(Set<Photo> flagPhotoSet){
		DefaultMutableTreeNode flagNode= (DefaultMutableTreeNode)treeRoot.getChildAt(0);
		
		Album currentFlagAlbum = (Album) flagNode.getUserObject();
		
		currentFlagAlbum.emptyOldPhotoSet();
		
		currentFlagAlbum.addPhotosToAlbum(flagPhotoSet);
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
	}
	
	public void greatAlbumUpdate(Set<Photo> greatPhotoSet){
		DefaultMutableTreeNode greatNode= (DefaultMutableTreeNode)treeRoot.getChildAt(1);
		
		Album currentGreatAlbum = (Album) greatNode.getUserObject();
		
		currentGreatAlbum.emptyOldPhotoSet();
		
		currentGreatAlbum.addPhotosToAlbum(greatPhotoSet);
		
		ss.addingState(stateTree,treeRoot);
		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
	}
	
	public void undoingChoices(){
		DefaultTreeModel savedModel =  ss.getUndoModel();
		DefaultMutableTreeNode newRoot =(DefaultMutableTreeNode) savedModel.getRoot();
		Album newRootAlbum = (Album) newRoot.getUserObject();
		
		Album root= new Album("Root");
    	Set<Photo> modelRootPhotoSet = newRootAlbum.getPhotoSet();
    	
    	root.addPhotosToAlbum(modelRootPhotoSet);
		stateTree = makeDataTree(root);
		creatingNewTree(savedModel,newRoot,(DefaultTreeModel)stateTree.getModel(),treeRoot);

		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
		
	}
	
	public void redoingChoices(){
		DefaultTreeModel savedModel =  ss.getRedoModel();
		DefaultMutableTreeNode newRoot =(DefaultMutableTreeNode) savedModel.getRoot();
		Album newRootAlbum = (Album) newRoot.getUserObject();
		
		Album root= new Album("Root");
    	Set<Photo> modelRootPhotoSet = newRootAlbum.getPhotoSet();
    	
    	root.addPhotosToAlbum(modelRootPhotoSet);
		stateTree = makeDataTree(root);
		creatingNewTree(savedModel,newRoot,(DefaultTreeModel)stateTree.getModel(),treeRoot);

		undoEnabled=ss.undoEnabled();
		redoEnabled=ss.redoEnabled();
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
    
    
    public void photosInSSStartAlbum (JTree startRoot, DefaultMutableTreeNode startNode){
    	ss.addingState(startRoot,startNode);
    }
    

	
	


	



	
	
	
	


}
