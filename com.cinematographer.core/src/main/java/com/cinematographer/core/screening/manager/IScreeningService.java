package com.cinematographer.core.screening.manager;

import java.util.Collection;

import com.cinematographer.core.screening.Screening;

public interface IScreeningService {

	Screening findScreening(String title);

	Collection<Screening> getAllScreenings();

	void addScreening(Screening screening);

	void removeScreening(String title);

}
