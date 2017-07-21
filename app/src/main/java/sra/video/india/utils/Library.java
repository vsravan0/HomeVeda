package sra.video.india.utils;

import java.io.Serializable;
import java.util.List;

/**
 * This is the 'library' of all the users videos
 * 
 * @author paul.blundell
 */
public class Library implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// The username of the owner of the library
	private String channelId;
	// A list of videos that the user owns
	private List<Video> videos;

	public Library(String channelId, List<Video> videos) {
		this.channelId = channelId;
		this.videos = videos;
	}

	/**
	 * @return the user name
	 */
	public String getUser() {
		return channelId;
	}

	/**
	 * @return the videos
	 */
	public List<Video> getVideos() {
		return videos;
	}
}