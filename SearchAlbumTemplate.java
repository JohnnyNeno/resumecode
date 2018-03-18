import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public abstract class SearchAlbumTemplate extends Album {
	
	
	public SearchAlbumTemplate(String s){
		super(s);
		super.setCommandsAllowed(false);
		
	}
	
	public void update(List<Album> aList){
		Set<Photo> changes = new HashSet<Photo>();
		changes = findPhotos(aList);
		
		super.emptyOldPhotoSet();
		super.addPhotosToAlbum(changes);
	}
	
	
	public abstract Set<Photo> findPhotos(List<Album> albumList);
	
	

}
