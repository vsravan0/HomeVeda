package sra.videos.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

//  sra.videos.ui.widget.SravanTextView

public class SravanTextView extends TextView {

	public SravanTextView(Context context) {
		super(context);
		loadFont(context);
	}

	private void loadFont(Context context) {
		this.setTypeface(sra.video.india.utils.LoadAppFonts.getLucidaTypeface(context));
	}
	
	public SravanTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.isInEditMode();
		loadFont(context);
	}

	public SravanTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.isInEditMode();
		loadFont(context);
	}

}
