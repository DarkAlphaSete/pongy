package rodrigo.pongy.event;

import rodrigo.pongy.object.Racket;

public interface RacketMovementListener {

	void moveUpPressed(RacketMovementListener racket);
	void moveDownPressed(RacketMovementListener racket);

}
