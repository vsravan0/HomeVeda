package sra.video.india.utils;

import android.content.Context;
import android.graphics.Typeface;

public class LoadAppFonts {
	public static Typeface italicf, regulartf,lucidasans;

	public static Typeface getItalicTypeface(Context context) {
		if (italicf == null) {
			italicf = Typeface.createFromAsset(context.getAssets(),
					"fonts/leaguegothicitalic.ttf");
		}
		return italicf;
	}
	
	public static Typeface getLucidaTypeface(Context context) {
		if (lucidasans == null) {
			lucidasans = Typeface.createFromAsset(context.getAssets(),
					"fonts/lucida_sans.ttf");
		}
		return lucidasans;
	}
	
	
	

	public static Typeface getRegularTypeface(Context context) {
		if (regulartf == null) {
			regulartf = Typeface.createFromAsset(context.getAssets(),
					"fonts/leaguegothicregular.ttf");
		}
		return regulartf;
	}

}
