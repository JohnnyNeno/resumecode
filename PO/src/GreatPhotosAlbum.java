import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GreatPhotosAlbum extends SearchAlbumTemplate {
	
	public GreatPhotosAlbum(String s){
		super(s);
	}
	public Set<Photo>findPhotos(List<Album> albumList){
		Set<Photo> highlyRatedSet = new HashSet<Photo>();
		
		
		
		for(int i=0;i<albumList.size();i++){
			
			Album a = albumList.get(i);
			Set<Photo> photoInAlbum=a.getPhotoSet();
			Object photoArray[] = photoInAlbum.toArray();
			
			for(int j=0;j<photoArray.length;j++){
				Photo p = (Photo) photoArray[j];
				
				if(p.getRating()>=4)
					highlyRatedSet.add(p);
				
			}
					
				}
		return  highlyRatedSet;
	}
}
