package Models;

public class Location {
	private final LocationType locationType;
	private final int cardIndex;
	
	public Location(LocationType locationType, int cardIndex) {
		this.locationType = locationType;
		this.cardIndex = cardIndex;
	}
	
	public LocationType getLocationType() {
		return locationType;
	}
	
	public int getCardIndex() {
		return cardIndex;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		return this.locationType == other.locationType && this.cardIndex == other.cardIndex;
	}
	
	@Override
	public String toString() {
		return String.format("%s:%d", locationType, cardIndex);
	}
	
}
