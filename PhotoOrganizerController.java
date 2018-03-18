import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeNode;
import java.util.Set;
import javax.swing.JTree;
public class PhotoOrganizerController {
	
	private static boolean initialized = false;
	public static CurrentState cs;
	
	
	
	public PhotoOrganizerController(PhotoOrganizer po){
		if(initialized==false){
			
			cs = new CurrentState(po.getStartAlbumList());
			
			cs.addObserver(po);
			
			
			
			
			initialized=true;
			
		}
		else{
			cs.addObserver(po);
			cs.inform(); 
		}
		}
	
	
	
	
	public void newAlbumPressed(DefaultMutableTreeNode tn,Album a){
		
		cs.addAlbumToTree(tn, a);
		cs.inform();
		}
	
	
	public void deleteAlbumPressed(Album delAlbum){
		
		cs.removeAlbumFromTree(delAlbum);
		cs.inform();
		
	}
	
	public void addPhotosPressed(Album addPhotosToThis,Set<Photo> thesePhotos){
		cs.inform();
		cs.addPhotoSetToTreeNode(addPhotosToThis, thesePhotos);
		cs.inform();
		
	}
	
	public void removePhotosPressed(Album removePhotosFromThis,Set<Photo>thesePhotos){
		cs.inform();
		cs.removePhotoSetFromTreeNode(removePhotosFromThis,thesePhotos);
		cs.inform();
		
	}
	
	public void flagButtonPressed(Set<Photo> newSet){
		cs.inform();
		cs.flagAlbumUpdate(newSet);
		cs.inform();
	}
	
	public void rateButtonPressed(Set<Photo> newSet){
		cs.inform();
		cs.greatAlbumUpdate(newSet);
		cs.inform();
	}
	
	public void closingWindow(PhotoOrganizer po){
		cs.removeObserver(po);
		cs.inform();
	}
	
	public void undoButtonPressed(){
		cs.undoingChoices();
		cs.inform();
	}
	
	public void redoButtonPressed(){
		cs.redoingChoices();
		cs.inform();
	}
	
	public void initializeSSPhoto(JTree startRoot, DefaultMutableTreeNode startNode){
		cs.photosInSSStartAlbum(startRoot, startNode);
	}

}
