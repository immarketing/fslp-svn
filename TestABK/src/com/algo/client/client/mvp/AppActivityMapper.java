package com.algo.client.client.mvp;

import com.algo.client.client.ClientFactory;
import com.algo.client.client.activity.SampleActivity;
import com.algo.client.client.place.SamplePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

/**
 * ActivityMapper associates each {@link Place} with its corresponding {@link Activity}.
 */
public class AppActivityMapper implements ActivityMapper {

	/**
	 * Provided for {@link Activitie}s.
	 */
	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
	  
		if (place instanceof SamplePlace)
			return new SampleActivity((SamplePlace) place, clientFactory);

		return null;
	}

}
