package com.cinematographer.core.screening.service;

import java.util.Collection;
import java.util.List;

import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.Status;

public interface IScreeningService {

	Screening findScreening(String title);

	Collection<Screening> getAllScreenings();

	void addScreening(Screening screening);

	void removeScreening(String title);

	void updateSeatsStatus(String title, List<String> selectedSeats,
			Status status);

}
