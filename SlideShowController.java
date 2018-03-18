import java.util.Set;
import java.util.HashSet;


public class SlideShowController {

	// TODO
	private Album album;
	private SlideShowView ssv;
	private int photoCounter=0;
	private int swapTimer=0;
	private Set <Photo> photoSet = new HashSet<Photo>();
	private boolean isCycling =true;
	private boolean startPress=false;
	private boolean nextSlidePress=false;
	private float initialAlpha= 0.0f;

	
	
	private Object [] photoSetArray; 
	private int textTimer;
	

	public SlideShowController(Album a, SlideShowView v) {
		album = a;
		ssv = v;
		photoSet = a.getPhotoSet();
		photoSetArray = photoSet.toArray();
		Photo firstPhoto = (Photo) photoSetArray[0];
		ssv.setPhoto(firstPhoto);
		
	}

	public void timeTick() {
		
		if(nextSlidePress&&startPress){
			photoCounter++;
			if(photoCounter>=album.getPhotoAmountInAlbum())
				photoCounter=0;
			Photo p =(Photo)photoSetArray[photoCounter];
			ssv.setPhoto(p);
			swapTimer=0;
			initialAlpha=1.0f;
			if(isCycling)
				initialAlpha=0.0f;
			nextSlidePress=false;
			ssv.setAlpha(initialAlpha);
		}
		
		
		if(textTimer>=250){
			ssv.setText("");
			Photo p = (Photo)photoSetArray[photoCounter];
			ssv.setPhoto(p);
		}
		if(textTimer<250){
		textTimer++;
		
		}
		
		
		if(isCycling&&startPress){
			swapTimer++;
			
			if (swapTimer<=100&&initialAlpha+0.01f<1.0f){
				ssv.setAlpha(initialAlpha);
				initialAlpha=initialAlpha+0.01f;
				
				
			}
			
			if(swapTimer>150&& initialAlpha-0.01f>0.0f){
				ssv.setAlpha(initialAlpha);
				initialAlpha=initialAlpha-0.01f;
				
			}
			
		
			
			if (swapTimer%250==0){
				
				swapTimer=0;
				
				
				photoCounter++;
				if(photoCounter>=album.getPhotoAmountInAlbum())
					photoCounter=0;
				
				Photo p = (Photo)photoSetArray[photoCounter];
				ssv.setPhoto(p);
				
			}
		}
		
		
		// TODO
	}

	public void startPressed() {
		
		startPress =true;
		ssv.setText(album.toString());
		Photo p = (Photo)photoSetArray[photoCounter];
		ssv.setPhoto(p);
		textTimer=0;
	
		
		
		
		// TODO
	}

	public void nextSlidePressed() {
		nextSlidePress=true;
		// TODO
	}

	public void pauseUnpausePressed() {
		if(isCycling==true)
			isCycling=false;
		else
			isCycling=true;
		// TODO
	}

}
