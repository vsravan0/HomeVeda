package sra.video.india.utils;

import java.io.Serializable;

public class Video implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title, url, thumbUrl, viodeoid, duration, channelName;

	public Video(String title, String url, String thumbUrl, String channelName,
			String videoid, String duration) {
		super();
		this.title = title;
		this.url = url;
		this.thumbUrl = thumbUrl;
		this.channelName =channelName;
		this.viodeoid = videoid;
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getViodeoid() {
		return viodeoid;
	}

	public void setViodeoid(String videoid) {
		this.viodeoid = videoid;
	}

	public String getduration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}