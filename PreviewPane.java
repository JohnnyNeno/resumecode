import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * PreviewPane is a panel that displays a set of photographs as small thumbnails
 * and allows the user to select a subset of them.
 * 
 * Original @author jbaek, rcm
 * 
 * This class compiles but it is incomplete. You need to edit it in order to use
 * the class(es) to represent albums that you have created.
 * 
 * You can modify any part of this class. However, we have marked with a TODO
 * comment tag the sections of code that probably require most of your
 * attention.
 */

public class PreviewPane extends JScrollPane {

	// content panel
	private JPanel content;

	// list of thumbnails currently displayed
	private List<Thumbnail> thumbnails;
	
	private Album albumBeingDisplayed;

	/**
	 * Make a PreviewPane.
	 */
	public PreviewPane() {
		content = new ScrollableFlowPanel();
		thumbnails = new ArrayList<Thumbnail>();
		setViewportView(content);
		
	}

	/**
	 * Clears the preview pane, so that it displays no photos.
	 */
	public void clear() {
		thumbnails.clear();
		for (Component c : content.getComponents()) {
			content.remove(c);
		}
	}

	/**
	 * Displays a set of photos, completely replacing the previously-displayed
	 * set of photos.
	 * 
	 * @param photos
	 *            the set of photos to display
	 */

	// TODO ADD Your code here
	public void display(Album a) // update parameters if needed
	{
		clear();
		albumBeingDisplayed = a;
		Set <Photo> photosHere= a.getPhotoSet();
		Object [] photoArray=photosHere.toArray();
		
		for(int i =0;i<photoArray.length;i++){
			Photo p = (Photo)photoArray[i];
			Thumbnail t = new Thumbnail(p);
			content.add(t);
			thumbnails.add(t);
		}

		// TODO generate a thumbnail for each photo
		// For each photo P in the set of photos to display do this:
		// Thumbnail t = new Thumbnail(p);
		// content.add(t);
		// thumbnails.add(t);

		// force the scroll pane to lay out again
		content.invalidate();
                content.validate();
		content.doLayout();
		content.repaint();
		invalidate();
		validate();
		doLayout();
		repaint();
	}

	/**
	 * @return the highlighted photos, i.e. the subset of displayed photos that
	 *         the user selected.
	 */
	public Set<Photo> getSelectedPhotos() {
		Set<Photo> result = new HashSet<Photo>();
		for (Thumbnail t : thumbnails) {
			if (t.isSelected()) { // if selected
				result.add(t.getPhoto()); // add to the list of returned ones
			}
		}
		return result;
	}
	
	public Album getPreviewPanesAlbum(){
		return albumBeingDisplayed;
	}

	/**
	 * ScrollableFlowPanel is a panel that flows its components left-to-right
	 * and wraps them onto multiple lines. Designed to be placed in a
	 * JScrollPane. Code from
	 * http://forums.sun.com/thread.jspa?forumID=57&threadID=701797&start=2
	 */
	private static class ScrollableFlowPanel extends JPanel implements
			Scrollable {
		public ScrollableFlowPanel() {
			setLayout(new FlowLayout(FlowLayout.LEADING));
		}

		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y, getParent().getWidth(), height);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(getWidth(), getPreferredHeight());
		}

		@Override
		public Dimension getPreferredScrollableViewportSize() {
			return super.getPreferredSize();
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			int hundredth = (orientation == SwingConstants.VERTICAL ? getParent()
					.getHeight() : getParent().getWidth()) / 100;
			return (hundredth == 0 ? 1 : hundredth);
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			return orientation == SwingConstants.VERTICAL ? getParent()
					.getHeight() : getParent().getWidth();
		}

		@Override
		public boolean getScrollableTracksViewportWidth() {
			return true;
		}

		@Override
		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		// Compute the preferred height of the panel, given the
		// width of the scroll panel.
		private int getPreferredHeight() {
			int rv = 0;
			for (int k = 0, count = getComponentCount(); k < count; k++) {
				Component comp = getComponent(k);
				Rectangle r = comp.getBounds();
				int height = r.y + r.height;
				if (height > rv)
					rv = height;
			}
			rv += ((FlowLayout) getLayout()).getVgap();
			return rv;
		}
	}
}
